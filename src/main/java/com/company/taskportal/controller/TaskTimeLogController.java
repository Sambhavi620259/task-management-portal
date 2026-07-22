package com.company.taskportal.controller;

import com.company.taskportal.dto.TaskTimeLogRequest;
import com.company.taskportal.dto.TaskTimeLogResponse;
import com.company.taskportal.service.TaskTimeLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/task-time-logs")
@RequiredArgsConstructor
public class TaskTimeLogController {

    private final TaskTimeLogService taskTimeLogService;

    @PostMapping
    public ResponseEntity<TaskTimeLogResponse> createTimeLog(
            @Valid @RequestBody TaskTimeLogRequest request) {

        return new ResponseEntity<>(
                taskTimeLogService.createTimeLog(request),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{timeLogId}")
    public ResponseEntity<TaskTimeLogResponse> updateTimeLog(
            @PathVariable Long timeLogId,
            @Valid @RequestBody TaskTimeLogRequest request) {

        return ResponseEntity.ok(
                taskTimeLogService.updateTimeLog(timeLogId, request)
        );
    }

    @GetMapping("/{timeLogId}")
    public ResponseEntity<TaskTimeLogResponse> getTimeLogById(
            @PathVariable Long timeLogId) {

        return ResponseEntity.ok(
                taskTimeLogService.getTimeLogById(timeLogId)
        );
    }

    @GetMapping
    public ResponseEntity<List<TaskTimeLogResponse>> getAllTimeLogs() {

        return ResponseEntity.ok(
                taskTimeLogService.getAllTimeLogs()
        );
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<TaskTimeLogResponse>> getTimeLogsByTask(
            @PathVariable Long taskId) {

        return ResponseEntity.ok(
                taskTimeLogService.getTimeLogsByTask(taskId)
        );
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<TaskTimeLogResponse>> getTimeLogsByEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                taskTimeLogService.getTimeLogsByEmployee(employeeId)
        );
    }

    @GetMapping("/work-date/{workDate}")
    public ResponseEntity<List<TaskTimeLogResponse>> getTimeLogsByWorkDate(
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate workDate) {

        return ResponseEntity.ok(
                taskTimeLogService.getTimeLogsByWorkDate(workDate)
        );
    }

    @GetMapping("/task/{taskId}/work-date/{workDate}")
    public ResponseEntity<List<TaskTimeLogResponse>> getTimeLogsByTaskAndWorkDate(
            @PathVariable Long taskId,
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate workDate) {

        return ResponseEntity.ok(
                taskTimeLogService.getTimeLogsByTaskAndWorkDate(
                        taskId,
                        workDate
                )
        );
    }

    @GetMapping("/employee/{employeeId}/work-date/{workDate}")
    public ResponseEntity<List<TaskTimeLogResponse>> getTimeLogsByEmployeeAndWorkDate(
            @PathVariable Long employeeId,
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate workDate) {

        return ResponseEntity.ok(
                taskTimeLogService.getTimeLogsByEmployeeAndWorkDate(
                        employeeId,
                        workDate
                )
        );
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<TaskTimeLogResponse>> getTimeLogsBetweenDates(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate) {

        return ResponseEntity.ok(
                taskTimeLogService.getTimeLogsBetweenDates(
                        startDate,
                        endDate
                )
        );
    }

    @GetMapping("/task/{taskId}/date-range")
    public ResponseEntity<List<TaskTimeLogResponse>> getTimeLogsByTaskBetweenDates(

            @PathVariable Long taskId,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate) {

        return ResponseEntity.ok(
                taskTimeLogService.getTimeLogsByTaskBetweenDates(
                        taskId,
                        startDate,
                        endDate
                )
        );
    }

    @GetMapping("/employee/{employeeId}/date-range")
    public ResponseEntity<List<TaskTimeLogResponse>> getTimeLogsByEmployeeBetweenDates(

            @PathVariable Long employeeId,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate) {

        return ResponseEntity.ok(
                taskTimeLogService.getTimeLogsByEmployeeBetweenDates(
                        employeeId,
                        startDate,
                        endDate
                )
        );
    }

    @GetMapping("/task/{taskId}/count")
    public ResponseEntity<Long> countByTask(
            @PathVariable Long taskId) {

        return ResponseEntity.ok(
                taskTimeLogService.countByTask(taskId)
        );
    }

    @GetMapping("/employee/{employeeId}/count")
    public ResponseEntity<Long> countByEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                taskTimeLogService.countByEmployee(employeeId)
        );
    }

    @GetMapping("/work-date/{workDate}/count")
    public ResponseEntity<Long> countByWorkDate(
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate workDate) {

        return ResponseEntity.ok(
                taskTimeLogService.countByWorkDate(workDate)
        );
    }

    @PatchMapping("/{timeLogId}/activate")
    public ResponseEntity<String> activateTimeLog(
            @PathVariable Long timeLogId) {

        taskTimeLogService.activateTimeLog(timeLogId);

        return ResponseEntity.ok("Time log activated successfully.");
    }

    @PatchMapping("/{timeLogId}/deactivate")
    public ResponseEntity<String> deactivateTimeLog(
            @PathVariable Long timeLogId) {

        taskTimeLogService.deactivateTimeLog(timeLogId);

        return ResponseEntity.ok("Time log deactivated successfully.");
    }

    @DeleteMapping("/{timeLogId}")
    public ResponseEntity<String> deleteTimeLog(
            @PathVariable Long timeLogId) {

        taskTimeLogService.deleteTimeLog(timeLogId);

        return ResponseEntity.ok("Time log deleted successfully.");
    }

}