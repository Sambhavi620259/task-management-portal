package com.company.taskportal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {

    private Long id;

    private String employeeCode;

    private String firstName;

    private String lastName;

    private String email;

    private String mobileNumber;

    private String designation;

    private LocalDate joiningDate;

    private Long organizationId;

    private String organizationName;

    private Long departmentId;

    private String departmentName;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}