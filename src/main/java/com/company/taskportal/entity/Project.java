package com.company.taskportal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "projects",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "project_code"),
                @UniqueConstraint(columnNames = "project_name")
        },
        indexes = {
                @Index(name = "idx_project_code", columnList = "project_code"),
                @Index(name = "idx_project_name", columnList = "project_name"),
                @Index(name = "idx_project_organization", columnList = "organization_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project extends BaseEntity {

    @Column(name = "project_code", nullable = false, length = 20)
    private String projectCode;

    @Column(name = "project_name", nullable = false, length = 150)
    private String projectName;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "project_manager", length = 100)
    private String projectManager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;
}