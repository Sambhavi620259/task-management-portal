package com.company.taskportal.service;

import com.company.taskportal.dto.EmployeeProfileRequest;
import com.company.taskportal.dto.EmployeeProfileResponse;

import java.util.List;

public interface EmployeeProfileService {

    EmployeeProfileResponse createProfile(
            Long userId,
            EmployeeProfileRequest request);

    EmployeeProfileResponse updateProfile(
            Long profileId,
            EmployeeProfileRequest request);

    EmployeeProfileResponse getProfileById(Long profileId);

    EmployeeProfileResponse getProfileByUserId(Long userId);

    List<EmployeeProfileResponse> getAllProfiles();

    List<EmployeeProfileResponse> getAllActiveProfiles();

    void activateProfile(Long profileId);

    void deactivateProfile(Long profileId);

    void deleteProfile(Long profileId);

    EmployeeProfileResponse getProfileByUsername(String username);
}