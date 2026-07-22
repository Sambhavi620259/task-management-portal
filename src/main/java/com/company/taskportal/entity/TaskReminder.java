package com.company.taskportal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "task_reminders",
        indexes = {
                @Index(name = "idx_reminder_task", columnList = "task_id"),
                @Index(name = "idx_reminder_employee", columnList = "employee_id"),
                @Index(name = "idx_reminder_type", columnList = "reminder_type"),
                @Index(name = "idx_reminder_datetime", columnList = "reminder_date_time"),
                @Index(name = "idx_reminder_sent", columnList = "sent"),
                @Index(name = "idx_reminder_task_employee", columnList = "task_id, employee_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskReminder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Task for which the reminder is created.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "task_id",
            nullable = false
    )
    private Task task;

    /**
     * Employee who will receive the reminder.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "employee_id",
            nullable = false
    )
    private Employee employee;

    /**
     * Reminder delivery channel.
     */
    @Enumerated(EnumType.STRING)
    @Column(
            name = "reminder_type",
            nullable = false,
            length = 30
    )
    private ReminderType reminderType;

    /**
     * Date and time when the reminder should be sent.
     */
    @Column(
            name = "reminder_date_time",
            nullable = false
    )
    private LocalDateTime reminderDateTime;

    /**
     * Custom reminder message.
     */
    @Column(
            name = "message",
            length = 1000
    )
    private String message;

    /**
     * Whether the reminder has already been sent.
     */
    @Builder.Default
    @Column(
            name = "sent",
            nullable = false
    )
    private Boolean sent = false;

    /**
     * Actual time when the reminder was sent.
     */
    @Column(name = "sent_at")
    private LocalDateTime sentAt;

}