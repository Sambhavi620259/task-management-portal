package com.company.taskportal.service;

import com.company.taskportal.dto.OrganizationDTO;
import com.company.taskportal.entity.Organization;
import com.company.taskportal.exception.DuplicateResourceException;
import com.company.taskportal.exception.ResourceNotFoundException;
import com.company.taskportal.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public OrganizationDTO createOrganization(OrganizationDTO dto) {

        if (organizationRepository.existsByOrganizationCode(dto.getOrganizationCode())) {
            throw new DuplicateResourceException("Organization Code already exists.");
        }

        if (organizationRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already exists.");
        }

        Organization organization = convertToEntity(dto);

        return convertToDTO(organizationRepository.save(organization));
    }

    @Override
    public OrganizationDTO updateOrganization(Long id, OrganizationDTO dto) {

        Organization organization = organizationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Organization not found."));

        if (!organization.getOrganizationCode().equals(dto.getOrganizationCode())
                && organizationRepository.existsByOrganizationCode(dto.getOrganizationCode())) {
            throw new DuplicateResourceException("Organization Code already exists.");
        }

        if (!organization.getEmail().equals(dto.getEmail())
                && organizationRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already exists.");
        }

        organization.setOrganizationCode(dto.getOrganizationCode());
        organization.setOrganizationName(dto.getOrganizationName());
        organization.setContactPerson(dto.getContactPerson());
        organization.setEmail(dto.getEmail());
        organization.setPhoneNumber(dto.getPhoneNumber());
        organization.setWebsite(dto.getWebsite());
        organization.setAddressLine1(dto.getAddressLine1());
        organization.setAddressLine2(dto.getAddressLine2());
        organization.setCity(dto.getCity());
        organization.setState(dto.getState());
        organization.setCountry(dto.getCountry());
        organization.setPostalCode(dto.getPostalCode());
        organization.setRegistrationNumber(dto.getRegistrationNumber());
        organization.setTaxNumber(dto.getTaxNumber());
        organization.setLogoUrl(dto.getLogoUrl());
        organization.setDescription(dto.getDescription());

        return convertToDTO(organizationRepository.save(organization));
    }

    @Override
    public OrganizationDTO getOrganizationById(Long id) {

        Organization organization = organizationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Organization not found."));

        return convertToDTO(organization);
    }

    @Override
    public List<OrganizationDTO> getAllOrganizations() {

        return organizationRepository.findByDeletedFalse()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrganization(Long id) {

        Organization organization = organizationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Organization not found."));

        organization.setDeleted(true);

        organizationRepository.save(organization);
    }

    @Override
    public void activateOrganization(Long id) {

        Organization organization = organizationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Organization not found."));

        organization.setActive(true);

        organizationRepository.save(organization);
    }

    @Override
    public void deactivateOrganization(Long id) {

        Organization organization = organizationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Organization not found."));

        organization.setActive(false);

        organizationRepository.save(organization);
    }

    private OrganizationDTO convertToDTO(Organization organization) {

        OrganizationDTO dto = new OrganizationDTO();

        dto.setId(organization.getId());
        dto.setCreatedAt(organization.getCreatedAt());
        dto.setUpdatedAt(organization.getUpdatedAt());

        dto.setOrganizationCode(organization.getOrganizationCode());
        dto.setOrganizationName(organization.getOrganizationName());
        dto.setContactPerson(organization.getContactPerson());
        dto.setEmail(organization.getEmail());
        dto.setPhoneNumber(organization.getPhoneNumber());
        dto.setWebsite(organization.getWebsite());
        dto.setAddressLine1(organization.getAddressLine1());
        dto.setAddressLine2(organization.getAddressLine2());
        dto.setCity(organization.getCity());
        dto.setState(organization.getState());
        dto.setCountry(organization.getCountry());
        dto.setPostalCode(organization.getPostalCode());
        dto.setRegistrationNumber(organization.getRegistrationNumber());
        dto.setTaxNumber(organization.getTaxNumber());
        dto.setLogoUrl(organization.getLogoUrl());
        dto.setDescription(organization.getDescription());
        dto.setActive(organization.getActive());

        return dto;
    }

    private Organization convertToEntity(OrganizationDTO dto) {

        Organization organization = new Organization();

        organization.setOrganizationCode(dto.getOrganizationCode());
        organization.setOrganizationName(dto.getOrganizationName());
        organization.setContactPerson(dto.getContactPerson());
        organization.setEmail(dto.getEmail());
        organization.setPhoneNumber(dto.getPhoneNumber());
        organization.setWebsite(dto.getWebsite());
        organization.setAddressLine1(dto.getAddressLine1());
        organization.setAddressLine2(dto.getAddressLine2());
        organization.setCity(dto.getCity());
        organization.setState(dto.getState());
        organization.setCountry(dto.getCountry());
        organization.setPostalCode(dto.getPostalCode());
        organization.setRegistrationNumber(dto.getRegistrationNumber());
        organization.setTaxNumber(dto.getTaxNumber());
        organization.setLogoUrl(dto.getLogoUrl());
        organization.setDescription(dto.getDescription());

        return organization;
    }
}