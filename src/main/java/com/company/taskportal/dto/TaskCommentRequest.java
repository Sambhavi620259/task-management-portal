package com.company.taskportal.dto;

import com.company.taskportal.entity.CommentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskCommentRequest {

    @NotNull(message = "Task ID is required.")
    private Long taskId;

    @NotNull(message = "Employee ID is required.")
    private Long employeeId;

    @NotBlank(message = "Comment is required.")
    @Size(max = 2000, message = "Comment cannot exceed 2000 characters.")
    private String comment;

    @NotNull(message = "Comment type is required.")
    private CommentType commentType;

}