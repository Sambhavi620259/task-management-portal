package com.company.taskportal.service;

import com.company.taskportal.dto.DashboardSummaryResponse;
import com.company.taskportal.entity.Priority;
import com.company.taskportal.entity.TaskStatus;
import com.company.taskportal.repository.DepartmentRepository;
import com.company.taskportal.repository.EmployeeRepository;
import com.company.taskportal.repository.ProjectRepository;
import com.company.taskportal.repository.TaskRepository;
import com.company.taskportal.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ProjectRepository projectRepository;

    @Override
    public DashboardSummaryResponse getDashboardSummary() {

        long totalTasks = taskRepository.countByDeletedFalse();

        long pendingTasks =
                taskRepository.countByStatusAndDeletedFalse(
                        TaskStatus.PENDING
                );

        long inProgressTasks =
                taskRepository.countByStatusAndDeletedFalse(
                        TaskStatus.IN_PROGRESS
                );

        long completedTasks =
                taskRepository.countByStatusAndDeletedFalse(
                        TaskStatus.COMPLETED
                );

        long overdueTasks =
                taskRepository.countByDueDateBeforeAndStatusNotAndDeletedFalse(
                        java.time.LocalDate.now(),
                        TaskStatus.COMPLETED
                );

        long lowPriority =
                taskRepository.countByPriorityAndDeletedFalse(
                        Priority.LOW
                );

        long mediumPriority =
                taskRepository.countByPriorityAndDeletedFalse(
                        Priority.MEDIUM
                );

        long highPriority =
                taskRepository.countByPriorityAndDeletedFalse(
                        Priority.HIGH
                );

        long criticalPriority =
                taskRepository.countByPriorityAndDeletedFalse(
                        Priority.CRITICAL
                );

        long totalEmployees =
                employeeRepository.countByDeletedFalse();

        long activeEmployees =
                employeeRepository.countByActiveTrueAndDeletedFalse();

        long totalDepartments =
                departmentRepository.countByDeletedFalse();

        long totalProjects =
                projectRepository.countByDeletedFalse();

        double completionPercentage = 0.0;

        if (totalTasks > 0) {
            completionPercentage =
                    ((double) completedTasks / totalTasks) * 100;
        }

        /*
         * Placeholder for SLA compliance.
         * Replace with your actual SLA calculation later.
         */
        double slaCompliancePercentage = 100.0;

        return DashboardSummaryResponse.builder()
                .totalTasks(totalTasks)
                .pendingTasks(pendingTasks)
                .inProgressTasks(inProgressTasks)
                .completedTasks(completedTasks)
                .overdueTasks(overdueTasks)

                .lowPriorityTasks(lowPriority)
                .mediumPriorityTasks(mediumPriority)
                .highPriorityTasks(highPriority)
                .criticalPriorityTasks(criticalPriority)

                .totalEmployees(totalEmployees)
                .activeEmployees(activeEmployees)

                .totalDepartments(totalDepartments)
                .totalProjects(totalProjects)

                .completionPercentage(
                        Math.round(completionPercentage * 100.0) / 100.0
                )
                .slaCompliancePercentage(
                        Math.round(slaCompliancePercentage * 100.0) / 100.0
                )
                .build();
    }
}