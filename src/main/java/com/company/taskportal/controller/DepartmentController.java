package com.company.taskportal.controller;

import com.company.taskportal.common.ApiResponse;
import com.company.taskportal.dto.DepartmentRequest;
import com.company.taskportal.dto.DepartmentResponse;
import com.company.taskportal.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponse>> createDepartment(
            @Valid @RequestBody DepartmentRequest request) {

        DepartmentResponse response = departmentService.createDepartment(request);

        ApiResponse<DepartmentResponse> apiResponse = new ApiResponse<>(
                true,
                "Department created successfully.",
                response,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getAllDepartments() {

        List<DepartmentResponse> response = departmentService.getAllDepartments();

        ApiResponse<List<DepartmentResponse>> apiResponse = new ApiResponse<>(
                true,
                "Departments fetched successfully.",
                response,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> getDepartmentById(
            @PathVariable Long id) {

        DepartmentResponse response = departmentService.getDepartmentById(id);

        ApiResponse<DepartmentResponse> apiResponse = new ApiResponse<>(
                true,
                "Department fetched successfully.",
                response,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequest request) {

        DepartmentResponse response = departmentService.updateDepartment(id, request);

        ApiResponse<DepartmentResponse> apiResponse = new ApiResponse<>(
                true,
                "Department updated successfully.",
                response,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDepartment(
            @PathVariable Long id) {

        departmentService.deleteDepartment(id);

        ApiResponse<Void> apiResponse = new ApiResponse<>(
                true,
                "Department deleted successfully.",
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(apiResponse);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<Void>> activateDepartment(
            @PathVariable Long id) {

        departmentService.activateDepartment(id);

        ApiResponse<Void> apiResponse = new ApiResponse<>(
                true,
                "Department activated successfully.",
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(apiResponse);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivateDepartment(
            @PathVariable Long id) {

        departmentService.deactivateDepartment(id);

        ApiResponse<Void> apiResponse = new ApiResponse<>(
                true,
                "Department deactivated successfully.",
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(apiResponse);
    }
}