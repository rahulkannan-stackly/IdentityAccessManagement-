package com.techpalle.mapper;

import org.springframework.stereotype.Component;

import com.techpalle.dto.role.RoleRequest;
import com.techpalle.dto.role.RoleResponse;
import com.techpalle.entity.Role;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    public Role toEntity(RoleRequest request) {

        return Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .isActive(request.getIsActive())
                .build();
    }

    public RoleResponse toResponse(Role role) {

        Set<String> permissions = role.getPermissions()
                .stream()
                .map(permission -> permission.getCode())
                .collect(Collectors.toSet());

        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .isActive(role.getIsActive())
                .permissions(permissions)
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .build();
    }
}
