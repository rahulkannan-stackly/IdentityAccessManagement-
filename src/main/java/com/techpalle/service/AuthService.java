package com.techpalle.service;

import com.techpalle.dto.auth.ForgotPasswordRequest;
import com.techpalle.dto.auth.LoginRequest;
import com.techpalle.dto.auth.LoginResponse;
import com.techpalle.dto.auth.RefreshTokenRequest;
import com.techpalle.dto.auth.RegisterRequest;
import com.techpalle.dto.auth.ResetPasswordRequest;
import com.techpalle.dto.auth.VerifyOtpRequest;

public interface AuthService {

    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    void logout(RefreshTokenRequest request);

    LoginResponse refreshToken( RefreshTokenRequest request);

    void forgotPassword(ForgotPasswordRequest request);

    void verifyOtp( VerifyOtpRequest request);

    void resetPassword( ResetPasswordRequest request);
}
