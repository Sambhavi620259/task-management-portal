package com.company.taskportal.controller;

import com.company.taskportal.dto.TaskReminderRequest;
import com.company.taskportal.dto.TaskReminderResponse;
import com.company.taskportal.entity.ReminderType;
import com.company.taskportal.service.TaskReminderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/task-reminders")
@RequiredArgsConstructor
public class TaskReminderController {

    private final TaskReminderService taskReminderService;

    @PostMapping
    public ResponseEntity<TaskReminderResponse> createReminder(
            @Valid @RequestBody TaskReminderRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        taskReminderService.createReminder(request)
                );
    }

    @PutMapping("/{reminderId}")
    public ResponseEntity<TaskReminderResponse> updateReminder(
            @PathVariable Long reminderId,
            @Valid @RequestBody TaskReminderRequest request
    ) {
        return ResponseEntity.ok(
                taskReminderService.updateReminder(
                        reminderId,
                        request
                )
        );
    }

    @GetMapping("/{reminderId}")
    public ResponseEntity<TaskReminderResponse> getReminderById(
            @PathVariable Long reminderId
    ) {
        return ResponseEntity.ok(
                taskReminderService.getReminderById(reminderId)
        );
    }

    @GetMapping
    public ResponseEntity<List<TaskReminderResponse>> getAllReminders() {
        return ResponseEntity.ok(
                taskReminderService.getAllReminders()
        );
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<TaskReminderResponse>> getRemindersByTask(
            @PathVariable Long taskId
    ) {
        return ResponseEntity.ok(
                taskReminderService.getRemindersByTask(taskId)
        );
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<TaskReminderResponse>> getRemindersByEmployee(
            @PathVariable Long employeeId
    ) {
        return ResponseEntity.ok(
                taskReminderService.getRemindersByEmployee(employeeId)
        );
    }

    @GetMapping("/type/{reminderType}")
    public ResponseEntity<List<TaskReminderResponse>> getRemindersByReminderType(
            @PathVariable ReminderType reminderType
    ) {
        return ResponseEntity.ok(
                taskReminderService.getRemindersByReminderType(reminderType)
        );
    }

    @GetMapping("/task/{taskId}/type/{reminderType}")
    public ResponseEntity<List<TaskReminderResponse>> getRemindersByTaskAndReminderType(
            @PathVariable Long taskId,
            @PathVariable ReminderType reminderType
    ) {
        return ResponseEntity.ok(
                taskReminderService.getRemindersByTaskAndReminderType(
                        taskId,
                        reminderType
                )
        );
    }

    @GetMapping("/employee/{employeeId}/type/{reminderType}")
    public ResponseEntity<List<TaskReminderResponse>> getRemindersByEmployeeAndReminderType(
            @PathVariable Long employeeId,
            @PathVariable ReminderType reminderType
    ) {
        return ResponseEntity.ok(
                taskReminderService.getRemindersByEmployeeAndReminderType(
                        employeeId,
                        reminderType
                )
        );
    }

    @GetMapping("/sent/{sent}")
    public ResponseEntity<List<TaskReminderResponse>> getRemindersBySentStatus(
            @PathVariable Boolean sent
    ) {
        return ResponseEntity.ok(
                taskReminderService.getRemindersBySentStatus(sent)
        );
    }

    @GetMapping("/task/{taskId}/sent/{sent}")
    public ResponseEntity<List<TaskReminderResponse>> getRemindersByTaskAndSentStatus(
            @PathVariable Long taskId,
            @PathVariable Boolean sent
    ) {
        return ResponseEntity.ok(
                taskReminderService.getRemindersByTaskAndSentStatus(
                        taskId,
                        sent
                )
        );
    }

    @GetMapping("/employee/{employeeId}/sent/{sent}")
    public ResponseEntity<List<TaskReminderResponse>> getRemindersByEmployeeAndSentStatus(
            @PathVariable Long employeeId,
            @PathVariable Boolean sent
    ) {
        return ResponseEntity.ok(
                taskReminderService.getRemindersByEmployeeAndSentStatus(
                        employeeId,
                        sent
                )
        );
    }

    @GetMapping("/due")
    public ResponseEntity<List<TaskReminderResponse>> getDueReminders() {
        return ResponseEntity.ok(
                taskReminderService.getDueReminders()
        );
    }

    @GetMapping("/between")
    public ResponseEntity<List<TaskReminderResponse>> getRemindersBetween(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end
    ) {
        return ResponseEntity.ok(
                taskReminderService.getRemindersBetween(
                        start,
                        end
                )
        );
    }

    @GetMapping("/count/task/{taskId}")
    public ResponseEntity<Long> countByTask(
            @PathVariable Long taskId
    ) {
        return ResponseEntity.ok(
                taskReminderService.countByTask(taskId)
        );
    }

    @GetMapping("/count/employee/{employeeId}")
    public ResponseEntity<Long> countByEmployee(
            @PathVariable Long employeeId
    ) {
        return ResponseEntity.ok(
                taskReminderService.countByEmployee(employeeId)
        );
    }

    @GetMapping("/count/type/{reminderType}")
    public ResponseEntity<Long> countByReminderType(
            @PathVariable ReminderType reminderType
    ) {
        return ResponseEntity.ok(
                taskReminderService.countByReminderType(reminderType)
        );
    }

    @GetMapping("/count/sent/{sent}")
    public ResponseEntity<Long> countBySentStatus(
            @PathVariable Boolean sent
    ) {
        return ResponseEntity.ok(
                taskReminderService.countBySentStatus(sent)
        );
    }

    @PatchMapping("/{reminderId}/mark-sent")
    public ResponseEntity<String> markAsSent(
            @PathVariable Long reminderId
    ) {
        taskReminderService.markAsSent(reminderId);

        return ResponseEntity.ok(
                "Task reminder marked as sent successfully."
        );
    }

    @PatchMapping("/{reminderId}/activate")
    public ResponseEntity<String> activateReminder(
            @PathVariable Long reminderId
    ) {
        taskReminderService.activateReminder(reminderId);

        return ResponseEntity.ok(
                "Task reminder activated successfully."
        );
    }

    @PatchMapping("/{reminderId}/deactivate")
    public ResponseEntity<String> deactivateReminder(
            @PathVariable Long reminderId
    ) {
        taskReminderService.deactivateReminder(reminderId);

        return ResponseEntity.ok(
                "Task reminder deactivated successfully."
        );
    }

    @DeleteMapping("/{reminderId}")
    public ResponseEntity<String> deleteReminder(
            @PathVariable Long reminderId
    ) {
        taskReminderService.deleteReminder(reminderId);

        return ResponseEntity.ok(
                "Task reminder deleted successfully."
        );
    }
}