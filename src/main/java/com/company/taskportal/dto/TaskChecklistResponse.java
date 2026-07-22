package com.company.taskportal.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskChecklistResponse {

    private Long id;

    // Task Information
    private Long taskId;
    private String taskCode;
    private String taskName;

    // Checklist Information
    private String title;
    private String description;
    private Boolean completed;

    // Assigned Employee Information
    private Long assignedToId;
    private String employeeCode;
    private String employeeName;

    // BaseEntity Information
    private Boolean active;
    private Boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}