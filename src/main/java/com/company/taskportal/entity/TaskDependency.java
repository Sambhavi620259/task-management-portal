package com.company.taskportal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "task_dependencies",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_task_dependency",
                        columnNames = {
                                "predecessor_task_id",
                                "successor_task_id"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDependency extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The task that must be completed (or started)
     * before the successor task, depending on the dependency type.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "predecessor_task_id",
            nullable = false
    )
    private Task predecessorTask;

    /**
     * The task that depends on the predecessor task.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "successor_task_id",
            nullable = false
    )
    private Task successorTask;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DependencyType dependencyType;

}