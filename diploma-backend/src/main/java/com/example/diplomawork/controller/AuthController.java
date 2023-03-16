package com.example.diplomawork.controller;

import com.example.api.AuthApi;
import com.example.diplomawork.service.AuthService;
import com.example.diplomawork.service.RefreshTokenService;
import com.example.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Objects;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Override
    public ResponseEntity<AuthenticationResponse> login(LoginRequest loginRequest) {
        AuthenticationResponse response = authService.login(loginRequest);
        if (Objects.equals(response.getRole(), "ROLE_ADMIN")) {
            logger.info("Login attempt: " + response.getRole());
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<LogoutResponse> logout(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getToken());
        return ResponseEntity.status(OK).body(new LogoutResponse().message("Refresh Token Deleted Successfully!!"));
    }

    @Override
    public ResponseEntity<AuthenticationResponse> refresh(RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    @Override
    public ResponseEntity<Void> signup(RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>(OK);
    }

    @Override
    public ResponseEntity<TokenVerifyResponse> verify(String token) {
        authService.verifyAccount(token);
        return ResponseEntity.status(OK).body(new TokenVerifyResponse().message("Account activated successfully"));
    }
}
