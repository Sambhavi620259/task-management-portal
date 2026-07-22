package com.company.taskportal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "tasks",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "task_code")
        },
        indexes = {
                @Index(name = "idx_task_code", columnList = "task_code"),
                @Index(name = "idx_task_status", columnList = "status"),
                @Index(name = "idx_task_priority", columnList = "priority"),
                @Index(name = "idx_task_project", columnList = "project_id"),
                @Index(name = "idx_task_department", columnList = "department_id"),
                @Index(name = "idx_task_category", columnList = "task_category_id"),
                @Index(name = "idx_task_frequency", columnList = "frequency_id"),
                @Index(name = "idx_task_assigned_to", columnList = "assigned_to"),
                @Index(name = "idx_task_organization", columnList = "organization_id"),
                @Index(name = "idx_task_due_date", columnList = "due_date")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task extends BaseEntity {

    /*----------------------------------------------------
     * Basic Information
     *---------------------------------------------------*/

    @Column(name = "task_code", nullable = false, unique = true, length = 30)
    private String taskCode;

    @Column(name = "task_name", nullable = false, length = 150)
    private String taskName;

    @Column(name = "description", length = 2000)
    private String description;

    /*----------------------------------------------------
     * Master References
     *---------------------------------------------------*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_category_id", nullable = false)
    private TaskCategory taskCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frequency_id")
    private Frequency frequency;

    /*----------------------------------------------------
     * Task Configuration
     *---------------------------------------------------*/

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 20)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_type", nullable = false, length = 30)
    private TaskType taskType;

    /*----------------------------------------------------
     * Scheduling
     *---------------------------------------------------*/

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "completed_date")
    private LocalDate completedDate;

    @Column(name = "estimated_hours")
    private Integer estimatedHours;

    @Column(name = "actual_hours")
    private Integer actualHours;

    /*----------------------------------------------------
     * Assignment
     *---------------------------------------------------*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to", nullable = false)
    private Employee assignedTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private Employee createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by")
    private Employee reviewedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private Employee approvedBy;

    /*----------------------------------------------------
     * Workflow
     *---------------------------------------------------*/

    @Builder.Default
    @Column(name = "workflow_required")
    private Boolean workflowRequired = false;

    @Builder.Default
    @Column(name = "approval_required")
    private Boolean approvalRequired = false;

    @Column(name = "workflow_step")
    private Integer workflowStep;

    /*----------------------------------------------------
     * Progress
     *---------------------------------------------------*/

    @Builder.Default
    @Column(name = "completion_percentage")
    private Integer completionPercentage = 0;

    /*----------------------------------------------------
     * SLA
     *---------------------------------------------------*/

    @Column(name = "sla_hours")
    private Integer slaHours;

    @Column(name = "reminder_before_hours")
    private Integer reminderBeforeHours;

    @Column(name = "escalation_after_hours")
    private Integer escalationAfterHours;

    /*----------------------------------------------------
     * Notification
     *---------------------------------------------------*/

    @Builder.Default
    @Column(name = "email_notification")
    private Boolean emailNotification = true;

    @Builder.Default
    @Column(name = "sms_notification")
    private Boolean smsNotification = false;

    @Builder.Default
    @Column(name = "push_notification")
    private Boolean pushNotification = true;

    /*----------------------------------------------------
     * Recurring Task
     *---------------------------------------------------*/

    @Column(name = "next_execution_date")
    private LocalDate nextExecutionDate;

    @Column(name = "last_execution_date")
    private LocalDate lastExecutionDate;

    @Builder.Default
    @Column(name = "auto_generate_next")
    private Boolean autoGenerateNext = false;


}