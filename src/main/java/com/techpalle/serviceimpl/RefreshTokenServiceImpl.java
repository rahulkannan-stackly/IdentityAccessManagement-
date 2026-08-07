package com.techpalle.serviceimpl;

import com.techpalle.entity.RefreshToken;
import com.techpalle.entity.User;
import com.techpalle.exception.BadRequestException;
import com.techpalle.repository.RefreshTokenRepository;
import com.techpalle.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken createRefreshToken(
            User user,
            String token
    ) {

        log.info("Creating refresh token for user: {}", user.getUsername());

        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .expiryDate(LocalDateTime.now().plusDays(7))
                .isRevoked(false)
                .user(user)
                .build();

        RefreshToken savedToken =
                refreshTokenRepository.save(refreshToken);

        log.info("Refresh token created successfully for user: {}",
                user.getUsername());

        return savedToken;
    }

    @Override
    public RefreshToken validateRefreshToken(String token) {

        log.info("Validating refresh token");

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() ->
                        new BadRequestException("Invalid refresh token"));

        if (Boolean.TRUE.equals(refreshToken.getIsRevoked())) {
            throw new BadRequestException(
                    "Refresh token has been revoked");
        }

        if (LocalDateTime.now()
                .isAfter(refreshToken.getExpiryDate())) {

            throw new BadRequestException(
                    "Refresh token has expired");
        }

        log.info("Refresh token validated successfully");

        return refreshToken;
    }

    @Override
    public void revokeRefreshToken(String token) {

        log.info("Revoking refresh token");

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() ->
                        new BadRequestException("Invalid refresh token"));

        refreshToken.setIsRevoked(true);

        refreshTokenRepository.save(refreshToken);

        log.info("Refresh token revoked successfully");
    }

    @Override
    public void deleteUserRefreshTokens(User user) {

        log.info(
                "Deleting refresh tokens for user: {}",
                user.getUsername()
        );

        refreshTokenRepository.deleteByUser(user);

        log.info(
                "Refresh tokens deleted successfully for user: {}",
                user.getUsername()
        );
    }
}