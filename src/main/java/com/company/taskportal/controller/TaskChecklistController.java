package com.company.taskportal.controller;

import com.company.taskportal.dto.TaskChecklistRequest;
import com.company.taskportal.dto.TaskChecklistResponse;
import com.company.taskportal.service.TaskChecklistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-checklists")
@RequiredArgsConstructor
public class TaskChecklistController {

    private final TaskChecklistService taskChecklistService;

    @PostMapping
    public ResponseEntity<TaskChecklistResponse> createChecklist(
            @Valid @RequestBody TaskChecklistRequest request) {

        return new ResponseEntity<>(
                taskChecklistService.createChecklist(request),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{checklistId}")
    public ResponseEntity<TaskChecklistResponse> updateChecklist(
            @PathVariable Long checklistId,
            @Valid @RequestBody TaskChecklistRequest request) {

        return ResponseEntity.ok(
                taskChecklistService.updateChecklist(checklistId, request)
        );
    }

    @GetMapping("/{checklistId}")
    public ResponseEntity<TaskChecklistResponse> getChecklistById(
            @PathVariable Long checklistId) {

        return ResponseEntity.ok(
                taskChecklistService.getChecklistById(checklistId)
        );
    }

    @GetMapping
    public ResponseEntity<List<TaskChecklistResponse>> getAllChecklists() {

        return ResponseEntity.ok(
                taskChecklistService.getAllChecklists()
        );
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<TaskChecklistResponse>> getChecklistsByTask(
            @PathVariable Long taskId) {

        return ResponseEntity.ok(
                taskChecklistService.getChecklistsByTask(taskId)
        );
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<TaskChecklistResponse>> getChecklistsByAssignedEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                taskChecklistService.getChecklistsByAssignedEmployee(employeeId)
        );
    }

    @GetMapping("/completed")
    public ResponseEntity<List<TaskChecklistResponse>> getCompletedChecklists() {

        return ResponseEntity.ok(
                taskChecklistService.getCompletedChecklists()
        );
    }

    @GetMapping("/pending")
    public ResponseEntity<List<TaskChecklistResponse>> getPendingChecklists() {

        return ResponseEntity.ok(
                taskChecklistService.getPendingChecklists()
        );
    }

    @GetMapping("/task/{taskId}/completed")
    public ResponseEntity<List<TaskChecklistResponse>> getCompletedChecklistsByTask(
            @PathVariable Long taskId) {

        return ResponseEntity.ok(
                taskChecklistService.getCompletedChecklistsByTask(taskId)
        );
    }

    @GetMapping("/task/{taskId}/pending")
    public ResponseEntity<List<TaskChecklistResponse>> getPendingChecklistsByTask(
            @PathVariable Long taskId) {

        return ResponseEntity.ok(
                taskChecklistService.getPendingChecklistsByTask(taskId)
        );
    }

    @GetMapping("/employee/{employeeId}/completed")
    public ResponseEntity<List<TaskChecklistResponse>> getCompletedChecklistsByEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                taskChecklistService.getCompletedChecklistsByEmployee(employeeId)
        );
    }

    @GetMapping("/employee/{employeeId}/pending")
    public ResponseEntity<List<TaskChecklistResponse>> getPendingChecklistsByEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                taskChecklistService.getPendingChecklistsByEmployee(employeeId)
        );
    }

    @GetMapping("/task/{taskId}/count")
    public ResponseEntity<Long> countByTask(
            @PathVariable Long taskId) {

        return ResponseEntity.ok(
                taskChecklistService.countByTask(taskId)
        );
    }

    @GetMapping("/task/{taskId}/completed/count")
    public ResponseEntity<Long> countCompletedByTask(
            @PathVariable Long taskId) {

        return ResponseEntity.ok(
                taskChecklistService.countCompletedByTask(taskId)
        );
    }

    @GetMapping("/task/{taskId}/pending/count")
    public ResponseEntity<Long> countPendingByTask(
            @PathVariable Long taskId) {

        return ResponseEntity.ok(
                taskChecklistService.countPendingByTask(taskId)
        );
    }

    @GetMapping("/employee/{employeeId}/count")
    public ResponseEntity<Long> countByEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                taskChecklistService.countByEmployee(employeeId)
        );
    }

    @GetMapping("/employee/{employeeId}/completed/count")
    public ResponseEntity<Long> countCompletedByEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                taskChecklistService.countCompletedByEmployee(employeeId)
        );
    }

    @GetMapping("/employee/{employeeId}/pending/count")
    public ResponseEntity<Long> countPendingByEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                taskChecklistService.countPendingByEmployee(employeeId)
        );
    }

    @PatchMapping("/{checklistId}/complete")
    public ResponseEntity<TaskChecklistResponse> markCompleted(
            @PathVariable Long checklistId) {

        return ResponseEntity.ok(
                taskChecklistService.markCompleted(checklistId)
        );
    }

    @PatchMapping("/{checklistId}/pending")
    public ResponseEntity<TaskChecklistResponse> markPending(
            @PathVariable Long checklistId) {

        return ResponseEntity.ok(
                taskChecklistService.markPending(checklistId)
        );
    }

    @PatchMapping("/{checklistId}/activate")
    public ResponseEntity<String> activateChecklist(
            @PathVariable Long checklistId) {

        taskChecklistService.activateChecklist(checklistId);

        return ResponseEntity.ok("Checklist activated successfully.");
    }

    @PatchMapping("/{checklistId}/deactivate")
    public ResponseEntity<String> deactivateChecklist(
            @PathVariable Long checklistId) {

        taskChecklistService.deactivateChecklist(checklistId);

        return ResponseEntity.ok("Checklist deactivated successfully.");
    }

    @DeleteMapping("/{checklistId}")
    public ResponseEntity<String> deleteChecklist(
            @PathVariable Long checklistId) {

        taskChecklistService.deleteChecklist(checklistId);

        return ResponseEntity.ok("Checklist deleted successfully.");
    }

}