package com.company.taskportal.service;

import com.company.taskportal.dto.TaskTimeLogRequest;
import com.company.taskportal.dto.TaskTimeLogResponse;
import com.company.taskportal.entity.Employee;
import com.company.taskportal.entity.Task;
import com.company.taskportal.entity.TaskTimeLog;
import com.company.taskportal.exception.ResourceNotFoundException;
import com.company.taskportal.repository.EmployeeRepository;
import com.company.taskportal.repository.TaskRepository;
import com.company.taskportal.repository.TaskTimeLogRepository;
import com.company.taskportal.service.TaskTimeLogService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskTimeLogServiceImpl implements TaskTimeLogService {

    private final TaskTimeLogRepository taskTimeLogRepository;
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

    private TaskTimeLog getTimeLog(Long id) {
        return taskTimeLogRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Time log not found with id : " + id));
    }

    private Long calculateDuration(TaskTimeLogRequest request) {
        if (request.getStartTime() == null || request.getEndTime() == null) {
            throw new IllegalArgumentException("Start time and end time cannot be null.");
        }

        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new IllegalArgumentException(
                    "End time must be after start time."
            );
        }

        return Duration.between(
                request.getStartTime(),
                request.getEndTime()
        ).toMinutes();
    }

    private TaskTimeLog mapToEntity(TaskTimeLogRequest request) {
        TaskTimeLog timeLog = new TaskTimeLog();

        timeLog.setTask(getTask(request.getTaskId()));
        timeLog.setEmployee(getEmployee(request.getEmployeeId()));

        timeLog.setWorkDate(request.getWorkDate());
        timeLog.setStartTime(request.getStartTime());
        timeLog.setEndTime(request.getEndTime());
        timeLog.setDurationMinutes(calculateDuration(request));
        timeLog.setDescription(request.getDescription());

        return timeLog;
    }

    private TaskTimeLogResponse mapToResponse(TaskTimeLog timeLog) {
        TaskTimeLogResponse response = new TaskTimeLogResponse();

        response.setId(timeLog.getId());

        // Task Information
        if (timeLog.getTask() != null) {
            response.setTaskId(timeLog.getTask().getId());
            response.setTaskCode(timeLog.getTask().getTaskCode());
            response.setTaskName(timeLog.getTask().getTaskName());
        }

        // Employee Information
        if (timeLog.getEmployee() != null) {
            Employee employee = timeLog.getEmployee();

            response.setEmployeeId(employee.getId());
            response.setEmployeeCode(employee.getEmployeeCode());

            String fullName = Stream.of(
                            employee.getFirstName(),
                            employee.getLastName()
                    )
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(" "))
                    .trim();

            response.setEmployeeName(fullName);
        }

        // Time Log Information
        response.setWorkDate(timeLog.getWorkDate());
        response.setStartTime(timeLog.getStartTime());
        response.setEndTime(timeLog.getEndTime());
        response.setDurationMinutes(timeLog.getDurationMinutes());
        response.setDescription(timeLog.getDescription());

        // BaseEntity Information
        response.setActive(timeLog.getActive());
        response.setDeleted(timeLog.getDeleted());
        response.setCreatedAt(timeLog.getCreatedAt());
        response.setUpdatedAt(timeLog.getUpdatedAt());

        return response;
    }

    @Override
    public TaskTimeLogResponse createTimeLog(TaskTimeLogRequest request) {
        TaskTimeLog timeLog = mapToEntity(request);
        timeLog = taskTimeLogRepository.save(timeLog);
        return mapToResponse(timeLog);
    }

    @Override
    public TaskTimeLogResponse updateTimeLog(
            Long timeLogId,
            TaskTimeLogRequest request) {

        TaskTimeLog timeLog = getTimeLog(timeLogId);

        timeLog.setTask(getTask(request.getTaskId()));
        timeLog.setEmployee(getEmployee(request.getEmployeeId()));

        timeLog.setWorkDate(request.getWorkDate());
        timeLog.setStartTime(request.getStartTime());
        timeLog.setEndTime(request.getEndTime());
        timeLog.setDurationMinutes(calculateDuration(request));
        timeLog.setDescription(request.getDescription());

        timeLog = taskTimeLogRepository.save(timeLog);

        return mapToResponse(timeLog);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskTimeLogResponse getTimeLogById(Long timeLogId) {
        return mapToResponse(getTimeLog(timeLogId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskTimeLogResponse> getAllTimeLogs() {
        return taskTimeLogRepository.findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskTimeLogResponse> getTimeLogsByTask(Long taskId) {
        Task task = getTask(taskId);

        return taskTimeLogRepository.findByTaskAndDeletedFalse(task)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskTimeLogResponse> getTimeLogsByEmployee(Long employeeId) {
        Employee employee = getEmployee(employeeId);

        return taskTimeLogRepository.findByEmployeeAndDeletedFalse(employee)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskTimeLogResponse> getTimeLogsByWorkDate(LocalDate workDate) {
        return taskTimeLogRepository.findByWorkDateAndDeletedFalse(workDate)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskTimeLogResponse> getTimeLogsByTaskAndWorkDate(
            Long taskId,
            LocalDate workDate) {

        Task task = getTask(taskId);

        return taskTimeLogRepository
                .findByTaskAndWorkDateAndDeletedFalse(task, workDate)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskTimeLogResponse> getTimeLogsByEmployeeAndWorkDate(
            Long employeeId,
            LocalDate workDate) {

        Employee employee = getEmployee(employeeId);

        return taskTimeLogRepository
                .findByEmployeeAndWorkDateAndDeletedFalse(employee, workDate)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskTimeLogResponse> getTimeLogsBetweenDates(
            LocalDate startDate,
            LocalDate endDate) {

        return taskTimeLogRepository
                .findByWorkDateBetweenAndDeletedFalse(startDate, endDate)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskTimeLogResponse> getTimeLogsByTaskBetweenDates(
            Long taskId,
            LocalDate startDate,
            LocalDate endDate) {

        Task task = getTask(taskId);

        return taskTimeLogRepository
                .findByTaskAndWorkDateBetweenAndDeletedFalse(
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
    public List<TaskTimeLogResponse> getTimeLogsByEmployeeBetweenDates(
            Long employeeId,
            LocalDate startDate,
            LocalDate endDate) {

        Employee employee = getEmployee(employeeId);

        return taskTimeLogRepository
                .findByEmployeeAndWorkDateBetweenAndDeletedFalse(
                        employee,
                        startDate,
                        endDate
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public long countByTask(Long taskId) {
        Task task = getTask(taskId);
        return taskTimeLogRepository.countByTaskAndDeletedFalse(task);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByEmployee(Long employeeId) {
        Employee employee = getEmployee(employeeId);
        return taskTimeLogRepository.countByEmployeeAndDeletedFalse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByWorkDate(LocalDate workDate) {
        return taskTimeLogRepository.countByWorkDateAndDeletedFalse(workDate);
    }

    @Override
    public void activateTimeLog(Long timeLogId) {
        TaskTimeLog timeLog = getTimeLog(timeLogId);
        timeLog.setActive(true);
        taskTimeLogRepository.save(timeLog);
    }

    @Override
    public void deactivateTimeLog(Long timeLogId) {
        TaskTimeLog timeLog = getTimeLog(timeLogId);
        timeLog.setActive(false);
        taskTimeLogRepository.save(timeLog);
    }

    @Override
    public void deleteTimeLog(Long timeLogId) {
        TaskTimeLog timeLog = getTimeLog(timeLogId);
        timeLog.setDeleted(true);
        timeLog.setActive(false);
        taskTimeLogRepository.save(timeLog);
    }
}