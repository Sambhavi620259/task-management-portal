package com.company.taskportal.service;

import com.company.taskportal.dto.TaskCommentRequest;
import com.company.taskportal.dto.TaskCommentResponse;
import com.company.taskportal.entity.CommentType;
import com.company.taskportal.entity.Employee;
import com.company.taskportal.entity.Task;
import com.company.taskportal.entity.TaskComment;
import com.company.taskportal.exception.ResourceNotFoundException;
import com.company.taskportal.repository.EmployeeRepository;
import com.company.taskportal.repository.TaskCommentRepository;
import com.company.taskportal.repository.TaskRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskCommentServiceImpl implements TaskCommentService {

    private final TaskCommentRepository taskCommentRepository;
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    private Task getTask(Long id) {
        return taskRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task not found with id : " + id));
    }

    private Employee getEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id : " + id));
    }

    private TaskComment mapToEntity(TaskCommentRequest request) {

        TaskComment comment = new TaskComment();

        comment.setTask(getTask(request.getTaskId()));
        comment.setEmployee(getEmployee(request.getEmployeeId()));

        comment.setComment(request.getComment());
        comment.setCommentType(request.getCommentType());

        return comment;
    }

    private TaskCommentResponse mapToResponse(TaskComment comment) {

        TaskCommentResponse response = new TaskCommentResponse();

        response.setId(comment.getId());

        // Task
        if (comment.getTask() != null) {
            response.setTaskId(comment.getTask().getId());
            response.setTaskCode(comment.getTask().getTaskCode());
            response.setTaskName(comment.getTask().getTaskName());
        }

        // Employee
        if (comment.getEmployee() != null) {

            response.setEmployeeId(comment.getEmployee().getId());

            response.setEmployeeCode(
                    comment.getEmployee().getEmployeeCode());

            response.setEmployeeName(
                    comment.getEmployee().getFirstName()
                            + " "
                            + comment.getEmployee().getLastName());
        }

        // Comment
        response.setComment(comment.getComment());
        response.setCommentType(comment.getCommentType());

        // Audit
        response.setActive(comment.getActive());
        response.setDeleted(comment.getDeleted());
        response.setCreatedAt(comment.getCreatedAt());
        response.setUpdatedAt(comment.getUpdatedAt());

        return response;
    }
    @Override
    public TaskCommentResponse createComment(TaskCommentRequest request) {

        TaskComment comment = mapToEntity(request);

        comment = taskCommentRepository.save(comment);

        return mapToResponse(comment);
    }

    @Override
    public TaskCommentResponse updateComment(Long commentId,
                                             TaskCommentRequest request) {

        TaskComment comment = taskCommentRepository
                .findByIdAndDeletedFalse(commentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task comment not found with id : " + commentId));

        comment.setTask(getTask(request.getTaskId()));
        comment.setEmployee(getEmployee(request.getEmployeeId()));
        comment.setComment(request.getComment());
        comment.setCommentType(request.getCommentType());

        comment = taskCommentRepository.save(comment);

        return mapToResponse(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskCommentResponse getCommentById(Long commentId) {

        TaskComment comment = taskCommentRepository
                .findByIdAndDeletedFalse(commentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task comment not found with id : " + commentId));

        return mapToResponse(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskCommentResponse> getAllComments() {

        return taskCommentRepository.findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public List<TaskCommentResponse> getCommentsByTask(Long taskId) {

        Task task = getTask(taskId);

        return taskCommentRepository.findByTaskAndDeletedFalse(task)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskCommentResponse> getCommentsByEmployee(Long employeeId) {

        Employee employee = getEmployee(employeeId);

        return taskCommentRepository.findByEmployeeAndDeletedFalse(employee)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskCommentResponse> getCommentsByCommentType(CommentType commentType) {

        return taskCommentRepository.findByCommentTypeAndDeletedFalse(commentType)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskCommentResponse> getCommentsByTaskAndCommentType(
            Long taskId,
            CommentType commentType) {

        Task task = getTask(taskId);

        return taskCommentRepository
                .findByTaskAndCommentTypeAndDeletedFalse(task, commentType)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }    @Override
    @Transactional(readOnly = true)
    public long countCommentsByTask(Long taskId) {

        Task task = getTask(taskId);

        return taskCommentRepository.countByTaskAndDeletedFalse(task);
    }

    @Override
    @Transactional(readOnly = true)
    public long countCommentsByEmployee(Long employeeId) {

        Employee employee = getEmployee(employeeId);

        return taskCommentRepository.countByEmployeeAndDeletedFalse(employee);
    }

    @Override
    public void activateComment(Long commentId) {

        TaskComment comment = taskCommentRepository
                .findByIdAndDeletedFalse(commentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task comment not found with id : " + commentId));

        comment.setActive(true);

        taskCommentRepository.save(comment);
    }

    @Override
    public void deactivateComment(Long commentId) {

        TaskComment comment = taskCommentRepository
                .findByIdAndDeletedFalse(commentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task comment not found with id : " + commentId));

        comment.setActive(false);

        taskCommentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {

        TaskComment comment = taskCommentRepository
                .findByIdAndDeletedFalse(commentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task comment not found with id : " + commentId));

        comment.setDeleted(true);
        comment.setActive(false);

        taskCommentRepository.save(comment);
    }

}