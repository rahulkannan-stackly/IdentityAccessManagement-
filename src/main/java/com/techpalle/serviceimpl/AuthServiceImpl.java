package com.techpalle.serviceimpl;

import com.techpalle.dto.auth.ForgotPasswordRequest;
import com.techpalle.dto.auth.LoginRequest;
import com.techpalle.dto.auth.LoginResponse;
import com.techpalle.dto.auth.RefreshTokenRequest;
import com.techpalle.dto.auth.RegisterRequest;
import com.techpalle.dto.auth.ResetPasswordRequest;
import com.techpalle.dto.auth.VerifyOtpRequest;
import com.techpalle.entity.OTP;
import com.techpalle.entity.RefreshToken;
import com.techpalle.entity.User;
import com.techpalle.exception.DuplicateResourceException;
import com.techpalle.exception.ResourceNotFoundException;
import com.techpalle.mapper.AuthMapper;
import com.techpalle.repository.UserRepository;
import com.techpalle.security.CustomUserDetailsService;
import com.techpalle.security.JwtService;
import com.techpalle.service.AuditLogService;
import com.techpalle.service.AuthService;
import com.techpalle.service.LoginHistoryService;
import com.techpalle.service.OTPService;
import com.techpalle.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    private final AuthMapper authMapper;
    private final OTPService otpService;
    private final RefreshTokenService refreshTokenService;
    private final LoginHistoryService loginHistoryService;
    private final AuditLogService auditLogService;

    @Override
    public void register(RegisterRequest request) {

        log.info(
                "Processing registration request for username: {}",
                request.getUsername()
        );

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException(
                    "Username already exists"
            );
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Email already exists"
            );
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .isActive(true)
                .build();

        User savedUser = userRepository.save(user);

        auditLogService.saveAuditLog(
                "REGISTER",
                "USER",
                savedUser.getId(),
                "SUCCESS",
                savedUser
        );

        log.info(
                "User registered successfully with id: {}",
                savedUser.getId()
        );
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        log.info(
                "Login attempt for username: {}",
                request.getUsername()
        );

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        UserDetails userDetails =
                customUserDetailsService.loadUserByUsername(
                        user.getUsername()
                );

        String accessToken =
                jwtService.generateAccessToken(userDetails);

        String refreshTokenValue =
                jwtService.generateRefreshToken(userDetails);

        refreshTokenService.createRefreshToken(
                user,
                refreshTokenValue
        );

        loginHistoryService.recordLogin(
                user,
                null,
                null
        );

        auditLogService.saveAuditLog(
                "LOGIN",
                "AUTH",
                user.getId(),
                "SUCCESS",
                user
        );

        log.info(
                "Login successful for username: {}",
                user.getUsername()
        );

        return authMapper.toLoginResponse(
                user,
                accessToken,
                refreshTokenValue
        );
    }

    @Override
    public void logout(RefreshTokenRequest request) {

        log.info("Processing logout request");

        RefreshToken refreshToken =
                refreshTokenService.validateRefreshToken(
                        request.getRefreshToken()
                );

        User user = refreshToken.getUser();

        refreshTokenService.revokeRefreshToken(
                request.getRefreshToken()
        );

        loginHistoryService.recordLogout(user);

        auditLogService.saveAuditLog(
                "LOGOUT",
                "AUTH",
                user.getId(),
                "SUCCESS",
                user
        );

        log.info(
                "Logout successful for user: {}",
                user.getUsername()
        );
    }

    @Override
    public LoginResponse refreshToken(
            RefreshTokenRequest request) {

        log.info("Processing refresh token request");

        RefreshToken refreshToken =
                refreshTokenService.validateRefreshToken(
                        request.getRefreshToken()
                );

        User user = refreshToken.getUser();

        UserDetails userDetails =
                customUserDetailsService.loadUserByUsername(
                        user.getUsername()
                );

        String accessToken =
                jwtService.generateAccessToken(userDetails);

        log.info(
                "Access token refreshed successfully for user: {}",
                user.getUsername()
        );

        return authMapper.toLoginResponse(
                user,
                accessToken,
                refreshToken.getToken()
        );
    }

    @Override
    public void forgotPassword(
            ForgotPasswordRequest request) {

        log.info(
                "Forgot password request received for email: {}",
                request.getEmail()
        );

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        otpService.generateOtp(user);

        auditLogService.saveAuditLog(
                "FORGOT_PASSWORD",
                "USER",
                user.getId(),
                "SUCCESS",
                user
        );

        log.info(
                "OTP generated successfully for email: {}",
                request.getEmail()
        );
    }

    @Override
    public void verifyOtp(
            VerifyOtpRequest request) {

        log.info(
                "Verifying OTP for email: {}",
                request.getEmail()
        );

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        otpService.validateOtp(
                request.getOtp(),
                user
        );

        log.info(
                "OTP verified successfully for email: {}",
                request.getEmail()
        );
    }

    @Override
    public void resetPassword(
            ResetPasswordRequest request) {

        log.info(
                "Reset password request received for email: {}",
                request.getEmail()
        );

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        OTP otp = otpService.validateOtp(
                request.getOtp(),
                user
        );

        otpService.markOtpAsUsed(otp);

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        userRepository.save(user);

        auditLogService.saveAuditLog(
                "RESET_PASSWORD",
                "USER",
                user.getId(),
                "SUCCESS",
                user
        );

        log.info(
                "Password reset completed successfully for user: {}",
                user.getUsername()
        );
    }
}
