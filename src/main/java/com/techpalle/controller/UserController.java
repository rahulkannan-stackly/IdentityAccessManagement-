package com.techpalle.controller;

import com.techpalle.dto.common.ApiResponse;
import com.techpalle.dto.user.UserRequest;
import com.techpalle.dto.user.UserResponse;
import com.techpalle.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "User Management",
        description = "APIs for managing users"
)
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create User")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody UserRequest request) {

        log.info(
                "Received create user request for username: {}",
                request.getUsername()
        );

        UserResponse response = userService.createUser(request);

        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("User created successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    @Operation(summary = "Get All Users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {

        log.info("Received request to fetch all users");

        List<UserResponse> users = userService.getAllUsers();

        return ResponseEntity.ok(
                ApiResponse.<List<UserResponse>>builder()
                        .success(true)
                        .message("Users fetched successfully")
                        .data(users)
                        .build()
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get User By Id")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @PathVariable Long id) {

        log.info(
                "Received request to fetch user with id: {}",
                id
        );

        UserResponse response = userService.getUserById(id);

        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("User fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update User")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request) {

        log.info(
                "Received request to update user with id: {}",
                id
        );

        UserResponse response =
                userService.updateUser(id, request);

        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("User updated successfully")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete User")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable Long id) {

        log.info(
                "Received request to delete user with id: {}",
                id
        );

        userService.deleteUser(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("User deleted successfully")
                        .data(null)
                        .build()
        );
    }
}