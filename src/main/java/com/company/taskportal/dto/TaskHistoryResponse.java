package com.company.taskportal.dto;

import com.company.taskportal.entity.TaskActionType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskHistoryResponse {

    private Long id;

    // Task Information
    private Long taskId;
    private String taskCode;
    private String taskName;

    // Employee Information
    private Long performedById;
    private String employeeCode;
    private String employeeName;

    // Audit Information
    private TaskActionType actionType;
    private String description;
    private String oldValue;
    private String newValue;

    // BaseEntity Information
    private Boolean active;
    private Boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}