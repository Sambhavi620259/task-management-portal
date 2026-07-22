package com.company.taskportal.service;

import com.company.taskportal.dto.TaskHistoryRequest;
import com.company.taskportal.dto.TaskHistoryResponse;
import com.company.taskportal.entity.Employee;
import com.company.taskportal.entity.Task;
import com.company.taskportal.entity.TaskActionType;
import com.company.taskportal.entity.TaskHistory;
import com.company.taskportal.exception.ResourceNotFoundException;
import com.company.taskportal.repository.EmployeeRepository;
import com.company.taskportal.repository.TaskHistoryRepository;
import com.company.taskportal.repository.TaskRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskHistoryServiceImpl implements TaskHistoryService {

    private final TaskHistoryRepository taskHistoryRepository;
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

    private TaskHistory mapToEntity(TaskHistoryRequest request) {
        TaskHistory history = new TaskHistory();

        history.setTask(getTask(request.getTaskId()));
        history.setPerformedBy(getEmployee(request.getPerformedById()));

        history.setActionType(request.getActionType());
        history.setDescription(request.getDescription());
        history.setOldValue(request.getOldValue());
        history.setNewValue(request.getNewValue());

        return history;
    }

    private TaskHistoryResponse mapToResponse(TaskHistory history) {
        TaskHistoryResponse response = new TaskHistoryResponse();

        response.setId(history.getId());

        // Task Information
        if (history.getTask() != null) {
            response.setTaskId(history.getTask().getId());
            response.setTaskCode(history.getTask().getTaskCode());
            response.setTaskName(history.getTask().getTaskName());
        }

        // Employee Information
        if (history.getPerformedBy() != null) {
            Employee emp = history.getPerformedBy();
            response.setPerformedById(emp.getId());
            response.setEmployeeCode(emp.getEmployeeCode());

            // Null-safe name formatting
            String fullName = Stream.of(emp.getFirstName(), emp.getLastName())
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(" "))
                    .trim();

            response.setEmployeeName(fullName);
        }

        // Audit Information
        response.setActionType(history.getActionType());
        response.setDescription(history.getDescription());
        response.setOldValue(history.getOldValue());
        response.setNewValue(history.getNewValue());

        // BaseEntity Information
        response.setActive(history.getActive());
        response.setDeleted(history.getDeleted());
        response.setCreatedAt(history.getCreatedAt());
        response.setUpdatedAt(history.getUpdatedAt());

        return response;
    }

    @Override
    public TaskHistoryResponse logHistory(TaskHistoryRequest request) {
        TaskHistory history = mapToEntity(request);
        history = taskHistoryRepository.save(history);
        return mapToResponse(history);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskHistoryResponse getHistoryById(Long historyId) {
        TaskHistory history = taskHistoryRepository
                .findByIdAndDeletedFalse(historyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task history not found with id : " + historyId));

        return mapToResponse(history);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskHistoryResponse> getAllHistory() {
        return taskHistoryRepository.findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskHistoryResponse> getHistoryByTask(Long taskId) {
        Task task = getTask(taskId);

        return taskHistoryRepository.findByTaskAndDeletedFalse(task)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskHistoryResponse> getHistoryByPerformedBy(Long employeeId) {
        Employee employee = getEmployee(employeeId);

        return taskHistoryRepository.findByPerformedByAndDeletedFalse(employee)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskHistoryResponse> getHistoryByActionType(TaskActionType actionType) {
        return taskHistoryRepository.findByActionTypeAndDeletedFalse(actionType)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskHistoryResponse> getHistoryByTaskAndActionType(
            Long taskId,
            TaskActionType actionType) {

        Task task = getTask(taskId);

        return taskHistoryRepository
                .findByTaskAndActionTypeAndDeletedFalse(task, actionType)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskHistoryResponse> getHistoryBetweenDates(
            LocalDateTime startDate,
            LocalDateTime endDate) {

        return taskHistoryRepository
                .findByCreatedAtBetweenAndDeletedFalse(startDate, endDate)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskHistoryResponse> getHistoryByTaskBetweenDates(
            Long taskId,
            LocalDateTime startDate,
            LocalDateTime endDate) {

        Task task = getTask(taskId);

        return taskHistoryRepository
                .findByTaskAndCreatedAtBetweenAndDeletedFalse(
                        task,
                        startDate,
                        endDate
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public long countHistoryByTask(Long taskId) {
        Task task = getTask(taskId);
        return taskHistoryRepository.countByTaskAndDeletedFalse(task);
    }

    @Override
    @Transactional(readOnly = true)
    public long countHistoryByPerformedBy(Long employeeId) {
        Employee employee = getEmployee(employeeId);
        return taskHistoryRepository.countByPerformedByAndDeletedFalse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public long countHistoryByActionType(TaskActionType actionType) {
        return taskHistoryRepository.countByActionTypeAndDeletedFalse(actionType);
    }

    @Override
    public void activateHistory(Long historyId) {
        TaskHistory history = taskHistoryRepository
                .findByIdAndDeletedFalse(historyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task history not found with id : " + historyId));

        history.setActive(true);
        taskHistoryRepository.save(history);
    }

    @Override
    public void deactivateHistory(Long historyId) {
        TaskHistory history = taskHistoryRepository
                .findByIdAndDeletedFalse(historyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task history not found with id : " + historyId));

        history.setActive(false);
        taskHistoryRepository.save(history);
    }

    @Override
    public void deleteHistory(Long historyId) {
        TaskHistory history = taskHistoryRepository
                .findByIdAndDeletedFalse(historyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task history not found with id : " + historyId));

        history.setDeleted(true);
        history.setActive(false);

        taskHistoryRepository.save(history);
    }
}