package com.company.taskportal.service;

import com.company.taskportal.dto.TaskCommentRequest;
import com.company.taskportal.dto.TaskCommentResponse;
import com.company.taskportal.entity.CommentType;

import java.util.List;

public interface TaskCommentService {

    TaskCommentResponse createComment(TaskCommentRequest request);

    TaskCommentResponse updateComment(Long commentId, TaskCommentRequest request);

    TaskCommentResponse getCommentById(Long commentId);

    List<TaskCommentResponse> getAllComments();

    List<TaskCommentResponse> getCommentsByTask(Long taskId);

    List<TaskCommentResponse> getCommentsByEmployee(Long employeeId);

    List<TaskCommentResponse> getCommentsByCommentType(CommentType commentType);

    List<TaskCommentResponse> getCommentsByTaskAndCommentType(
            Long taskId,
            CommentType commentType
    );

    long countCommentsByTask(Long taskId);

    long countCommentsByEmployee(Long employeeId);

    void activateComment(Long commentId);

    void deactivateComment(Long commentId);

    void deleteComment(Long commentId);

}