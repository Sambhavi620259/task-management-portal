package com.company.taskportal.controller;

import com.company.taskportal.dto.TaskDependencyRequest;
import com.company.taskportal.dto.TaskDependencyResponse;
import com.company.taskportal.entity.DependencyType;
import com.company.taskportal.service.TaskDependencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-dependencies")
@RequiredArgsConstructor
public class TaskDependencyController {

    private final TaskDependencyService taskDependencyService;

    @PostMapping
    public ResponseEntity<TaskDependencyResponse> createDependency(
            @Valid @RequestBody TaskDependencyRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskDependencyService.createDependency(request));
    }

    @PutMapping("/{dependencyId}")
    public ResponseEntity<TaskDependencyResponse> updateDependency(
            @PathVariable Long dependencyId,
            @Valid @RequestBody TaskDependencyRequest request
    ) {
        return ResponseEntity.ok(
                taskDependencyService.updateDependency(
                        dependencyId,
                        request
                )
        );
    }

    @GetMapping("/{dependencyId}")
    public ResponseEntity<TaskDependencyResponse> getDependencyById(
            @PathVariable Long dependencyId
    ) {
        return ResponseEntity.ok(
                taskDependencyService.getDependencyById(dependencyId)
        );
    }

    @GetMapping
    public ResponseEntity<List<TaskDependencyResponse>> getAllDependencies() {
        return ResponseEntity.ok(
                taskDependencyService.getAllDependencies()
        );
    }

    @GetMapping("/predecessor/{predecessorTaskId}")
    public ResponseEntity<List<TaskDependencyResponse>> getDependenciesByPredecessorTask(
            @PathVariable Long predecessorTaskId
    ) {
        return ResponseEntity.ok(
                taskDependencyService.getDependenciesByPredecessorTask(
                        predecessorTaskId
                )
        );
    }

    @GetMapping("/successor/{successorTaskId}")
    public ResponseEntity<List<TaskDependencyResponse>> getDependenciesBySuccessorTask(
            @PathVariable Long successorTaskId
    ) {
        return ResponseEntity.ok(
                taskDependencyService.getDependenciesBySuccessorTask(
                        successorTaskId
                )
        );
    }

    @GetMapping("/type/{dependencyType}")
    public ResponseEntity<List<TaskDependencyResponse>> getDependenciesByType(
            @PathVariable DependencyType dependencyType
    ) {
        return ResponseEntity.ok(
                taskDependencyService.getDependenciesByType(
                        dependencyType
                )
        );
    }

    @GetMapping("/predecessor/{predecessorTaskId}/type/{dependencyType}")
    public ResponseEntity<List<TaskDependencyResponse>> getDependenciesByPredecessorTaskAndType(
            @PathVariable Long predecessorTaskId,
            @PathVariable DependencyType dependencyType
    ) {
        return ResponseEntity.ok(
                taskDependencyService
                        .getDependenciesByPredecessorTaskAndType(
                                predecessorTaskId,
                                dependencyType
                        )
        );
    }

    @GetMapping("/successor/{successorTaskId}/type/{dependencyType}")
    public ResponseEntity<List<TaskDependencyResponse>> getDependenciesBySuccessorTaskAndType(
            @PathVariable Long successorTaskId,
            @PathVariable DependencyType dependencyType
    ) {
        return ResponseEntity.ok(
                taskDependencyService
                        .getDependenciesBySuccessorTaskAndType(
                                successorTaskId,
                                dependencyType
                        )
        );
    }

    @GetMapping("/count/predecessor/{predecessorTaskId}")
    public ResponseEntity<Long> countByPredecessorTask(
            @PathVariable Long predecessorTaskId
    ) {
        return ResponseEntity.ok(
                taskDependencyService.countByPredecessorTask(
                        predecessorTaskId
                )
        );
    }

    @GetMapping("/count/successor/{successorTaskId}")
    public ResponseEntity<Long> countBySuccessorTask(
            @PathVariable Long successorTaskId
    ) {
        return ResponseEntity.ok(
                taskDependencyService.countBySuccessorTask(
                        successorTaskId
                )
        );
    }

    @GetMapping("/count/type/{dependencyType}")
    public ResponseEntity<Long> countByDependencyType(
            @PathVariable DependencyType dependencyType
    ) {
        return ResponseEntity.ok(
                taskDependencyService.countByDependencyType(
                        dependencyType
                )
        );
    }

    @PatchMapping("/{dependencyId}/activate")
    public ResponseEntity<String> activateDependency(
            @PathVariable Long dependencyId
    ) {
        taskDependencyService.activateDependency(dependencyId);

        return ResponseEntity.ok(
                "Task dependency activated successfully."
        );
    }

    @PatchMapping("/{dependencyId}/deactivate")
    public ResponseEntity<String> deactivateDependency(
            @PathVariable Long dependencyId
    ) {
        taskDependencyService.deactivateDependency(dependencyId);

        return ResponseEntity.ok(
                "Task dependency deactivated successfully."
        );
    }

    @DeleteMapping("/{dependencyId}")
    public ResponseEntity<String> deleteDependency(
            @PathVariable Long dependencyId
    ) {
        taskDependencyService.deleteDependency(dependencyId);

        return ResponseEntity.ok(
                "Task dependency deleted successfully."
        );
    }
}