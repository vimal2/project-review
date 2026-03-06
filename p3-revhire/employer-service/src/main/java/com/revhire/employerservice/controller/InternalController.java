package com.revhire.employerservice.controller;

import com.revhire.employerservice.dto.ApiResponse;
import com.revhire.employerservice.dto.EmployerProfileResponse;
import com.revhire.employerservice.dto.JobPostingResponse;
import com.revhire.employerservice.service.EmployerService;
import com.revhire.employerservice.service.JobPostingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Internal endpoints for inter-service communication
 * These endpoints are not exposed to external clients and are used by other microservices
 */
@RestController
@RequestMapping("/internal/employer")
public class InternalController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InternalController.class);

    private final EmployerService employerService;
    private final JobPostingService jobPostingService;

    public InternalController(EmployerService employerService, JobPostingService jobPostingService) {
        this.employerService = employerService;
        this.jobPostingService = jobPostingService;
    }

    /**
     * Get employer profile by user ID (for internal use by other services)
     */
    @GetMapping("/profile/{userId}")
    public ResponseEntity<ApiResponse<EmployerProfileResponse>> getProfileByUserId(
            @PathVariable Long userId) {

        log.info("INTERNAL GET /internal/employer/profile/{} ", userId);

        // Note: This needs proper implementation with EmployerProfileResponse
        return ResponseEntity.ok(
            ApiResponse.success("Employer profile retrieved", new EmployerProfileResponse())
        );
    }

    /**
     * Get all open jobs (for job seeker service)
     */
    @GetMapping("/jobs/open")
    public ResponseEntity<ApiResponse<List<JobPostingResponse>>> getAllOpenJobs() {

        log.info("INTERNAL GET /internal/employer/jobs/open");

        List<JobPostingResponse> jobs = jobPostingService.getAllOpenJobs();

        return ResponseEntity.ok(
            ApiResponse.success("Open jobs retrieved successfully", jobs)
        );
    }

    /**
     * Get job by ID (for application service)
     */
    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<ApiResponse<JobPostingResponse>> getJobById(@PathVariable Long jobId) {

        log.info("INTERNAL GET /internal/employer/jobs/{}", jobId);

        // Note: This endpoint gets job without employer validation for internal use
        // In a real implementation, we would have a separate method in the service
        // For now, returning a basic response
        return ResponseEntity.ok(
            ApiResponse.success("Job retrieved successfully", new JobPostingResponse())
        );
    }

    /**
     * Verify employer exists (for auth service)
     */
    @GetMapping("/verify/{userId}")
    public ResponseEntity<ApiResponse<Boolean>> verifyEmployerExists(@PathVariable Long userId) {

        log.info("INTERNAL GET /internal/employer/verify/{}", userId);

        // Note: This would check if employer profile exists
        boolean exists = true; // Placeholder

        return ResponseEntity.ok(
            ApiResponse.success("Employer verification completed", exists)
        );
    }

    /**
     * Get company name for an employer (for application/notification services)
     */
    @GetMapping("/{userId}/company-name")
    public ResponseEntity<ApiResponse<String>> getCompanyName(@PathVariable Long userId) {

        log.info("INTERNAL GET /internal/employer/{}/company-name", userId);

        // Note: This would fetch company name from employer profile
        String companyName = "Company"; // Placeholder

        return ResponseEntity.ok(
            ApiResponse.success("Company name retrieved", companyName)
        );
    }

    /**
     * Health check for internal endpoints
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(
            ApiResponse.success("Internal Employer Service endpoints are operational", "OK")
        );
    }
}
