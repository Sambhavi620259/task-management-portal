package com.company.taskportal.dto;

import com.company.taskportal.entity.AttachmentType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskAttachmentResponse {

    private Long id;

    // Task Information
    private Long taskId;
    private String taskCode;
    private String taskName;

    // Uploaded By Information
    private Long uploadedById;
    private String employeeCode;
    private String employeeName;

    // File Information
    private String fileName;
    private String originalFileName;
    private String fileUrl;
    private String contentType;
    private Long fileSize;
    private AttachmentType attachmentType;

    // Audit Information
    private Boolean active;
    private Boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}