package com.company.taskportal.service;

import com.company.taskportal.entity.RefreshToken;
import com.company.taskportal.entity.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshToken verifyRefreshToken(String token);

    RefreshToken getByToken(String token);

    void deleteByUser(User user);
}