package com.company.taskportal.controller;

import com.company.taskportal.dto.TaskCommentRequest;
import com.company.taskportal.dto.TaskCommentResponse;
import com.company.taskportal.entity.CommentType;
import com.company.taskportal.service.TaskCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-comments")
@RequiredArgsConstructor
public class TaskCommentController {

    private final TaskCommentService taskCommentService;

    @PostMapping
    public ResponseEntity<TaskCommentResponse> createComment(
            @Valid @RequestBody TaskCommentRequest request) {

        return new ResponseEntity<>(
                taskCommentService.createComment(request),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<TaskCommentResponse> updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody TaskCommentRequest request) {

        return ResponseEntity.ok(
                taskCommentService.updateComment(commentId, request)
        );
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<TaskCommentResponse> getCommentById(
            @PathVariable Long commentId) {

        return ResponseEntity.ok(
                taskCommentService.getCommentById(commentId)
        );
    }

    @GetMapping
    public ResponseEntity<List<TaskCommentResponse>> getAllComments() {

        return ResponseEntity.ok(
                taskCommentService.getAllComments()
        );
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<TaskCommentResponse>> getCommentsByTask(
            @PathVariable Long taskId) {

        return ResponseEntity.ok(
                taskCommentService.getCommentsByTask(taskId)
        );
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<TaskCommentResponse>> getCommentsByEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                taskCommentService.getCommentsByEmployee(employeeId)
        );
    }

    @GetMapping("/type/{commentType}")
    public ResponseEntity<List<TaskCommentResponse>> getCommentsByCommentType(
            @PathVariable CommentType commentType) {

        return ResponseEntity.ok(
                taskCommentService.getCommentsByCommentType(commentType)
        );
    }

    @GetMapping("/task/{taskId}/type/{commentType}")
    public ResponseEntity<List<TaskCommentResponse>> getCommentsByTaskAndCommentType(
            @PathVariable Long taskId,
            @PathVariable CommentType commentType) {

        return ResponseEntity.ok(
                taskCommentService.getCommentsByTaskAndCommentType(taskId, commentType)
        );
    }

    @GetMapping("/task/{taskId}/count")
    public ResponseEntity<Long> countCommentsByTask(
            @PathVariable Long taskId) {

        return ResponseEntity.ok(
                taskCommentService.countCommentsByTask(taskId)
        );
    }

    @GetMapping("/employee/{employeeId}/count")
    public ResponseEntity<Long> countCommentsByEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                taskCommentService.countCommentsByEmployee(employeeId)
        );
    }

    @PatchMapping("/{commentId}/activate")
    public ResponseEntity<Void> activateComment(
            @PathVariable Long commentId) {

        taskCommentService.activateComment(commentId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{commentId}/deactivate")
    public ResponseEntity<Void> deactivateComment(
            @PathVariable Long commentId) {

        taskCommentService.deactivateComment(commentId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId) {

        taskCommentService.deleteComment(commentId);

        return ResponseEntity.noContent().build();
    }

}