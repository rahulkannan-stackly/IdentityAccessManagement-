package com.techpalle.serviceimpl;

import com.techpalle.dto.role.AssignRoleRequest;
import com.techpalle.dto.role.RoleRequest;
import com.techpalle.dto.role.RoleResponse;
import com.techpalle.entity.Role;
import com.techpalle.entity.User;
import com.techpalle.exception.DuplicateResourceException;
import com.techpalle.exception.ResourceNotFoundException;
import com.techpalle.mapper.RoleMapper;
import com.techpalle.repository.RoleRepository;
import com.techpalle.repository.UserRepository;
import com.techpalle.service.AuditLogService;
import com.techpalle.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;
    private final AuditLogService auditLogService;

    @Override
    public RoleResponse createRole(RoleRequest request) {

        log.info("Creating role with name: {}", request.getName());

        if (roleRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException(
                    "Role already exists with name: " + request.getName()
            );
        }

        Role role = roleMapper.toEntity(request);

        Role savedRole = roleRepository.save(role);

        auditLogService.saveAuditLog(
                "ROLE_CREATE",
                "ROLE",
                savedRole.getId(),
                "SUCCESS",
                null
        );

        log.info(
                "Role created successfully with id: {}",
                savedRole.getId()
        );

        return roleMapper.toResponse(savedRole);
    }

    @Override
    public List<RoleResponse> getAllRoles() {

        log.info("Fetching all roles");

        List<RoleResponse> roles = roleRepository.findAll()
                .stream()
                .map(roleMapper::toResponse)
                .toList();

        log.info(
                "Successfully fetched {} roles",
                roles.size()
        );

        return roles;
    }

    @Override
    public void assignRoles(AssignRoleRequest request) {

        log.info(
                "Assigning roles to user id: {}",
                request.getUserId()
        );

        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: "
                                        + request.getUserId()
                        ));

        Set<Role> roles = new HashSet<>();

        for (Long roleId : request.getRoleIds()) {

            Role role = roleRepository
                    .findById(roleId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Role not found with id: "
                                            + roleId
                            ));

            roles.add(role);
        }

        user.setRoles(roles);

        userRepository.save(user);

        auditLogService.saveAuditLog(
                "ROLE_ASSIGN",
                "USER_ROLE",
                user.getId(),
                "SUCCESS",
                user
        );

        log.info(
                "Roles assigned successfully to user id: {}",
                user.getId()
        );
    }
}
