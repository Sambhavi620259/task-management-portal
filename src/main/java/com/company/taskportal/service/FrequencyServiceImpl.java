package com.company.taskportal.service;

import com.company.taskportal.dto.FrequencyRequest;
import com.company.taskportal.dto.FrequencyResponse;
import com.company.taskportal.entity.Frequency;
import com.company.taskportal.exception.ResourceAlreadyExistsException;
import com.company.taskportal.exception.ResourceNotFoundException;
import com.company.taskportal.repository.FrequencyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FrequencyServiceImpl implements FrequencyService {

    private final FrequencyRepository frequencyRepository;

    public FrequencyServiceImpl(FrequencyRepository frequencyRepository) {
        this.frequencyRepository = frequencyRepository;
    }

    private FrequencyResponse mapToResponse(Frequency frequency) {

        FrequencyResponse response = new FrequencyResponse();

        response.setId(frequency.getId());
        response.setFrequencyCode(frequency.getFrequencyCode());
        response.setFrequencyName(frequency.getFrequencyName());
        response.setDescription(frequency.getDescription());
        response.setIntervalValue(frequency.getIntervalValue());
        response.setIntervalUnit(frequency.getIntervalUnit());

        response.setActive(frequency.getActive());
        response.setDeleted(frequency.getDeleted());

        response.setCreatedAt(frequency.getCreatedAt());
        response.setUpdatedAt(frequency.getUpdatedAt());

        return response;
    }
    @Override
    public FrequencyResponse createFrequency(FrequencyRequest request) {

        if (frequencyRepository.existsByFrequencyCodeAndDeletedFalse(request.getFrequencyCode())) {
            throw new ResourceAlreadyExistsException(
                    "Frequency code already exists: " + request.getFrequencyCode());
        }

        if (frequencyRepository.existsByFrequencyNameAndDeletedFalse(request.getFrequencyName())) {
            throw new ResourceAlreadyExistsException(
                    "Frequency name already exists: " + request.getFrequencyName());
        }

        Frequency frequency = new Frequency();

        frequency.setFrequencyCode(request.getFrequencyCode());
        frequency.setFrequencyName(request.getFrequencyName());
        frequency.setDescription(request.getDescription());
        frequency.setIntervalValue(request.getIntervalValue());
        frequency.setIntervalUnit(request.getIntervalUnit());

        frequency.setActive(true);
        frequency.setDeleted(false);

        Frequency savedFrequency = frequencyRepository.save(frequency);

        return mapToResponse(savedFrequency);
    }
    @Override
    public FrequencyResponse updateFrequency(Long frequencyId,
                                             FrequencyRequest request) {

        Frequency frequency = frequencyRepository
                .findByIdAndDeletedFalse(frequencyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Frequency not found with ID: " + frequencyId));

        if (!frequency.getFrequencyCode().equalsIgnoreCase(request.getFrequencyCode())
                && frequencyRepository.existsByFrequencyCodeAndDeletedFalse(request.getFrequencyCode())) {

            throw new ResourceAlreadyExistsException(
                    "Frequency code already exists: " + request.getFrequencyCode());
        }

        if (!frequency.getFrequencyName().equalsIgnoreCase(request.getFrequencyName())
                && frequencyRepository.existsByFrequencyNameAndDeletedFalse(request.getFrequencyName())) {

            throw new ResourceAlreadyExistsException(
                    "Frequency name already exists: " + request.getFrequencyName());
        }

        frequency.setFrequencyCode(request.getFrequencyCode());
        frequency.setFrequencyName(request.getFrequencyName());
        frequency.setDescription(request.getDescription());
        frequency.setIntervalValue(request.getIntervalValue());
        frequency.setIntervalUnit(request.getIntervalUnit());

        Frequency updatedFrequency = frequencyRepository.save(frequency);

        return mapToResponse(updatedFrequency);
    }
    @Override
    @Transactional(readOnly = true)
    public FrequencyResponse getFrequencyById(Long frequencyId) {

        Frequency frequency = frequencyRepository
                .findByIdAndDeletedFalse(frequencyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Frequency not found with ID: " + frequencyId));

        return mapToResponse(frequency);
    }

    @Override
    @Transactional(readOnly = true)
    public FrequencyResponse getFrequencyByCode(String frequencyCode) {

        Frequency frequency = frequencyRepository
                .findByFrequencyCodeAndDeletedFalse(frequencyCode)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Frequency not found with code: " + frequencyCode));

        return mapToResponse(frequency);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FrequencyResponse> getAllFrequencies() {

        return frequencyRepository.findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FrequencyResponse> getAllActiveFrequencies() {

        return frequencyRepository.findByActiveTrueAndDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public void activateFrequency(Long frequencyId) {

        Frequency frequency = frequencyRepository
                .findByIdAndDeletedFalse(frequencyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Frequency not found with ID: " + frequencyId));

        frequency.setActive(true);

        frequencyRepository.save(frequency);
    }

    @Override
    public void deactivateFrequency(Long frequencyId) {

        Frequency frequency = frequencyRepository
                .findByIdAndDeletedFalse(frequencyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Frequency not found with ID: " + frequencyId));

        frequency.setActive(false);

        frequencyRepository.save(frequency);
    }

    @Override
    public void deleteFrequency(Long frequencyId) {

        Frequency frequency = frequencyRepository
                .findByIdAndDeletedFalse(frequencyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Frequency not found with ID: " + frequencyId));

        frequency.setDeleted(true);
        frequency.setActive(false);

        frequencyRepository.save(frequency);
    }
}