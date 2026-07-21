package com.company.taskportal.controller;

import com.company.taskportal.common.ApiResponse;
import com.company.taskportal.dto.EmployeeRequest;
import com.company.taskportal.dto.EmployeeResponse;
import com.company.taskportal.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Create Employee
     */
    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponse>> createEmployee(
            @Valid @RequestBody EmployeeRequest request) {

        EmployeeResponse response = employeeService.createEmployee(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "Employee created successfully.",
                        response
                ));
    }

    /**
     * Update Employee
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {

        EmployeeResponse response = employeeService.updateEmployee(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Employee updated successfully.",
                        response
                )
        );
    }

    /**
     * Get Employee By Id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployeeById(
            @PathVariable Long id) {

        EmployeeResponse response = employeeService.getEmployeeById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Employee fetched successfully.",
                        response
                )
        );
    }

    /**
     * Get All Employees
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getAllEmployees() {

        List<EmployeeResponse> response = employeeService.getAllEmployees();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Employees fetched successfully.",
                        response
                )
        );
    }

    /**
     * Get All Active Employees
     */
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getActiveEmployees() {

        List<EmployeeResponse> response = employeeService.getAllActiveEmployees();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Active employees fetched successfully.",
                        response
                )
        );
    }

    /**
     * Get Employees By Organization
     */
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getEmployeesByOrganization(
            @PathVariable Long organizationId) {

        List<EmployeeResponse> response =
                employeeService.getEmployeesByOrganization(organizationId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Employees fetched successfully.",
                        response
                )
        );
    }

    /**
     * Get Employees By Department
     */
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getEmployeesByDepartment(
            @PathVariable Long departmentId) {

        List<EmployeeResponse> response =
                employeeService.getEmployeesByDepartment(departmentId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Employees fetched successfully.",
                        response
                )
        );
    }

    /**
     * Activate Employee
     */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<String>> activateEmployee(
            @PathVariable Long id) {

        employeeService.activateEmployee(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Employee activated successfully.",
                        null
                )
        );
    }

    /**
     * Deactivate Employee
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<String>> deactivateEmployee(
            @PathVariable Long id) {

        employeeService.deactivateEmployee(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Employee deactivated successfully.",
                        null
                )
        );
    }

    /**
     * Soft Delete Employee
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(
            @PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Employee deleted successfully.",
                        null
                )
        );
    }

}