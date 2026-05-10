package org.wigo.myday.controller.authentication;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wigo.myday.dto.*;
import org.wigo.myday.model.UserEntity;
import org.wigo.myday.response.ApiResponse;
import org.wigo.myday.response.LoginResponse;
import org.wigo.myday.response.UserResponse;
import org.wigo.myday.service.Jwt.AuthenticationService;
import org.wigo.myday.service.Jwt.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService            = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterUserDto dto) {
        UserEntity user = authenticationService.register(dto);
        return ResponseEntity.ok(new UserResponse(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginUserDto dto) {
        UserEntity user  = authenticationService.authenticate(dto);
        String token     = jwtService.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(token, jwtService.getJwtExpiration()));
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse> verify(@Valid @RequestBody VerifyUserDto dto) {
        authenticationService.verifyUser(dto);
        return ResponseEntity.ok(new ApiResponse("Account verified successfully"));
    }

    @PostMapping("/resend")
    public ResponseEntity<ApiResponse> resend(@Valid @RequestBody ResendVerificationDto dto) {
        authenticationService.resendVerificationCode(dto.getEmail());
        return ResponseEntity.ok(new ApiResponse("Verification code resent successfully"));
    }
}