package com.company.taskportal.service;

import com.company.taskportal.entity.RefreshToken;
import com.company.taskportal.entity.User;
import com.company.taskportal.exception.ResourceNotFoundException;
import com.company.taskportal.repository.RefreshTokenRepository;
import com.company.taskportal.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * Refresh token validity (7 days)
     */
    private static final long REFRESH_TOKEN_VALIDITY_DAYS = 7;

    @Override
    public RefreshToken createRefreshToken(User user) {

        refreshTokenRepository.findByUser(user)
                .ifPresent(refreshTokenRepository::delete);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(LocalDateTime.now().plusDays(REFRESH_TOKEN_VALIDITY_DAYS))
                .revoked(false)
                .build();

        refreshToken = refreshTokenRepository.save(refreshToken);

        log.info("Refresh token created for user: {}", user.getUsername());

        return refreshToken;
    }

    @Override
    public RefreshToken verifyRefreshToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Refresh token not found."));

        if (Boolean.TRUE.equals(refreshToken.getRevoked())) {
            throw new IllegalArgumentException("Refresh token has been revoked.");
        }

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {

            refreshTokenRepository.delete(refreshToken);

            throw new IllegalArgumentException("Refresh token has expired.");
        }

        return refreshToken;
    }

    @Override
    public RefreshToken getByToken(String token) {

        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Refresh token not found."));
    }

    @Override
    public void deleteByUser(User user) {

        refreshTokenRepository.deleteByUser(user);

        log.info("Refresh token deleted for user: {}", user.getUsername());
    }
}