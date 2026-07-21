package com.company.taskportal.controller;

import com.company.taskportal.common.ApiResponse;
import com.company.taskportal.dto.ProjectRequest;
import com.company.taskportal.dto.ProjectResponse;
import com.company.taskportal.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectResponse>> createProject(
            @Valid @RequestBody ProjectRequest request) {

        ProjectResponse response = projectService.createProject(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "Project created successfully.",
                        response
                ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectRequest request) {

        ProjectResponse response = projectService.updateProject(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Project updated successfully.",
                        response
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProjectById(
            @PathVariable Long id) {

        ProjectResponse response = projectService.getProjectById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Project fetched successfully.",
                        response
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getAllProjects() {

        List<ProjectResponse> response = projectService.getAllProjects();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Projects fetched successfully.",
                        response
                )
        );
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getAllActiveProjects() {

        List<ProjectResponse> response = projectService.getAllActiveProjects();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Active projects fetched successfully.",
                        response
                )
        );
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getProjectsByOrganization(
            @PathVariable Long organizationId) {

        List<ProjectResponse> response =
                projectService.getProjectsByOrganization(organizationId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Projects fetched successfully.",
                        response
                )
        );
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<Void>> activateProject(
            @PathVariable Long id) {

        projectService.activateProject(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Project activated successfully.",
                        null
                )
        );
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivateProject(
            @PathVariable Long id) {

        projectService.deactivateProject(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Project deactivated successfully.",
                        null
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(
            @PathVariable Long id) {

        projectService.deleteProject(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Project deleted successfully.",
                        null
                )
        );
    }
}