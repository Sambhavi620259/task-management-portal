package com.company.taskportal.controller;

import com.company.taskportal.dto.TaskHistoryResponse;
import com.company.taskportal.entity.TaskActionType;
import com.company.taskportal.service.TaskHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/task-history")
@RequiredArgsConstructor
public class TaskHistoryController {

    private final TaskHistoryService taskHistoryService;

    @GetMapping("/{historyId}")
    public ResponseEntity<TaskHistoryResponse> getHistoryById(
            @PathVariable Long historyId) {

        return ResponseEntity.ok(
                taskHistoryService.getHistoryById(historyId)
        );
    }

    @GetMapping
    public ResponseEntity<List<TaskHistoryResponse>> getAllHistory() {

        return ResponseEntity.ok(
                taskHistoryService.getAllHistory()
        );
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<TaskHistoryResponse>> getHistoryByTask(
            @PathVariable Long taskId) {

        return ResponseEntity.ok(
                taskHistoryService.getHistoryByTask(taskId)
        );
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<TaskHistoryResponse>> getHistoryByPerformedBy(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                taskHistoryService.getHistoryByPerformedBy(employeeId)
        );
    }

    @GetMapping("/action/{actionType}")
    public ResponseEntity<List<TaskHistoryResponse>> getHistoryByActionType(
            @PathVariable TaskActionType actionType) {

        return ResponseEntity.ok(
                taskHistoryService.getHistoryByActionType(actionType)
        );
    }

    @GetMapping("/task/{taskId}/action/{actionType}")
    public ResponseEntity<List<TaskHistoryResponse>> getHistoryByTaskAndActionType(
            @PathVariable Long taskId,
            @PathVariable TaskActionType actionType) {

        return ResponseEntity.ok(
                taskHistoryService.getHistoryByTaskAndActionType(
                        taskId,
                        actionType
                )
        );
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<TaskHistoryResponse>> getHistoryBetweenDates(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime endDate) {

        return ResponseEntity.ok(
                taskHistoryService.getHistoryBetweenDates(
                        startDate,
                        endDate
                )
        );
    }

    @GetMapping("/task/{taskId}/date-range")
    public ResponseEntity<List<TaskHistoryResponse>> getHistoryByTaskBetweenDates(

            @PathVariable Long taskId,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime endDate) {

        return ResponseEntity.ok(
                taskHistoryService.getHistoryByTaskBetweenDates(
                        taskId,
                        startDate,
                        endDate
                )
        );
    }

    @GetMapping("/task/{taskId}/count")
    public ResponseEntity<Long> countHistoryByTask(
            @PathVariable Long taskId) {

        return ResponseEntity.ok(
                taskHistoryService.countHistoryByTask(taskId)
        );
    }

    @GetMapping("/employee/{employeeId}/count")
    public ResponseEntity<Long> countHistoryByPerformedBy(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                taskHistoryService.countHistoryByPerformedBy(employeeId)
        );
    }

    @GetMapping("/action/{actionType}/count")
    public ResponseEntity<Long> countHistoryByActionType(
            @PathVariable TaskActionType actionType) {

        return ResponseEntity.ok(
                taskHistoryService.countHistoryByActionType(actionType)
        );
    }

}