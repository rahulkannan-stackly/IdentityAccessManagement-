package com.techpalle.serviceimpl;

import com.techpalle.dto.user.UserRequest;
import com.techpalle.dto.user.UserResponse;
import com.techpalle.entity.User;
import com.techpalle.exception.DuplicateResourceException;
import com.techpalle.exception.ResourceNotFoundException;
import com.techpalle.mapper.UserMapper;
import com.techpalle.repository.UserRepository;
import com.techpalle.service.AuditLogService;
import com.techpalle.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private  UserRepository userRepository;
    private  UserMapper userMapper;
    private  AuditLogService auditLogService;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest request) {

        log.info("Creating user with username: {}", request.getUsername());

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException(
                    "Username already exists"
            );
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Email already exists"
            );
        }

        User user = userMapper.toEntity(request);

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        User savedUser = userRepository.save(user);

        auditLogService.saveAuditLog(
                "USER_CREATE",
                "USER",
                savedUser.getId(),
                "SUCCESS",
                savedUser
        );

        log.info(
                "User created successfully with id: {}",
                savedUser.getId()
        );

        return userMapper.toResponse(savedUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        log.info("Fetching all users");

        List<UserResponse> users = userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();

        log.info(
                "Successfully fetched {} users",
                users.size()
        );

        return users;
    }

    @Override
    public UserResponse getUserById(Long id) {

        log.info("Fetching user with id: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id
                        ));

        log.info("User found with id: {}", id);

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {

        log.info("Updating user with id: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id
                        ));

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setIsActive(request.getIsActive());

        User updatedUser = userRepository.save(user);

        auditLogService.saveAuditLog(
                "USER_UPDATE",
                "USER",
                updatedUser.getId(),
                "SUCCESS",
                updatedUser
        );

        log.info(
                "User updated successfully with id: {}",
                updatedUser.getId()
        );

        return userMapper.toResponse(updatedUser);
    }

      @Override
       public void deleteUser(Long id) {

        		    log.info("Deleting user with id: {}", id);

        		    User user = userRepository.findById(id)
        		            .orElseThrow(() ->
        		                    new ResourceNotFoundException(
        		                            "User not found with id: " + id
        		                    ));

        		    userRepository.delete(user);

        		    auditLogService.saveAuditLog(
        		            "USER_DELETE",
        		            "USER",
        		            user.getId(),
        		            "SUCCESS",
        		            user
        		    );

        		    log.info(
        		            "User deleted successfully with id: {}",
        		            id
        		    );
        		}
    }