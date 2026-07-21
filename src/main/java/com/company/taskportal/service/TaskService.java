package com.company.taskportal.service;

import com.company.taskportal.dto.TaskRequest;
import com.company.taskportal.dto.TaskResponse;
import com.company.taskportal.entity.Priority;
import com.company.taskportal.entity.TaskStatus;

import java.util.List;

public interface TaskService {

    // CRUD
    TaskResponse createTask(TaskRequest request);

    TaskResponse updateTask(Long id, TaskRequest request);

    TaskResponse getTaskById(Long id);

    TaskResponse getTaskByCode(String taskCode);

    List<TaskResponse> getAllTasks();

    // Organization
    List<TaskResponse> getTasksByOrganization(Long organizationId);

    // Department
    List<TaskResponse> getTasksByDepartment(Long departmentId);

    // Project
    List<TaskResponse> getTasksByProject(Long projectId);

    // Category
    List<TaskResponse> getTasksByCategory(Long categoryId);

    // Employee
    List<TaskResponse> getTasksByAssignedEmployee(Long employeeId);

    // Status
    List<TaskResponse> getTasksByStatus(TaskStatus status);

    // Priority
    List<TaskResponse> getTasksByPriority(Priority priority);

    // Dashboard
    List<TaskResponse> getOverdueTasks();

    // Progress
    TaskResponse updateProgress(Long taskId, Integer completionPercentage);

    // Workflow
    TaskResponse completeTask(Long taskId);

    TaskResponse approveTask(Long taskId);

    TaskResponse rejectTask(Long taskId);

    // Activation
    void activateTask(Long taskId);

    void deactivateTask(Long taskId);

    // Soft Delete
    void deleteTask(Long taskId);
}