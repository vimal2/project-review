package com.revhire.employerservice.controller;

import com.revhire.employerservice.dto.ApiResponse;
import com.revhire.employerservice.dto.EmployerProfileRequest;
import com.revhire.employerservice.dto.EmployerProfileResponse;
import com.revhire.employerservice.dto.EmployerStatisticsResponse;
import com.revhire.employerservice.exception.BadRequestException;
import com.revhire.employerservice.service.EmployerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employer/company-profile")
public class EmployerProfileController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EmployerProfileController.class);

    private final EmployerService employerService;

    public EmployerProfileController(EmployerService employerService) {
        this.employerService = employerService;
    }

    /**
     * Get company profile for the authenticated employer
     */
    @GetMapping
    public ResponseEntity<ApiResponse<EmployerProfileResponse>> getProfile(
            @RequestHeader(value = "X-User-Id", required = true) Long userId) {

        log.info("GET /api/employer/company-profile - User ID: {}", userId);

        if (userId == null || userId <= 0) {
            throw new BadRequestException("X-User-Id header is required and must be a positive number");
        }

        // Note: This needs to be updated to use EmployerProfileResponse
        // For now returning placeholder
        return ResponseEntity.ok(
            ApiResponse.success("Company profile retrieved successfully", new EmployerProfileResponse())
        );
    }

    /**
     * Update company profile for the authenticated employer
     */
    @PutMapping
    public ResponseEntity<ApiResponse<EmployerProfileResponse>> updateProfile(
            @RequestHeader(value = "X-User-Id", required = true) Long userId,
            @Valid @RequestBody EmployerProfileRequest request) {

        log.info("PUT /api/employer/company-profile - User ID: {}", userId);

        if (userId == null || userId <= 0) {
            throw new BadRequestException("X-User-Id header is required and must be a positive number");
        }

        // Note: This needs to be updated to use EmployerProfileResponse
        // For now returning placeholder
        return ResponseEntity.ok(
            ApiResponse.success("Company profile updated successfully", new EmployerProfileResponse())
        );
    }

    /**
     * Get statistics for the authenticated employer
     */
    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<EmployerStatisticsResponse>> getStatistics(
            @RequestHeader(value = "X-User-Id", required = true) Long userId) {

        log.info("GET /api/employer/company-profile/statistics - User ID: {}", userId);

        if (userId == null || userId <= 0) {
            throw new BadRequestException("X-User-Id header is required and must be a positive number");
        }

        EmployerStatisticsResponse statistics = employerService.getStatistics(userId);

        return ResponseEntity.ok(
            ApiResponse.success("Statistics retrieved successfully", statistics)
        );
    }
}
