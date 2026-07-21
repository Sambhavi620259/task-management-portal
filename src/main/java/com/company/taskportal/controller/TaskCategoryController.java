package com.company.taskportal.controller;

import com.company.taskportal.common.ApiResponse;
import com.company.taskportal.dto.TaskCategoryRequest;
import com.company.taskportal.dto.TaskCategoryResponse;
import com.company.taskportal.service.TaskCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-categories")
@Tag(name = "Task Category", description = "Task Category Management APIs")
public class TaskCategoryController {

    private final TaskCategoryService taskCategoryService;

    public TaskCategoryController(TaskCategoryService taskCategoryService) {
        this.taskCategoryService = taskCategoryService;
    }

    @PostMapping
    @Operation(summary = "Create Task Category")
    public ResponseEntity<ApiResponse<TaskCategoryResponse>> createCategory(
            @Valid @RequestBody TaskCategoryRequest request) {

        TaskCategoryResponse response = taskCategoryService.createCategory(request);

        return ResponseEntity.ok(
                ApiResponse.success("Task category created successfully", response)
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Task Category")
    public ResponseEntity<ApiResponse<TaskCategoryResponse>> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody TaskCategoryRequest request) {

        TaskCategoryResponse response =
                taskCategoryService.updateCategory(id, request);

        return ResponseEntity.ok(
                ApiResponse.success("Task category updated successfully", response)
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Task Category By ID")
    public ResponseEntity<ApiResponse<TaskCategoryResponse>> getCategoryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        taskCategoryService.getCategoryById(id)
                )
        );
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get Task Category By Code")
    public ResponseEntity<ApiResponse<TaskCategoryResponse>> getCategoryByCode(
            @PathVariable String code) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        taskCategoryService.getCategoryByCode(code)
                )
        );
    }

    @GetMapping
    @Operation(summary = "Get All Task Categories")
    public ResponseEntity<ApiResponse<List<TaskCategoryResponse>>> getAllCategories() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        taskCategoryService.getAllCategories()
                )
        );
    }

    @GetMapping("/active")
    @Operation(summary = "Get Active Task Categories")
    public ResponseEntity<ApiResponse<List<TaskCategoryResponse>>> getAllActiveCategories() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        taskCategoryService.getAllActiveCategories()
                )
        );
    }

    @GetMapping("/organization/{organizationId}")
    @Operation(summary = "Get Categories By Organization")
    public ResponseEntity<ApiResponse<List<TaskCategoryResponse>>> getCategoriesByOrganization(
            @PathVariable Long organizationId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        taskCategoryService.getCategoriesByOrganization(organizationId)
                )
        );
    }

    @PutMapping("/{id}/activate")
    @Operation(summary = "Activate Task Category")
    public ResponseEntity<ApiResponse<String>> activateCategory(
            @PathVariable Long id) {

        taskCategoryService.activateCategory(id);

        return ResponseEntity.ok(
                ApiResponse.success("Task category activated successfully")
        );
    }

    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate Task Category")
    public ResponseEntity<ApiResponse<String>> deactivateCategory(
            @PathVariable Long id) {

        taskCategoryService.deactivateCategory(id);

        return ResponseEntity.ok(
                ApiResponse.success("Task category deactivated successfully")
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Task Category")
    public ResponseEntity<ApiResponse<String>> deleteCategory(
            @PathVariable Long id) {

        taskCategoryService.deleteCategory(id);

        return ResponseEntity.ok(
                ApiResponse.success("Task category deleted successfully")
        );
    }
}