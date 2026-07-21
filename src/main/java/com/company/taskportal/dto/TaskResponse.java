package com.company.taskportal.dto;

import com.company.taskportal.entity.Priority;
import com.company.taskportal.entity.TaskStatus;
import com.company.taskportal.entity.TaskType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class TaskResponse {

    private Long id;

    private String taskCode;

    private String taskName;

    private String description;

    // Organization
    private Long organizationId;
    private String organizationName;

    // Department
    private Long departmentId;
    private String departmentName;

    // Project
    private Long projectId;
    private String projectName;

    // Category
    private Long taskCategoryId;
    private String taskCategoryName;

    // Frequency
    private Long frequencyId;
    private String frequencyName;

    // Assignment
    private Long assignedToId;
    private String assignedToName;

    private Long createdById;
    private String createdByName;

    private Long reviewedById;
    private String reviewedByName;

    private Long approvedById;
    private String approvedByName;

    // Task Configuration
    private Priority priority;

    private TaskStatus status;

    private TaskType taskType;

    // Scheduling
    private LocalDate startDate;

    private LocalDate dueDate;

    private LocalDate completedDate;

    private Integer estimatedHours;

    private Integer actualHours;

    // Workflow
    private Boolean workflowRequired;

    private Boolean approvalRequired;

    private Integer workflowStep;

    // Progress
    private Integer completionPercentage;

    // SLA
    private Integer slaHours;

    private Integer reminderBeforeHours;

    private Integer escalationAfterHours;

    // Notification
    private Boolean emailNotification;

    private Boolean smsNotification;

    private Boolean pushNotification;

    // Recurring
    private LocalDate nextExecutionDate;

    private LocalDate lastExecutionDate;

    private Boolean autoGenerateNext;

    // BaseEntity Fields
    private Boolean active;

    private Boolean deleted;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}