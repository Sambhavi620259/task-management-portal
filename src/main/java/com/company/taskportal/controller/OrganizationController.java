package com.company.taskportal.controller;

import com.company.taskportal.common.ApiResponse;
import com.company.taskportal.dto.OrganizationDTO;
import com.company.taskportal.service.OrganizationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrganizationDTO>> createOrganization(
            @Valid @RequestBody OrganizationDTO organizationDTO) {

        OrganizationDTO createdOrganization = organizationService.createOrganization(organizationDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "Organization created successfully.",
                        createdOrganization
                ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrganizationDTO>> updateOrganization(
            @PathVariable Long id,
            @Valid @RequestBody OrganizationDTO organizationDTO) {

        OrganizationDTO updatedOrganization = organizationService.updateOrganization(id, organizationDTO);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Organization updated successfully.",
                        updatedOrganization
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrganizationDTO>> getOrganizationById(
            @PathVariable Long id) {

        OrganizationDTO organization = organizationService.getOrganizationById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Organization fetched successfully.",
                        organization
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrganizationDTO>>> getAllOrganizations() {

        List<OrganizationDTO> organizations = organizationService.getAllOrganizations();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Organizations fetched successfully.",
                        organizations
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrganization(@PathVariable Long id) {

        organizationService.deleteOrganization(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Organization deleted successfully.",
                        null
                )
        );
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<Void>> activateOrganization(@PathVariable Long id) {

        organizationService.activateOrganization(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Organization activated successfully.",
                        null
                )
        );
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivateOrganization(@PathVariable Long id) {

        organizationService.deactivateOrganization(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Organization deactivated successfully.",
                        null
                )
        );
    }
}