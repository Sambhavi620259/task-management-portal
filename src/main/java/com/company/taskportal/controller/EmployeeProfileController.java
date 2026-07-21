package com.company.taskportal.controller;

import com.company.taskportal.common.ApiResponse;
import com.company.taskportal.dto.EmployeeProfileRequest;
import com.company.taskportal.dto.EmployeeProfileResponse;
import com.company.taskportal.service.EmployeeProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class EmployeeProfileController {

    private final EmployeeProfileService employeeProfileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<EmployeeProfileResponse>> createProfile(
            @RequestParam Long userId,
            @Valid @ModelAttribute EmployeeProfileRequest request) {

        EmployeeProfileResponse response =
                employeeProfileService.createProfile(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<EmployeeProfileResponse>builder()
                        .success(true)
                        .message("Employee profile created successfully.")
                        .data(response)
                        .build());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<EmployeeProfileResponse>> updateProfile(
            @PathVariable Long id,
            @Valid @ModelAttribute EmployeeProfileRequest request) {

        EmployeeProfileResponse response =
                employeeProfileService.updateProfile(id, request);

        return ResponseEntity.ok(
                ApiResponse.<EmployeeProfileResponse>builder()
                        .success(true)
                        .message("Employee profile updated successfully.")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeProfileResponse>> getProfileById(
            @PathVariable Long id) {

        EmployeeProfileResponse response =
                employeeProfileService.getProfileById(id);

        return ResponseEntity.ok(
                ApiResponse.<EmployeeProfileResponse>builder()
                        .success(true)
                        .message("Employee profile fetched successfully.")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeProfileResponse>>> getAllProfiles() {

        List<EmployeeProfileResponse> response =
                employeeProfileService.getAllProfiles();

        return ResponseEntity.ok(
                ApiResponse.<List<EmployeeProfileResponse>>builder()
                        .success(true)
                        .message("Employee profiles fetched successfully.")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<EmployeeProfileResponse>>> getAllActiveProfiles() {

        List<EmployeeProfileResponse> response =
                employeeProfileService.getAllActiveProfiles();

        return ResponseEntity.ok(
                ApiResponse.<List<EmployeeProfileResponse>>builder()
                        .success(true)
                        .message("Active employee profiles fetched successfully.")
                        .data(response)
                        .build()
        );
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<Void>> activateProfile(
            @PathVariable Long id) {

        employeeProfileService.activateProfile(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Employee profile activated successfully.")
                        .build()
        );
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivateProfile(
            @PathVariable Long id) {

        employeeProfileService.deactivateProfile(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Employee profile deactivated successfully.")
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProfile(
            @PathVariable Long id) {

        employeeProfileService.deleteProfile(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Employee profile deleted successfully.")
                        .build()
        );
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<EmployeeProfileResponse>> getMyProfile(
            Authentication authentication) {

        String username = authentication.getName();

        // You'll implement this method in the service
        EmployeeProfileResponse response =
                employeeProfileService.getProfileByUsername(username);

        return ResponseEntity.ok(
                ApiResponse.<EmployeeProfileResponse>builder()
                        .success(true)
                        .message("Profile fetched successfully.")
                        .data(response)
                        .build()
        );
    }
}