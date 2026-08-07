package com.techpalle.mapper;




import org.springframework.stereotype.Component;

import com.techpalle.dto.auth.LoginResponse;
import com.techpalle.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthMapper {

    public LoginResponse toLoginResponse(
            User user,
            String accessToken,
            String refreshToken
    ) {

        Set<String> roles = user.getRoles()
                .stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet());

        return LoginResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(roles)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }
}

