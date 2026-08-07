package com.techpalle.serviceimpl;

import com.techpalle.dto.permission.PermissionRequest;
import com.techpalle.dto.permission.PermissionResponse;
import com.techpalle.entity.Permission;
import com.techpalle.exception.DuplicateResourceException;
import com.techpalle.mapper.PermissionMapper;
import com.techpalle.repository.PermissionRepository;
import com.techpalle.service.AuditLogService;
import com.techpalle.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    private final AuditLogService auditLogService;

    @Override
    public PermissionResponse createPermission(
            PermissionRequest request) {

        log.info(
                "Creating permission with code: {}",
                request.getCode()
        );

        if (permissionRepository.existsByCode(request.getCode())) {

            log.error(
                    "Permission already exists with code: {}",
                    request.getCode()
            );

            throw new DuplicateResourceException(
                    "Permission already exists with code: "
                            + request.getCode()
            );
        }

        Permission permission =
                permissionMapper.toEntity(request);

        Permission savedPermission =
                permissionRepository.save(permission);

        auditLogService.saveAuditLog(
                "PERMISSION_CREATE",
                "PERMISSION",
                savedPermission.getId(),
                "SUCCESS",
                null
        );

        log.info(
                "Permission created successfully with id: {}",
                savedPermission.getId()
        );

        return permissionMapper.toResponse(savedPermission);
    }

    @Override
    public List<PermissionResponse> getAllPermissions() {

        log.info("Fetching all permissions");

        List<PermissionResponse> permissionResponses =
                permissionRepository.findAll()
                        .stream()
                        .map(permissionMapper::toResponse)
                        .toList();

        log.info(
                "Successfully fetched {} permissions",
                permissionResponses.size()
        );

        return permissionResponses;
    }
}