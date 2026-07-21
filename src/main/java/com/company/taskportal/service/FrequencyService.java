package com.company.taskportal.service;

import com.company.taskportal.dto.FrequencyRequest;
import com.company.taskportal.dto.FrequencyResponse;

import java.util.List;

public interface FrequencyService {

    FrequencyResponse createFrequency(FrequencyRequest request);

    FrequencyResponse updateFrequency(Long frequencyId,
                                      FrequencyRequest request);

    FrequencyResponse getFrequencyById(Long frequencyId);

    FrequencyResponse getFrequencyByCode(String frequencyCode);

    List<FrequencyResponse> getAllFrequencies();

    List<FrequencyResponse> getAllActiveFrequencies();

    void activateFrequency(Long frequencyId);

    void deactivateFrequency(Long frequencyId);

    void deleteFrequency(Long frequencyId);
}