package com.company.taskportal.service;

import com.company.taskportal.dto.EmployeeProfileRequest;
import com.company.taskportal.dto.EmployeeProfileResponse;
import com.company.taskportal.entity.EmployeeProfile;
import com.company.taskportal.entity.User;
import com.company.taskportal.exception.DuplicateResourceException;
import com.company.taskportal.exception.ResourceNotFoundException;
import com.company.taskportal.repository.EmployeeProfileRepository;
import com.company.taskportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private static final String PROFILE = "profile";
    private static final String AADHAAR = "aadhaar";
    private static final String PAN = "pan";
    private static final String RESUME = "resume";
    private static final String ADDRESS_PROOF = "address-proof";
    private static final String OFFER_LETTER = "offer-letter";
    private static final String EXPERIENCE = "experience";

    private final EmployeeProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    @Override
    public EmployeeProfileResponse createProfile(
            Long userId,
            EmployeeProfileRequest request) {

        User user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        if (profileRepository.existsByUser(user)) {
            throw new DuplicateResourceException(
                    "Employee profile already exists for this user.");
        }

        String employeeCode = request.getEmployeeCode().trim().toUpperCase();

        String aadhaarNumber = request.getAadhaarNumber() == null
                ? null
                : request.getAadhaarNumber().trim();

        String panNumber = request.getPanNumber() == null
                ? null
                : request.getPanNumber().trim().toUpperCase();

        if (profileRepository.existsByEmployeeCode(employeeCode)) {
            throw new DuplicateResourceException(
                    "Employee code already exists.");
        }

        if (aadhaarNumber != null &&
                profileRepository.existsByAadhaarNumber(aadhaarNumber)) {

            throw new DuplicateResourceException(
                    "Aadhaar number already exists.");
        }

        if (panNumber != null &&
                profileRepository.existsByPanNumber(panNumber)) {

            throw new DuplicateResourceException(
                    "PAN number already exists.");
        }

        EmployeeProfile profile = EmployeeProfile.builder()

                .user(user)

                .employeeCode(employeeCode)

                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())

                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .bloodGroup(request.getBloodGroup())

                .mobileNumber(request.getMobileNumber())
                .alternateMobileNumber(request.getAlternateMobileNumber())
                .officialEmail(request.getOfficialEmail())

                .aadhaarNumber(aadhaarNumber)
                .panNumber(panNumber)
                .passportNumber(request.getPassportNumber())
                .drivingLicenseNumber(request.getDrivingLicenseNumber())

                .designation(request.getDesignation())
                .department(request.getDepartment())
                .organization(request.getOrganization())
                .joiningDate(request.getJoiningDate())
                .experience(request.getExperience())

                .currentAddress(request.getCurrentAddress())
                .permanentAddress(request.getPermanentAddress())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .pincode(request.getPincode())

                .emergencyContactName(request.getEmergencyContactName())
                .emergencyContactRelation(request.getEmergencyContactRelation())
                .emergencyContactNumber(request.getEmergencyContactNumber())

                .profilePhoto(fileStorageService.uploadFile(
                        request.getProfilePhoto(),
                        PROFILE))

                .aadhaarFrontImage(fileStorageService.uploadFile(
                        request.getAadhaarFrontImage(),
                        AADHAAR))

                .aadhaarBackImage(fileStorageService.uploadFile(
                        request.getAadhaarBackImage(),
                        AADHAAR))

                .panImage(fileStorageService.uploadFile(
                        request.getPanImage(),
                        PAN))

                .resumePdf(fileStorageService.uploadFile(
                        request.getResumePdf(),
                        RESUME))

                .addressProofPdf(fileStorageService.uploadFile(
                        request.getAddressProofPdf(),
                        ADDRESS_PROOF))

                .offerLetterPdf(fileStorageService.uploadFile(
                        request.getOfferLetterPdf(),
                        OFFER_LETTER))

                .experienceCertificatePdf(fileStorageService.uploadFile(
                        request.getExperienceCertificatePdf(),
                        EXPERIENCE))

                .build();

        profile = profileRepository.save(profile);

        log.info(
                "Employee profile created successfully. User={}, EmployeeCode={}",
                user.getUsername(),
                employeeCode
        );

        return mapToResponse(profile);
    }
    @Override
    public EmployeeProfileResponse updateProfile(
            Long profileId,
            EmployeeProfileRequest request) {

        EmployeeProfile profile = profileRepository
                .findByIdAndDeletedFalse(profileId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee profile not found."));

        String employeeCode = request.getEmployeeCode().trim().toUpperCase();

        String aadhaarNumber = request.getAadhaarNumber() == null
                ? null
                : request.getAadhaarNumber().trim();

        String panNumber = request.getPanNumber() == null
                ? null
                : request.getPanNumber().trim().toUpperCase();

        // Employee Code validation
        if (!profile.getEmployeeCode().equals(employeeCode)
                && profileRepository.existsByEmployeeCode(employeeCode)) {

            throw new DuplicateResourceException(
                    "Employee code already exists.");
        }

        // Aadhaar validation
        if (aadhaarNumber != null
                && !aadhaarNumber.equals(profile.getAadhaarNumber())
                && profileRepository.existsByAadhaarNumber(aadhaarNumber)) {

            throw new DuplicateResourceException(
                    "Aadhaar number already exists.");
        }

        // PAN validation
        if (panNumber != null
                && !panNumber.equals(profile.getPanNumber())
                && profileRepository.existsByPanNumber(panNumber)) {

            throw new DuplicateResourceException(
                    "PAN number already exists.");
        }

        profile.setEmployeeCode(employeeCode);

        profile.setFirstName(request.getFirstName());
        profile.setMiddleName(request.getMiddleName());
        profile.setLastName(request.getLastName());

        profile.setGender(request.getGender());
        profile.setDateOfBirth(request.getDateOfBirth());
        profile.setBloodGroup(request.getBloodGroup());

        profile.setMobileNumber(request.getMobileNumber());
        profile.setAlternateMobileNumber(request.getAlternateMobileNumber());
        profile.setOfficialEmail(request.getOfficialEmail());

        profile.setAadhaarNumber(aadhaarNumber);
        profile.setPanNumber(panNumber);
        profile.setPassportNumber(request.getPassportNumber());
        profile.setDrivingLicenseNumber(request.getDrivingLicenseNumber());

        profile.setDesignation(request.getDesignation());
        profile.setDepartment(request.getDepartment());
        profile.setOrganization(request.getOrganization());
        profile.setJoiningDate(request.getJoiningDate());
        profile.setExperience(request.getExperience());

        profile.setCurrentAddress(request.getCurrentAddress());
        profile.setPermanentAddress(request.getPermanentAddress());
        profile.setCity(request.getCity());
        profile.setState(request.getState());
        profile.setCountry(request.getCountry());
        profile.setPincode(request.getPincode());

        profile.setEmergencyContactName(request.getEmergencyContactName());
        profile.setEmergencyContactRelation(request.getEmergencyContactRelation());
        profile.setEmergencyContactNumber(request.getEmergencyContactNumber());

        // Replace uploaded files if a new file is provided

        profile.setProfilePhoto(
                replaceFile(
                        profile.getProfilePhoto(),
                        request.getProfilePhoto(),
                        PROFILE));

        profile.setAadhaarFrontImage(
                replaceFile(
                        profile.getAadhaarFrontImage(),
                        request.getAadhaarFrontImage(),
                        AADHAAR));

        profile.setAadhaarBackImage(
                replaceFile(
                        profile.getAadhaarBackImage(),
                        request.getAadhaarBackImage(),
                        AADHAAR));

        profile.setPanImage(
                replaceFile(
                        profile.getPanImage(),
                        request.getPanImage(),
                        PAN));

        profile.setResumePdf(
                replaceFile(
                        profile.getResumePdf(),
                        request.getResumePdf(),
                        RESUME));

        profile.setAddressProofPdf(
                replaceFile(
                        profile.getAddressProofPdf(),
                        request.getAddressProofPdf(),
                        ADDRESS_PROOF));

        profile.setOfferLetterPdf(
                replaceFile(
                        profile.getOfferLetterPdf(),
                        request.getOfferLetterPdf(),
                        OFFER_LETTER));

        profile.setExperienceCertificatePdf(
                replaceFile(
                        profile.getExperienceCertificatePdf(),
                        request.getExperienceCertificatePdf(),
                        EXPERIENCE));

        profile = profileRepository.save(profile);

        log.info(
                "Employee profile updated successfully. User={}, EmployeeCode={}",
                profile.getUser().getUsername(),
                profile.getEmployeeCode()
        );

        return mapToResponse(profile);
    }
    @Override
    public EmployeeProfileResponse getProfileById(Long profileId) {

        EmployeeProfile profile = profileRepository
                .findByIdAndDeletedFalse(profileId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee profile not found."));

        return mapToResponse(profile);
    }

    @Override
    public EmployeeProfileResponse getProfileByUserId(Long userId) {

        User user = userRepository
                .findByIdAndDeletedFalse(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found."));

        EmployeeProfile profile = profileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee profile not found."));

        return mapToResponse(profile);
    }

    @Override
    public EmployeeProfileResponse getProfileByUsername(String username) {

        User user = userRepository
                .findByUsernameAndDeletedFalse(username.trim())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found."));

        EmployeeProfile profile = profileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee profile not found."));

        return mapToResponse(profile);
    }

    @Override
    public List<EmployeeProfileResponse> getAllProfiles() {

        return profileRepository.findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<EmployeeProfileResponse> getAllActiveProfiles() {

        return profileRepository.findByActiveTrueAndDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public void activateProfile(Long profileId) {

        EmployeeProfile profile = profileRepository
                .findByIdAndDeletedFalse(profileId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee profile not found."));

        if (Boolean.TRUE.equals(profile.getActive())) {
            return;
        }

        profile.setActive(true);

        profileRepository.save(profile);

        log.info(
                "Employee profile activated. User={}, EmployeeCode={}",
                profile.getUser().getUsername(),
                profile.getEmployeeCode()
        );
    }

    @Override
    public void deactivateProfile(Long profileId) {

        EmployeeProfile profile = profileRepository
                .findByIdAndDeletedFalse(profileId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee profile not found."));

        if (Boolean.FALSE.equals(profile.getActive())) {
            return;
        }

        profile.setActive(false);

        profileRepository.save(profile);

        log.info(
                "Employee profile deactivated. User={}, EmployeeCode={}",
                profile.getUser().getUsername(),
                profile.getEmployeeCode()
        );
    }

    @Override
    public void deleteProfile(Long profileId) {

        EmployeeProfile profile = profileRepository
                .findByIdAndDeletedFalse(profileId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee profile not found."));

        // Delete uploaded files

        fileStorageService.deleteFile(profile.getProfilePhoto());
        fileStorageService.deleteFile(profile.getAadhaarFrontImage());
        fileStorageService.deleteFile(profile.getAadhaarBackImage());
        fileStorageService.deleteFile(profile.getPanImage());
        fileStorageService.deleteFile(profile.getResumePdf());
        fileStorageService.deleteFile(profile.getAddressProofPdf());
        fileStorageService.deleteFile(profile.getOfferLetterPdf());
        fileStorageService.deleteFile(profile.getExperienceCertificatePdf());

        // Soft delete

        profile.setDeleted(true);
        profile.setActive(false);

        profileRepository.save(profile);

        log.info(
                "Employee profile deleted. User={}, EmployeeCode={}",
                profile.getUser().getUsername(),
                profile.getEmployeeCode()
        );
    }
    /**
     * Replaces an existing file with a new uploaded file.
     * If no new file is provided, the existing file path is returned.
     */
    private String replaceFile(
            String oldFile,
            org.springframework.web.multipart.MultipartFile newFile,
            String folder) {

        if (newFile == null || newFile.isEmpty()) {
            return oldFile;
        }

        if (oldFile != null && !oldFile.isBlank()) {
            fileStorageService.deleteFile(oldFile);
        }

        return fileStorageService.uploadFile(newFile, folder);
    }
    private EmployeeProfileResponse mapToResponse(EmployeeProfile profile) {

        return EmployeeProfileResponse.builder()

                .id(profile.getId())

                // User Information
                .userId(profile.getUser().getId())
                .username(profile.getUser().getUsername())
                .email(profile.getUser().getEmail())
                .role(profile.getUser().getRole())

                // Employee Information
                .employeeCode(profile.getEmployeeCode())
                .firstName(profile.getFirstName())
                .middleName(profile.getMiddleName())
                .lastName(profile.getLastName())
                .gender(profile.getGender())
                .dateOfBirth(profile.getDateOfBirth())
                .bloodGroup(profile.getBloodGroup())

                // Contact Information
                .mobileNumber(profile.getMobileNumber())
                .alternateMobileNumber(profile.getAlternateMobileNumber())
                .officialEmail(profile.getOfficialEmail())

                // Identity Information
                .aadhaarNumber(profile.getAadhaarNumber())
                .panNumber(profile.getPanNumber())
                .passportNumber(profile.getPassportNumber())
                .drivingLicenseNumber(profile.getDrivingLicenseNumber())

                // Organization Information
                .designation(profile.getDesignation())
                .department(profile.getDepartment())
                .organization(profile.getOrganization())
                .joiningDate(profile.getJoiningDate())
                .experience(profile.getExperience())

                // Address Information
                .currentAddress(profile.getCurrentAddress())
                .permanentAddress(profile.getPermanentAddress())
                .city(profile.getCity())
                .state(profile.getState())
                .country(profile.getCountry())
                .pincode(profile.getPincode())

                // Emergency Contact
                .emergencyContactName(profile.getEmergencyContactName())
                .emergencyContactRelation(profile.getEmergencyContactRelation())
                .emergencyContactNumber(profile.getEmergencyContactNumber())

                // Uploaded Documents
                .profilePhoto(profile.getProfilePhoto())
                .aadhaarFrontImage(profile.getAadhaarFrontImage())
                .aadhaarBackImage(profile.getAadhaarBackImage())
                .panImage(profile.getPanImage())
                .resumePdf(profile.getResumePdf())
                .addressProofPdf(profile.getAddressProofPdf())
                .offerLetterPdf(profile.getOfferLetterPdf())
                .experienceCertificatePdf(profile.getExperienceCertificatePdf())

                // Status
                .active(profile.getActive())

                // Audit Fields
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())

                .build();
    }

}