package com.company.taskportal.controller;

import com.company.taskportal.dto.TaskReportResponse;
import com.company.taskportal.entity.Priority;
import com.company.taskportal.entity.TaskStatus;
import com.company.taskportal.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskReportResponse>> getTaskReport() {

        return ResponseEntity.ok(
                reportService.getTaskReport()
        );
    }

    @GetMapping("/tasks/project/{projectId}")
    public ResponseEntity<List<TaskReportResponse>> getTaskReportByProject(
            @PathVariable Long projectId
    ) {

        return ResponseEntity.ok(
                reportService.getTaskReportByProject(projectId)
        );
    }

    @GetMapping("/tasks/department/{departmentId}")
    public ResponseEntity<List<TaskReportResponse>> getTaskReportByDepartment(
            @PathVariable Long departmentId
    ) {

        return ResponseEntity.ok(
                reportService.getTaskReportByDepartment(departmentId)
        );
    }

    @GetMapping("/tasks/employee/{employeeId}")
    public ResponseEntity<List<TaskReportResponse>> getTaskReportByEmployee(
            @PathVariable Long employeeId
    ) {

        return ResponseEntity.ok(
                reportService.getTaskReportByEmployee(employeeId)
        );
    }

    @GetMapping("/tasks/status/{status}")
    public ResponseEntity<List<TaskReportResponse>> getTaskReportByStatus(
            @PathVariable TaskStatus status
    ) {

        return ResponseEntity.ok(
                reportService.getTaskReportByStatus(status)
        );
    }

    @GetMapping("/tasks/priority/{priority}")
    public ResponseEntity<List<TaskReportResponse>> getTaskReportByPriority(
            @PathVariable Priority priority
    ) {

        return ResponseEntity.ok(
                reportService.getTaskReportByPriority(priority)
        );
    }

    @GetMapping("/tasks/date-range")
    public ResponseEntity<List<TaskReportResponse>> getTaskReportByDateRange(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate
    ) {

        return ResponseEntity.ok(
                reportService.getTaskReportByDateRange(
                        startDate,
                        endDate
                )
        );
    }

}