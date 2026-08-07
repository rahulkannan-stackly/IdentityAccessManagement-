package com.techpalle.mapper;

import org.springframework.stereotype.Component;

import com.techpalle.dto.user.UserRequest;
import com.techpalle.dto.user.UserResponse;
import com.techpalle.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toEntity(UserRequest request) {

        return User.builder().username(request.getUsername()).email(request.getEmail()) 
        		.password(request.getPassword()).firstName(request.getFirstName()) .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber()).isActive(request.getIsActive()) .build();
    }

    public UserResponse toResponse(User user) {

        Set<String> roles = user.getRoles() .stream().map(role -> role.getName())
                .collect(Collectors.toSet());

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .isActive(user.getIsActive())
                .roles(roles)
                .lastLoginTime(user.getLastLoginTime())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}