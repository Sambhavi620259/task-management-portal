package com.company.taskportal.dto;

import com.company.taskportal.entity.AttachmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskAttachmentRequest {

    @NotNull(message = "Task ID is required.")
    private Long taskId;

    @NotNull(message = "Uploaded by employee ID is required.")
    private Long uploadedById;

    @NotBlank(message = "File name is required.")
    private String fileName;

    @NotBlank(message = "Original file name is required.")
    private String originalFileName;

    @NotBlank(message = "File URL is required.")
    private String fileUrl;

    @NotBlank(message = "Content type is required.")
    private String contentType;

    @NotNull(message = "File size is required.")
    private Long fileSize;

    @NotNull(message = "Attachment type is required.")
    private AttachmentType attachmentType;

}