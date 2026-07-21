package com.company.taskportal.dto;

import com.company.taskportal.entity.Priority;
import com.company.taskportal.entity.TaskStatus;
import com.company.taskportal.entity.TaskType;
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskRequest {

    @NotBlank(message = "Task code is required")
    @Size(max = 30)
    private String taskCode;

    @NotBlank(message = "Task name is required")
    @Size(max = 150)
    private String taskName;

    @Size(max = 2000)
    private String description;

    @NotNull(message = "Organization is required")
    private Long organizationId;

    @NotNull(message = "Department is required")
    private Long departmentId;

    @NotNull(message = "Project is required")
    private Long projectId;

    @NotNull(message = "Task category is required")
    private Long taskCategoryId;

    // Optional for ONE_TIME tasks
    private Long frequencyId;

    @NotNull(message = "Assigned employee is required")
    private Long assignedToId;

    private Long reviewedById;

    private Long approvedById;

    @NotNull(message = "Priority is required")
    private Priority priority;

    @NotNull(message = "Status is required")
    private TaskStatus status;

    @NotNull(message = "Task type is required")
    private TaskType taskType;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate dueDate;

    private Integer estimatedHours;

    private Integer actualHours;

    private Integer completionPercentage;

    private Boolean workflowRequired;

    private Boolean approvalRequired;

    private Integer workflowStep;

    private Integer slaHours;

    private Integer reminderBeforeHours;

    private Integer escalationAfterHours;

    private Boolean emailNotification;

    private Boolean smsNotification;

    private Boolean pushNotification;

    private LocalDate nextExecutionDate;

    private LocalDate lastExecutionDate;

    private Boolean autoGenerateNext;

}