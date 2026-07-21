package com.company.taskportal.service;

import com.company.taskportal.dto.LoginRequest;
import com.company.taskportal.dto.LoginResponse;
import com.company.taskportal.dto.RegisterRequest;
import com.company.taskportal.dto.UserResponse;
import com.company.taskportal.entity.RefreshToken;
import com.company.taskportal.entity.User;
import com.company.taskportal.exception.DuplicateResourceException;
import com.company.taskportal.exception.ResourceNotFoundException;
import com.company.taskportal.repository.UserRepository;
import com.company.taskportal.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public LoginResponse register(RegisterRequest request) {

        String username = request.getUsername().trim();
        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByUsernameAndDeletedFalse(username)) {
            throw new DuplicateResourceException("Username already exists.");
        }

        if (userRepository.existsByEmailAndDeletedFalse(email)) {
            throw new DuplicateResourceException("Email already exists.");
        }

        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        user = userRepository.save(user);

        refreshTokenService.deleteByUser(user);

        String accessToken = jwtService.generateToken(user);

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user);

        log.info("User registered successfully: {}", username);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        String username = request.getUsername().trim();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        if (!Boolean.TRUE.equals(user.getActive())) {
            throw new IllegalStateException("User account is inactive.");
        }

        refreshTokenService.deleteByUser(user);

        String accessToken = jwtService.generateToken(user);

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user);

        log.info("User logged in: {}", username);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public LoginResponse refreshToken(String token) {

        RefreshToken refreshToken =
                refreshTokenService.verifyRefreshToken(token);

        User user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(user);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public void logout(String token) {

        RefreshToken refreshToken =
                refreshTokenService.getByToken(token);

        refreshTokenService.deleteByUser(refreshToken.getUser());

        log.info("User logged out successfully.");
    }

    @Override
    public UserResponse getUserById(Long id) {

        User user = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        return mapToResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<UserResponse> getAllActiveUsers() {

        return userRepository.findByActiveTrueAndDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void activateUser(Long id) {

        User user = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        user.setActive(true);

        userRepository.save(user);

        log.info("User activated: {}", user.getUsername());
    }

    @Override
    public void deactivateUser(Long id) {

        User user = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        user.setActive(false);

        userRepository.save(user);

        log.info("User deactivated: {}", user.getUsername());
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        user.setDeleted(true);
        user.setActive(false);

        userRepository.save(user);

        log.info("User deleted: {}", user.getUsername());
    }

    private UserResponse mapToResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}