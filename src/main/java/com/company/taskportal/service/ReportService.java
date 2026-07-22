package com.company.taskportal.service;

import com.company.taskportal.dto.TaskReportResponse;
import com.company.taskportal.entity.Priority;
import com.company.taskportal.entity.TaskStatus;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    /**
     * Returns all task reports.
     */
    List<TaskReportResponse> getTaskReport();

    /**
     * Returns task reports by project.
     */
    List<TaskReportResponse> getTaskReportByProject(Long projectId);

    /**
     * Returns task reports by department.
     */
    List<TaskReportResponse> getTaskReportByDepartment(Long departmentId);

    /**
     * Returns task reports by assigned employee.
     */
    List<TaskReportResponse> getTaskReportByEmployee(Long employeeId);

    /**
     * Returns task reports by task status.
     */
    List<TaskReportResponse> getTaskReportByStatus(
            TaskStatus status
    );

    /**
     * Returns task reports by priority.
     */
    List<TaskReportResponse> getTaskReportByPriority(
            Priority priority
    );

    /**
     * Returns task reports between two dates.
     */
    List<TaskReportResponse> getTaskReportByDateRange(
            LocalDate startDate,
            LocalDate endDate
    );

}