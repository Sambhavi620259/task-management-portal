package com.company.taskportal.service;

import com.company.taskportal.dto.TaskChecklistRequest;
import com.company.taskportal.dto.TaskChecklistResponse;

import java.util.List;

public interface TaskChecklistService {

    TaskChecklistResponse createChecklist(TaskChecklistRequest request);

    TaskChecklistResponse updateChecklist(
            Long checklistId,
            TaskChecklistRequest request
    );

    TaskChecklistResponse getChecklistById(Long checklistId);

    List<TaskChecklistResponse> getAllChecklists();

    List<TaskChecklistResponse> getChecklistsByTask(Long taskId);

    List<TaskChecklistResponse> getChecklistsByAssignedEmployee(Long employeeId);

    List<TaskChecklistResponse> getCompletedChecklists();

    List<TaskChecklistResponse> getPendingChecklists();

    List<TaskChecklistResponse> getCompletedChecklistsByTask(Long taskId);

    List<TaskChecklistResponse> getPendingChecklistsByTask(Long taskId);

    List<TaskChecklistResponse> getCompletedChecklistsByEmployee(Long employeeId);

    List<TaskChecklistResponse> getPendingChecklistsByEmployee(Long employeeId);

    long countByTask(Long taskId);

    long countCompletedByTask(Long taskId);

    long countPendingByTask(Long taskId);

    long countByEmployee(Long employeeId);

    long countCompletedByEmployee(Long employeeId);

    long countPendingByEmployee(Long employeeId);

    TaskChecklistResponse markCompleted(Long checklistId);

    TaskChecklistResponse markPending(Long checklistId);

    void activateChecklist(Long checklistId);

    void deactivateChecklist(Long checklistId);

    void deleteChecklist(Long checklistId);

}