package com.company.taskportal.service;

import com.company.taskportal.dto.LoginRequest;
import com.company.taskportal.dto.LoginResponse;
import com.company.taskportal.dto.RegisterRequest;
import com.company.taskportal.dto.UserResponse;

import java.util.List;

public interface UserService {

    LoginResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    LoginResponse refreshToken(String refreshToken);

    void logout(String refreshToken);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    List<UserResponse> getAllActiveUsers();

    void activateUser(Long id);

    void deactivateUser(Long id);

    void deleteUser(Long id);
}