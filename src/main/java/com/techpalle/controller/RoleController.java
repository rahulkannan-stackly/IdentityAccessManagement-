package com.techpalle.controller;

import com.techpalle.dto.common.ApiResponse;
import com.techpalle.dto.role.AssignRoleRequest;
import com.techpalle.dto.role.RoleRequest;
import com.techpalle.dto.role.RoleResponse;
import com.techpalle.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "Role Management",
        description = "APIs for managing roles and user role assignments"
)
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    @Operation(summary = "Create Role")
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(
            @Valid @RequestBody RoleRequest request) {

        log.info(
                "Received create role request for role: {}",
                request.getName()
        );

        RoleResponse response =
                roleService.createRole(request);

        return ResponseEntity.ok(
                ApiResponse.<RoleResponse>builder()
                        .success(true)
                        .message("Role created successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    @Operation(summary = "Get All Roles")
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRoles() {

        log.info("Received request to fetch all roles");

        List<RoleResponse> roles =
                roleService.getAllRoles();

        return ResponseEntity.ok(
                ApiResponse.<List<RoleResponse>>builder()
                        .success(true)
                        .message("Roles fetched successfully")
                        .data(roles)
                        .build()
        );
    }

    @PostMapping("/assign")
    @Operation(summary = "Assign Roles To User")
    public ResponseEntity<ApiResponse<Void>> assignRoles(
            @Valid @RequestBody AssignRoleRequest request) {

        log.info(
                "Received role assignment request for user id: {}",
                request.getUserId()
        );

        roleService.assignRoles(request);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Roles assigned successfully")
                        .data(null)
                        .build()
        );
    }
}