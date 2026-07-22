package com.company.taskportal.service;

import com.company.taskportal.dto.TaskHistoryRequest;
import com.company.taskportal.dto.TaskHistoryResponse;
import com.company.taskportal.entity.TaskActionType;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskHistoryService {

    /**
     * Internal method used by business services
     * to create audit trail entries.
     */
    TaskHistoryResponse logHistory(TaskHistoryRequest request);

    TaskHistoryResponse getHistoryById(Long historyId);

    List<TaskHistoryResponse> getAllHistory();

    List<TaskHistoryResponse> getHistoryByTask(Long taskId);

    List<TaskHistoryResponse> getHistoryByPerformedBy(Long employeeId);

    List<TaskHistoryResponse> getHistoryByActionType(
            TaskActionType actionType
    );

    List<TaskHistoryResponse> getHistoryByTaskAndActionType(
            Long taskId,
            TaskActionType actionType
    );

    List<TaskHistoryResponse> getHistoryBetweenDates(
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    List<TaskHistoryResponse> getHistoryByTaskBetweenDates(
            Long taskId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    long countHistoryByTask(Long taskId);

    long countHistoryByPerformedBy(Long employeeId);

    long countHistoryByActionType(
            TaskActionType actionType
    );

    void activateHistory(Long historyId);

    void deactivateHistory(Long historyId);

    void deleteHistory(Long historyId);

}