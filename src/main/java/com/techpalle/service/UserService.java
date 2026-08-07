package com.techpalle.service;

import com.techpalle.dto.user.UserRequest;
import com.techpalle.dto.user.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse updateUser( Long id,UserRequest request);

    void deleteUser(Long id);
}
