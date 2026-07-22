package com.company.taskportal.service;

import com.company.taskportal.dto.TaskTimeLogRequest;
import com.company.taskportal.dto.TaskTimeLogResponse;

import java.time.LocalDate;
import java.util.List;

public interface TaskTimeLogService {

    TaskTimeLogResponse createTimeLog(TaskTimeLogRequest request);

    TaskTimeLogResponse updateTimeLog(
            Long timeLogId,
            TaskTimeLogRequest request
    );

    TaskTimeLogResponse getTimeLogById(Long timeLogId);

    List<TaskTimeLogResponse> getAllTimeLogs();

    List<TaskTimeLogResponse> getTimeLogsByTask(Long taskId);

    List<TaskTimeLogResponse> getTimeLogsByEmployee(Long employeeId);

    List<TaskTimeLogResponse> getTimeLogsByWorkDate(LocalDate workDate);

    List<TaskTimeLogResponse> getTimeLogsByTaskAndWorkDate(
            Long taskId,
            LocalDate workDate
    );

    List<TaskTimeLogResponse> getTimeLogsByEmployeeAndWorkDate(
            Long employeeId,
            LocalDate workDate
    );

    List<TaskTimeLogResponse> getTimeLogsBetweenDates(
            LocalDate startDate,
            LocalDate endDate
    );

    List<TaskTimeLogResponse> getTimeLogsByTaskBetweenDates(
            Long taskId,
            LocalDate startDate,
            LocalDate endDate
    );

    List<TaskTimeLogResponse> getTimeLogsByEmployeeBetweenDates(
            Long employeeId,
            LocalDate startDate,
            LocalDate endDate
    );

    long countByTask(Long taskId);

    long countByEmployee(Long employeeId);

    long countByWorkDate(LocalDate workDate);

    void activateTimeLog(Long timeLogId);

    void deactivateTimeLog(Long timeLogId);

    void deleteTimeLog(Long timeLogId);

}