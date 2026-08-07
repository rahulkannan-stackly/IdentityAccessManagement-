package com.techpalle.service;

import com.techpalle.entity.RefreshToken;
import com.techpalle.entity.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken( User user, String token);

    RefreshToken validateRefreshToken( String token);

    void revokeRefreshToken(String token);

    void deleteUserRefreshTokens(User user);
}
