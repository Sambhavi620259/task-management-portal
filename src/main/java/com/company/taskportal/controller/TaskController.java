package com.company.taskportal.controller;

import com.company.taskportal.common.ApiResponse;
import com.company.taskportal.dto.TaskRequest;
import com.company.taskportal.dto.TaskResponse;
import com.company.taskportal.entity.Priority;
import com.company.taskportal.entity.TaskStatus;
import com.company.taskportal.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(
            @Valid @RequestBody TaskRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Task created successfully.",
                        taskService.createTask(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Task updated successfully.",
                        taskService.updateTask(id, request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> getTaskById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Task retrieved successfully.",
                        taskService.getTaskById(id)));
    }

    @GetMapping("/code/{taskCode}")
    public ResponseEntity<ApiResponse<TaskResponse>> getTaskByCode(
            @PathVariable String taskCode) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Task retrieved successfully.",
                        taskService.getTaskByCode(taskCode)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getAllTasks() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Tasks retrieved successfully.",
                        taskService.getAllTasks()));
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getTasksByOrganization(
            @PathVariable Long organizationId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Organization tasks retrieved successfully.",
                        taskService.getTasksByOrganization(organizationId)));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getTasksByDepartment(
            @PathVariable Long departmentId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Department tasks retrieved successfully.",
                        taskService.getTasksByDepartment(departmentId)));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getTasksByProject(
            @PathVariable Long projectId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Project tasks retrieved successfully.",
                        taskService.getTasksByProject(projectId)));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getTasksByCategory(
            @PathVariable Long categoryId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Category tasks retrieved successfully.",
                        taskService.getTasksByCategory(categoryId)));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getTasksByAssignedEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Employee tasks retrieved successfully.",
                        taskService.getTasksByAssignedEmployee(employeeId)));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getTasksByStatus(
            @PathVariable TaskStatus status) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Tasks retrieved successfully.",
                        taskService.getTasksByStatus(status)));
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getTasksByPriority(
            @PathVariable Priority priority) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Tasks retrieved successfully.",
                        taskService.getTasksByPriority(priority)));
    }

    @GetMapping("/overdue")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getOverdueTasks() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Overdue tasks retrieved successfully.",
                        taskService.getOverdueTasks()));
    }

    @PatchMapping("/{id}/progress/{percentage}")
    public ResponseEntity<ApiResponse<TaskResponse>> updateProgress(
            @PathVariable Long id,
            @PathVariable Integer percentage) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Task progress updated successfully.",
                        taskService.updateProgress(id, percentage)));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<TaskResponse>> completeTask(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Task completed successfully.",
                        taskService.completeTask(id)));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<TaskResponse>> approveTask(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Task approved successfully.",
                        taskService.approveTask(id)));
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<TaskResponse>> rejectTask(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Task rejected successfully.",
                        taskService.rejectTask(id)));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<String>> activateTask(
            @PathVariable Long id) {

        taskService.activateTask(id);

        return ResponseEntity.ok(
                ApiResponse.success("Task activated successfully."));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<String>> deactivateTask(
            @PathVariable Long id) {

        taskService.deactivateTask(id);

        return ResponseEntity.ok(
                ApiResponse.success("Task deactivated successfully."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTask(
            @PathVariable Long id) {

        taskService.deleteTask(id);

        return ResponseEntity.ok(
                ApiResponse.success("Task deleted successfully."));
    }
}