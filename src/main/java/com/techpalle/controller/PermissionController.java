package com.techpalle.controller;

import com.techpalle.dto.common.ApiResponse;
import com.techpalle.dto.permission.PermissionRequest;
import com.techpalle.dto.permission.PermissionResponse;
import com.techpalle.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "Permission Management",
        description = "APIs for managing permissions"
)
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    @Operation(summary = "Create Permission")
    public ResponseEntity<ApiResponse<PermissionResponse>> createPermission(
            @Valid @RequestBody PermissionRequest request) {

        log.info(
                "Received create permission request for code: {}",
                request.getCode()
        );

        PermissionResponse response =
                permissionService.createPermission(request);

        return ResponseEntity.ok(
                ApiResponse.<PermissionResponse>builder()
                        .success(true)
                        .message("Permission created successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    @Operation(summary = "Get All Permissions")
    public ResponseEntity<ApiResponse<List<PermissionResponse>>> getAllPermissions() {

        log.info("Received request to fetch all permissions");

        List<PermissionResponse> permissions =
                permissionService.getAllPermissions();

        return ResponseEntity.ok(
                ApiResponse.<List<PermissionResponse>>builder()
                        .success(true)
                        .message("Permissions fetched successfully")
                        .data(permissions)
                        .build()
        );
    }
}