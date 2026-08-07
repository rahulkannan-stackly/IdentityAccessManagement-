package com.techpalle.controller;

import com.techpalle.dto.auth.ForgotPasswordRequest;
import com.techpalle.dto.auth.LoginRequest;
import com.techpalle.dto.auth.LoginResponse;
import com.techpalle.dto.auth.RefreshTokenRequest;
import com.techpalle.dto.auth.RegisterRequest;
import com.techpalle.dto.auth.ResetPasswordRequest;
import com.techpalle.dto.auth.VerifyOtpRequest;
import com.techpalle.dto.common.ApiResponse;
import com.techpalle.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "Authentication Management",
        description = "Authentication, JWT, OTP and Password Management APIs"
)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    public ResponseEntity<ApiResponse<Void>> register(
            @Valid @RequestBody RegisterRequest request) {

        log.info(
                "Received registration request for username: {}",
                request.getUsername()
        );

        authService.register(request);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("User registered successfully")
                        .data(null)
                        .build()
        );
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user and generate JWT tokens")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        log.info(
                "Received login request for username: {}",
                request.getUsername()
        );

        LoginResponse response =
                authService.login(request);

        return ResponseEntity.ok(
                ApiResponse.<LoginResponse>builder()
                        .success(true)
                        .message("Login successful")
                        .data(response)
                        .build()
        );
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout user and revoke refresh token")
    public ResponseEntity<ApiResponse<Void>> logout(
            @Valid @RequestBody RefreshTokenRequest request) {

        log.info("Received logout request");

        authService.logout(request);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Logout successful")
                        .data(null)
                        .build()
        );
    }

    @PostMapping("/refresh")
    @Operation(summary = "Generate new access token using refresh token")
    public ResponseEntity<ApiResponse<LoginResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {

        log.info("Received refresh token request");

        LoginResponse response =
                authService.refreshToken(request);

        return ResponseEntity.ok(
                ApiResponse.<LoginResponse>builder()
                        .success(true)
                        .message("Access token refreshed successfully")
                        .data(response)
                        .build()
        );
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Generate OTP for password reset")
    public ResponseEntity<ApiResponse<Void>> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request) {

        log.info(
                "Received forgot password request for email: {}",
                request.getEmail()
        );

        authService.forgotPassword(request);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("OTP generated successfully")
                        .data(null)
                        .build()
        );
    }

    @PostMapping("/verify-otp")
    @Operation(summary = "Verify OTP")
    public ResponseEntity<ApiResponse<Void>> verifyOtp(
            @Valid @RequestBody VerifyOtpRequest request) {

        log.info(
                "Received OTP verification request for email: {}",
                request.getEmail()
        );

        authService.verifyOtp(request);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("OTP verified successfully")
                        .data(null)
                        .build()
        );
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset user password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request) {

        log.info(
                "Received password reset request for email: {}",
                request.getEmail()
        );

        authService.resetPassword(request);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Password reset successfully")
                        .data(null)
                        .build()
        );
    }
}
