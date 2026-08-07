package com.techpalle.serviceimpl;

import com.techpalle.entity.OTP;
import com.techpalle.entity.User;
import com.techpalle.exception.BadRequestException;
import com.techpalle.repository.OTPRepository;
import com.techpalle.service.OTPService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class OTPServiceImpl implements OTPService {

    private final OTPRepository otpRepository;

    @Override
    public OTP generateOtp(User user) {

        log.info(
                "Generating OTP for user: {}",
                user.getUsername()
        );

        otpRepository.deleteByUser(user);

        String otpCode = String.format(
                "%06d",
                new Random().nextInt(1000000)
        );

        OTP otp = OTP.builder()
                .code(otpCode)
                .expiryDate(LocalDateTime.now().plusMinutes(10))
                .isUsed(false)
                .user(user)
                .build();

        OTP savedOtp = otpRepository.save(otp);

        log.info(
                "OTP generated successfully for user: {}",
                user.getUsername()
        );

        return savedOtp;
    }

    @Override
    public OTP validateOtp(
            String otpCode,
            User user
    ) {

        log.info(
                "Validating OTP for user: {}",
                user.getUsername()
        );

        OTP otp = otpRepository
                .findByCodeAndUser(otpCode, user)
                .orElseThrow(() ->
                        new BadRequestException("Invalid OTP"));

        if (Boolean.TRUE.equals(otp.getIsUsed())) {

            log.error(
                    "OTP already used for user: {}",
                    user.getUsername()
            );

            throw new BadRequestException(
                    "OTP has already been used"
            );
        }

        if (LocalDateTime.now().isAfter(
                otp.getExpiryDate())) {

            log.error(
                    "OTP expired for user: {}",
                    user.getUsername()
            );

            throw new BadRequestException(
                    "OTP has expired"
            );
        }

        log.info(
                "OTP validated successfully for user: {}",
                user.getUsername()
        );

        return otp;
    }

    @Override
    public void markOtpAsUsed(OTP otp) {

        log.info(
                "Marking OTP as used. OTP Id: {}",
                otp.getId()
        );

        otp.setIsUsed(true);
        otp.setUsedAt(LocalDateTime.now());

        otpRepository.save(otp);

        log.info(
                "OTP marked as used successfully. OTP Id: {}",
                otp.getId()
        );
    }

    @Override
    public void deleteUserOtps(User user) {

        log.info(
                "Deleting OTPs for user: {}",
                user.getUsername()
        );

        otpRepository.deleteByUser(user);

        log.info(
                "OTPs deleted successfully for user: {}",
                user.getUsername()
        );
    }
}

