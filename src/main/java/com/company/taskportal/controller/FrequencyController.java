package com.company.taskportal.controller;

import com.company.taskportal.common.ApiResponse;
import com.company.taskportal.dto.FrequencyRequest;
import com.company.taskportal.dto.FrequencyResponse;
import com.company.taskportal.service.FrequencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/frequencies")
@Tag(name = "Frequency", description = "Frequency Management APIs")
public class FrequencyController {

    private final FrequencyService frequencyService;

    public FrequencyController(FrequencyService frequencyService) {
        this.frequencyService = frequencyService;
    }

    @PostMapping
    @Operation(summary = "Create Frequency")
    public ResponseEntity<ApiResponse<FrequencyResponse>> createFrequency(
            @Valid @RequestBody FrequencyRequest request) {

        FrequencyResponse response = frequencyService.createFrequency(request);

        return ResponseEntity.ok(
                ApiResponse.success("Frequency created successfully", response)
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Frequency")
    public ResponseEntity<ApiResponse<FrequencyResponse>> updateFrequency(
            @PathVariable Long id,
            @Valid @RequestBody FrequencyRequest request) {

        FrequencyResponse response = frequencyService.updateFrequency(id, request);

        return ResponseEntity.ok(
                ApiResponse.success("Frequency updated successfully", response)
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Frequency By ID")
    public ResponseEntity<ApiResponse<FrequencyResponse>> getFrequencyById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        frequencyService.getFrequencyById(id)
                )
        );
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get Frequency By Code")
    public ResponseEntity<ApiResponse<FrequencyResponse>> getFrequencyByCode(
            @PathVariable String code) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        frequencyService.getFrequencyByCode(code)
                )
        );
    }

    @GetMapping
    @Operation(summary = "Get All Frequencies")
    public ResponseEntity<ApiResponse<List<FrequencyResponse>>> getAllFrequencies() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        frequencyService.getAllFrequencies()
                )
        );
    }

    @GetMapping("/active")
    @Operation(summary = "Get Active Frequencies")
    public ResponseEntity<ApiResponse<List<FrequencyResponse>>> getAllActiveFrequencies() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        frequencyService.getAllActiveFrequencies()
                )
        );
    }

    @PutMapping("/{id}/activate")
    @Operation(summary = "Activate Frequency")
    public ResponseEntity<ApiResponse<String>> activateFrequency(
            @PathVariable Long id) {

        frequencyService.activateFrequency(id);

        return ResponseEntity.ok(
                ApiResponse.success("Frequency activated successfully")
        );
    }

    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate Frequency")
    public ResponseEntity<ApiResponse<String>> deactivateFrequency(
            @PathVariable Long id) {

        frequencyService.deactivateFrequency(id);

        return ResponseEntity.ok(
                ApiResponse.success("Frequency deactivated successfully")
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Frequency")
    public ResponseEntity<ApiResponse<String>> deleteFrequency(
            @PathVariable Long id) {

        frequencyService.deleteFrequency(id);

        return ResponseEntity.ok(
                ApiResponse.success("Frequency deleted successfully")
        );
    }
}