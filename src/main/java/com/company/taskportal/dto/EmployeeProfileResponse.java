package com.company.taskportal.dto;

import com.company.taskportal.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeProfileResponse {

    // Profile ID
    private Long id;

    // User Details
    private Long userId;
    private String username;
    private String email;
    private Role role;

    // Employee Details
    private String employeeCode;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private LocalDate dateOfBirth;
    private String bloodGroup;

    // Contact Details
    private String mobileNumber;
    private String alternateMobileNumber;
    private String officialEmail;

    // Government IDs
    private String aadhaarNumber;
    private String panNumber;
    private String passportNumber;
    private String drivingLicenseNumber;

    // Employment Details
    private String designation;
    private String department;
    private String organization;
    private LocalDate joiningDate;
    private Integer experience;

    // Address
    private String currentAddress;
    private String permanentAddress;
    private String city;
    private String state;
    private String country;
    private String pincode;

    // Emergency Contact
    private String emergencyContactName;
    private String emergencyContactRelation;
    private String emergencyContactNumber;

    // Document Paths
    private String profilePhoto;
    private String aadhaarFrontImage;
    private String aadhaarBackImage;
    private String panImage;
    private String resumePdf;
    private String addressProofPdf;
    private String offerLetterPdf;
    private String experienceCertificatePdf;

    // Status
    private Boolean active;

    // Audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}