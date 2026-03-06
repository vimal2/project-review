package com.revhire.employerservice.controller;

import com.revhire.employerservice.dto.ApiResponse;
import com.revhire.employerservice.dto.EmployerCompanyProfileRequest;
import com.revhire.employerservice.dto.EmployerCompanyProfileResponse;
import com.revhire.employerservice.dto.EmployerStatisticsResponse;
import com.revhire.employerservice.exception.BadRequestException;
import com.revhire.employerservice.service.EmployerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employer")
public class EmployerController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EmployerController.class);

    private final EmployerService employerService;

    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }

    /**
     * Get company profile for the authenticated employer
     * @param userIdHeader the user ID from request header
     * @return ResponseEntity containing the company profile
     */
    @GetMapping("/company-profile")
    public ResponseEntity<ApiResponse<EmployerCompanyProfileResponse>> getCompanyProfile(
            @RequestHeader(value = "X-User-Id", required = true) Long userIdHeader) {

        log.info("GET /api/employer/company-profile - User ID: {}", userIdHeader);

        if (userIdHeader == null || userIdHeader <= 0) {
            throw new BadRequestException("X-User-Id header is required and must be a positive number");
        }

        EmployerCompanyProfileResponse profile = employerService.getCompanyProfile(userIdHeader);

        return ResponseEntity.ok(
            ApiResponse.success("Company profile retrieved successfully", profile)
        );
    }

    /**
     * Update company profile for the authenticated employer
     * @param userIdHeader the user ID from request header
     * @param request the update request
     * @return ResponseEntity containing the updated company profile
     */
    @PutMapping("/company-profile")
    public ResponseEntity<ApiResponse<EmployerCompanyProfileResponse>> updateCompanyProfile(
            @RequestHeader(value = "X-User-Id", required = true) Long userIdHeader,
            @Valid @RequestBody EmployerCompanyProfileRequest request) {

        log.info("PUT /api/employer/company-profile - User ID: {}", userIdHeader);

        if (userIdHeader == null || userIdHeader <= 0) {
            throw new BadRequestException("X-User-Id header is required and must be a positive number");
        }

        EmployerCompanyProfileResponse profile = employerService.updateCompanyProfile(userIdHeader, request);

        return ResponseEntity.ok(
            ApiResponse.success("Company profile updated successfully", profile)
        );
    }

    /**
     * Get statistics for the authenticated employer
     * @param userIdHeader the user ID from request header
     * @return ResponseEntity containing employer statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<EmployerStatisticsResponse>> getStatistics(
            @RequestHeader(value = "X-User-Id", required = true) Long userIdHeader) {

        log.info("GET /api/employer/statistics - User ID: {}", userIdHeader);

        if (userIdHeader == null || userIdHeader <= 0) {
            throw new BadRequestException("X-User-Id header is required and must be a positive number");
        }

        EmployerStatisticsResponse statistics = employerService.getStatistics(userIdHeader);

        return ResponseEntity.ok(
            ApiResponse.success("Statistics retrieved successfully", statistics)
        );
    }

    /**
     * Health check endpoint
     * @return ResponseEntity with health status
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(
            ApiResponse.success("Employer Service is running", "OK")
        );
    }
}
