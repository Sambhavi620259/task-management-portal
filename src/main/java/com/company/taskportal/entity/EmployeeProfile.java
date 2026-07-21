package com.company.taskportal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "employee_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeProfile extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // Employee Details
    @Column(nullable = false, unique = true)
    private String employeeCode;

    @Column(nullable = false)
    private String firstName;

    private String middleName;

    @Column(nullable = false)
    private String lastName;

    private String gender;

    private LocalDate dateOfBirth;

    private String bloodGroup;

    // Contact Details
    @Column(nullable = false)
    private String mobileNumber;

    private String alternateMobileNumber;

    private String officialEmail;

    // Government IDs
    @Column(unique = true)
    private String aadhaarNumber;

    @Column(unique = true)
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
    @Column(length = 500)
    private String currentAddress;

    @Column(length = 500)
    private String permanentAddress;

    private String city;

    private String state;

    private String country;

    private String pincode;

    // Emergency Contact
    private String emergencyContactName;

    private String emergencyContactRelation;

    private String emergencyContactNumber;

    // Documents (Store File Paths)
    private String profilePhoto;

    private String aadhaarFrontImage;

    private String aadhaarBackImage;

    private String panImage;

    private String resumePdf;

    private String addressProofPdf;

    private String offerLetterPdf;

    private String experienceCertificatePdf;
}