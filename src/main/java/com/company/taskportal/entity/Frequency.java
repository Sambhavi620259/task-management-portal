package com.company.taskportal.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "frequencies",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "frequency_code"),
                @UniqueConstraint(columnNames = "frequency_name")
        }
)
public class Frequency extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "frequency_code", nullable = false, length = 20)
    private String frequencyCode;

    @Column(name = "frequency_name", nullable = false, length = 100)
    private String frequencyName;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Integer intervalValue;

    @Column(length = 20)
    private String intervalUnit;

    public Frequency() {
    }

    public Long getId() {
        return id;
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

    public void setDescription(String description) {
        this.description = description;
    }
}