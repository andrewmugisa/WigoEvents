package org.wigo.myday.controller.authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wigo.myday.dto.LoginUserDto;
import org.wigo.myday.dto.RegisterUserDto;
import org.wigo.myday.dto.VerifyUserDto;
import org.wigo.myday.model.UserEntity;
import org.wigo.myday.response.LoginResponse;
import org.wigo.myday.service.Jwt.AuthenticationService;
import org.wigo.myday.service.Jwt.JwtService;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserEntity> register(@RequestBody RegisterUserDto registerUserDto) {
        UserEntity registerUser = authenticationService.register(registerUserDto);
        return ResponseEntity.ok(registerUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        UserEntity authenticateUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticateUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getJwtExpiration());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDto verifyUserDto) {
        try{
            authenticationService.verifyUser(verifyUserDto);
            return ResponseEntity.ok("Account verified Successfully");

        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {
        try{
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification code resend Successfully");
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
