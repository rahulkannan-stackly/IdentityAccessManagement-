package com.techpalle.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    private String email;

    @NotBlank(message = "OTP is required")
    @Size(max = 10, message = "OTP must not exceed 10 characters")
    private String otp;

    @NotBlank(message = "New password is required")
    @Size(min = 8, max = 255,
            message = "Password must be between 8 and 255 characters")
    private String newPassword;
}

