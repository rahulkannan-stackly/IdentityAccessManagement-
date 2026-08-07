package com.techpalle.mapper;

import org.springframework.stereotype.Component;
import com.techpalle.dto.permission.PermissionRequest;
import com.techpalle.dto.permission.PermissionResponse;
import com.techpalle.entity.Permission;

@Component
public class PermissionMapper {

    public Permission toEntity(PermissionRequest request) {

        return Permission.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .isActive(request.getIsActive())
                .build();
    }

    public PermissionResponse toResponse(Permission permission) {

        return PermissionResponse.builder()
                .id(permission.getId())
                .code(permission.getCode())
                .name(permission.getName())
                .description(permission.getDescription())
                .isActive(permission.getIsActive())
                .createdAt(permission.getCreatedAt())
                .updatedAt(permission.getUpdatedAt())
                .build();
    }
}
