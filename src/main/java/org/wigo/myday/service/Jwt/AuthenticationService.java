package org.wigo.myday.service.Jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.wigo.myday.dto.LoginUserDto;
import org.wigo.myday.dto.RegisterUserDto;
import org.wigo.myday.dto.VerifyUserDto;
import org.wigo.myday.model.UserEntity;
import org.wigo.myday.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final EmailService emailService;

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            EmailService emailService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }

    //signup/Register
    public UserEntity register(RegisterUserDto regInput) {
        UserEntity userEntity = new UserEntity(regInput.getUsername(), regInput.getEmail(), passwordEncoder.encode(regInput.getPassword()));
        userEntity.setVerificationCode(generateVerificationCode());
        userEntity.setVerificationCodeExpiration(
                LocalDateTime.now().plusMinutes(15)
                        .atZone(ZoneId.systemDefault()) // or ZoneOffset.UTC
                        .toInstant()
        );        userEntity.setEnabled(false);
        sendVerificationEmail(userEntity);
        return userRepository.save(userEntity);
    }

    public UserEntity authenticate(LoginUserDto loginInput) {
        UserEntity userEntity = userRepository.findByEmail(loginInput.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!userEntity.isEnabled()) {
            throw new RuntimeException("Account not verified. Please verify your account");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginInput.getEmail(),
                        loginInput.getPassword()
                )
        );
        return userEntity;
    }

    public void verifyUser(VerifyUserDto input) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(input.getEmail());
        if(optionalUser.isPresent()) {
            UserEntity userEntity = optionalUser.get();

            if(userEntity.getVerificationCode().equals(input.getVerificationCode())) {
                userEntity.setEnabled(true);
                userEntity.setVerificationCode(null);
                userEntity.setVerificationCodeExpiration(null);
                userRepository.save(userEntity);
            }else{
                throw new RuntimeException("Verification code does not match");
            }
        }else{
            throw new RuntimeException("User not found");
        }
    }

    public void resendVerificationCode(String email) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()) {
            UserEntity userEntity = optionalUser.get();

            if(userEntity.isEnabled()){
                throw new RuntimeException("Account is already verified");
            }

            userEntity.setVerificationCode(generateVerificationCode());
            userEntity.setVerificationCodeExpiration(Instant.from(LocalDateTime.now().plusMinutes(15))); //userEntity.setVerificationCodeExpiration(LocalDateTime.now().plusMinutes(15));
            sendVerificationEmail(userEntity);
            userRepository.save(userEntity);
        }else{
            throw new RuntimeException("User not found");
        }
    }

    public void sendVerificationEmail(UserEntity userEntity) {
        String subject = "Account Verification";
        String verificationCode = userEntity.getVerificationCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif; margin:0; padding:0; background-color: #f5f5f5;\">"
                + "<div style=\"max-width: 600px; margin: 0 auto; padding: 20px;\">"
                + "<h2 style=\"color: #333; text-align: center;\">Welcome to Our App!</h2>"
                + "<p style=\"font-size: 16px; color: #555; text-align: center;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); text-align: center;\">"
                + "<h3 style=\"color: #333; margin-bottom: 15px;\">Your Verification Code</h3>"
                + "<p style=\"font-size: 24px; font-weight: bold; color: #007bff; letter-spacing: 4px; user-select: all;\">"
                + verificationCode
                + "</p>"
                + "</div>"
                + "<p style=\"font-size: 14px; color: #888; text-align: center; margin-top: 20px;\">If you did not request this code, please ignore this email.</p>"
                + "</div>"
                + "</body>"
                + "</html>";

        try{
            emailService.sendVerificationEmail(userEntity.getEmail(), subject, htmlMessage);
        } catch (Exception e){
            logger.error("Failed to send verification email to {}: {}", userEntity.getEmail(), e.getMessage(), e);
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

}
