package com.company.taskportal.controller;

import com.company.taskportal.dto.TaskAttachmentRequest;
import com.company.taskportal.dto.TaskAttachmentResponse;
import com.company.taskportal.entity.AttachmentType;
import com.company.taskportal.service.TaskAttachmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-attachments")
@RequiredArgsConstructor
public class TaskAttachmentController {

    private final TaskAttachmentService taskAttachmentService;

    @PostMapping
    public ResponseEntity<TaskAttachmentResponse> createAttachment(
            @Valid @RequestBody TaskAttachmentRequest request) {

        return new ResponseEntity<>(
                taskAttachmentService.createAttachment(request),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{attachmentId}")
    public ResponseEntity<TaskAttachmentResponse> updateAttachment(
            @PathVariable Long attachmentId,
            @Valid @RequestBody TaskAttachmentRequest request) {

        return ResponseEntity.ok(
                taskAttachmentService.updateAttachment(
                        attachmentId,
                        request
                )
        );
    }

    @GetMapping("/{attachmentId}")
    public ResponseEntity<TaskAttachmentResponse> getAttachmentById(
            @PathVariable Long attachmentId) {

        return ResponseEntity.ok(
                taskAttachmentService.getAttachmentById(attachmentId)
        );
    }

    @GetMapping
    public ResponseEntity<List<TaskAttachmentResponse>> getAllAttachments() {

        return ResponseEntity.ok(
                taskAttachmentService.getAllAttachments()
        );
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<TaskAttachmentResponse>> getAttachmentsByTask(
            @PathVariable Long taskId) {

        return ResponseEntity.ok(
                taskAttachmentService.getAttachmentsByTask(taskId)
        );
    }

    @GetMapping("/uploaded-by/{uploadedById}")
    public ResponseEntity<List<TaskAttachmentResponse>> getAttachmentsByUploadedBy(
            @PathVariable Long uploadedById) {

        return ResponseEntity.ok(
                taskAttachmentService.getAttachmentsByUploadedBy(uploadedById)
        );
    }

    @GetMapping("/type/{attachmentType}")
    public ResponseEntity<List<TaskAttachmentResponse>> getAttachmentsByAttachmentType(
            @PathVariable AttachmentType attachmentType) {

        return ResponseEntity.ok(
                taskAttachmentService.getAttachmentsByAttachmentType(
                        attachmentType
                )
        );
    }

    @GetMapping("/task/{taskId}/type/{attachmentType}")
    public ResponseEntity<List<TaskAttachmentResponse>> getAttachmentsByTaskAndAttachmentType(
            @PathVariable Long taskId,
            @PathVariable AttachmentType attachmentType) {

        return ResponseEntity.ok(
                taskAttachmentService.getAttachmentsByTaskAndAttachmentType(
                        taskId,
                        attachmentType
                )
        );
    }

    @GetMapping("/task/{taskId}/count")
    public ResponseEntity<Long> countAttachmentsByTask(
            @PathVariable Long taskId) {

        return ResponseEntity.ok(
                taskAttachmentService.countAttachmentsByTask(taskId)
        );
    }

    @GetMapping("/uploaded-by/{uploadedById}/count")
    public ResponseEntity<Long> countAttachmentsByUploadedBy(
            @PathVariable Long uploadedById) {

        return ResponseEntity.ok(
                taskAttachmentService.countAttachmentsByUploadedBy(
                        uploadedById
                )
        );
    }

    @PatchMapping("/{attachmentId}/activate")
    public ResponseEntity<Void> activateAttachment(
            @PathVariable Long attachmentId) {

        taskAttachmentService.activateAttachment(attachmentId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{attachmentId}/deactivate")
    public ResponseEntity<Void> deactivateAttachment(
            @PathVariable Long attachmentId) {

        taskAttachmentService.deactivateAttachment(attachmentId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{attachmentId}")
    public ResponseEntity<Void> deleteAttachment(
            @PathVariable Long attachmentId) {

        taskAttachmentService.deleteAttachment(attachmentId);

        return ResponseEntity.noContent().build();
    }

}