package com.company.taskportal.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskTimeLogResponse {

    private Long id;

    // Task Information
    private Long taskId;
    private String taskCode;
    private String taskName;

    // Employee Information
    private Long employeeId;
    private String employeeCode;
    private String employeeName;

    // Time Log Information
    private LocalDate workDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long durationMinutes;
    private String description;

    // BaseEntity Information
    private Boolean active;
    private Boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}