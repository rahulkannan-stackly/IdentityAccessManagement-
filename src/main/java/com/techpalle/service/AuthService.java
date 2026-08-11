package com.techpalle.service;

import com.techpalle.dto.auth.ForgotPasswordRequest;
import com.techpalle.dto.auth.LoginRequest;
import com.techpalle.dto.auth.LoginResponse;
import com.techpalle.dto.auth.OtpResponse;
import com.techpalle.dto.auth.RefreshTokenRequest;
import com.techpalle.dto.auth.RegisterRequest;
import com.techpalle.dto.auth.ResetPasswordRequest;
import com.techpalle.dto.auth.ResetPasswordResponse;
import com.techpalle.dto.auth.VerifyOtpRequest;
import com.techpalle.dto.auth.VerifyOtpResponse;
import com.techpalle.dto.user.UserResponse;

public interface AuthService {

	UserResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    void logout(RefreshTokenRequest request);

    LoginResponse refreshToken( RefreshTokenRequest request);

    OtpResponse forgotPassword(ForgotPasswordRequest request);

    VerifyOtpResponse verifyOtp( VerifyOtpRequest request);

    ResetPasswordResponse resetPassword( ResetPasswordRequest request);
    
   
}
