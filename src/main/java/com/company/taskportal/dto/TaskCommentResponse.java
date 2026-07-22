package com.company.taskportal.dto;

import com.company.taskportal.entity.CommentType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskCommentResponse {

    private Long id;

    // Task
    private Long taskId;
    private String taskCode;
    private String taskName;

    // Employee
    private Long employeeId;
    private String employeeCode;
    private String employeeName;

    // Comment
    private String comment;
    private CommentType commentType;

    // Audit
    private Boolean active;
    private Boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}