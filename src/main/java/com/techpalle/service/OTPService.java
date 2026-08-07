package com.techpalle.service;

import com.techpalle.entity.OTP;
import com.techpalle.entity.User;

public interface OTPService {

    OTP generateOtp(User user);

    OTP validateOtp( String otpCode,User user );

    void markOtpAsUsed(OTP otp);

    void deleteUserOtps(User user);
}
