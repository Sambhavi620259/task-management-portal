package com.company.taskportal.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FrequencyRequest {

    @NotBlank(message = "Frequency code is required")
    @Size(max = 20, message = "Frequency code cannot exceed 20 characters")
    private String frequencyCode;

    @NotBlank(message = "Frequency name is required")
    @Size(max = 100, message = "Frequency name cannot exceed 100 characters")
    private String frequencyName;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Interval value is required")
    @Min(value = 1, message = "Interval value must be at least 1")
    private Integer intervalValue;

    @NotBlank(message = "Interval unit is required")
    @Size(max = 20, message = "Interval unit cannot exceed 20 characters")
    private String intervalUnit;

    public FrequencyRequest() {
    }

    public String getFrequencyCode() {
        return frequencyCode;
    }

    public void setFrequencyCode(String frequencyCode) {
        this.frequencyCode = frequencyCode;
    }

    public String getFrequencyName() {
        return frequencyName;
    }

    public void setFrequencyName(String frequencyName) {
        this.frequencyName = frequencyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIntervalValue() {
        return intervalValue;
    }

    public void setIntervalValue(Integer intervalValue) {
        this.intervalValue = intervalValue;
    }

    public String getIntervalUnit() {
        return intervalUnit;
    }

    public void setIntervalUnit(String intervalUnit) {
        this.intervalUnit = intervalUnit;
    }
}