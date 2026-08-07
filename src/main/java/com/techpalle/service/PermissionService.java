package com.techpalle.service;

import com.techpalle.dto.permission.PermissionRequest;
import com.techpalle.dto.permission.PermissionResponse;

import java.util.List;

public interface PermissionService {

    PermissionResponse createPermission(PermissionRequest request);

    List<PermissionResponse> getAllPermissions();
}