package com.techpalle.service;

import com.techpalle.dto.role.AssignRoleRequest;
import com.techpalle.dto.role.RoleRequest;
import com.techpalle.dto.role.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse createRole(RoleRequest request);

    List<RoleResponse> getAllRoles();

    void assignRoles(AssignRoleRequest request);
}
