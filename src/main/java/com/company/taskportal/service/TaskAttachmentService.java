package com.company.taskportal.service;

import com.company.taskportal.dto.TaskAttachmentRequest;
import com.company.taskportal.dto.TaskAttachmentResponse;
import com.company.taskportal.entity.AttachmentType;

import java.util.List;

public interface TaskAttachmentService {

    TaskAttachmentResponse createAttachment(TaskAttachmentRequest request);

    TaskAttachmentResponse updateAttachment(
            Long attachmentId,
            TaskAttachmentRequest request
    );

    TaskAttachmentResponse getAttachmentById(Long attachmentId);

    List<TaskAttachmentResponse> getAllAttachments();

    List<TaskAttachmentResponse> getAttachmentsByTask(Long taskId);

    List<TaskAttachmentResponse> getAttachmentsByUploadedBy(Long uploadedById);

    List<TaskAttachmentResponse> getAttachmentsByAttachmentType(
            AttachmentType attachmentType
    );

    List<TaskAttachmentResponse> getAttachmentsByTaskAndAttachmentType(
            Long taskId,
            AttachmentType attachmentType
    );

    long countAttachmentsByTask(Long taskId);

    long countAttachmentsByUploadedBy(Long uploadedById);

    void activateAttachment(Long attachmentId);

    void deactivateAttachment(Long attachmentId);

    void deleteAttachment(Long attachmentId);

}