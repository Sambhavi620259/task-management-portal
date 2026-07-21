package com.company.taskportal.service;

import com.company.taskportal.dto.OrganizationDTO;

import java.util.List;

public interface OrganizationService {

    OrganizationDTO createOrganization(OrganizationDTO organizationDTO);

    OrganizationDTO updateOrganization(Long id, OrganizationDTO organizationDTO);

    OrganizationDTO getOrganizationById(Long id);

    List<OrganizationDTO> getAllOrganizations();

    void deleteOrganization(Long id);

    void activateOrganization(Long id);

    void deactivateOrganization(Long id);

}