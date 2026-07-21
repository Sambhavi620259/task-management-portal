package com.company.taskportal.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeProfileRequest {

    // Employee Details
    @NotBlank(message = "Employee code is required.")
    private String employeeCode;

    @NotBlank(message = "First name is required.")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    private String gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private String bloodGroup;

    // Contact Details
    @NotBlank(message = "Mobile number is required.")
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    // Documents
    private MultipartFile profilePhoto;

    private MultipartFile aadhaarFrontImage;

    private MultipartFile aadhaarBackImage;

    private MultipartFile panImage;

    private MultipartFile resumePdf;

    private MultipartFile addressProofPdf;

    private MultipartFile offerLetterPdf;

    private MultipartFile experienceCertificatePdf;
}