package com.revhire.employerservice.controller;

import com.revhire.employerservice.dto.ApiResponse;
import com.revhire.employerservice.dto.JobPostingRequest;
import com.revhire.employerservice.dto.JobPostingResponse;
import com.revhire.employerservice.exception.BadRequestException;
import com.revhire.employerservice.service.JobPostingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employer/jobs")
public class JobPostingController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JobPostingController.class);

    private final JobPostingService jobPostingService;

    public JobPostingController(JobPostingService jobPostingService) {
        this.jobPostingService = jobPostingService;
    }

    /**
     * Get all jobs for the authenticated employer
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<JobPostingResponse>>> getJobs(
            @RequestHeader(value = "X-User-Id", required = true) Long userId) {

        log.info("GET /api/employer/jobs - User ID: {}", userId);

        if (userId == null || userId <= 0) {
            throw new BadRequestException("X-User-Id header is required and must be a positive number");
        }

        List<JobPostingResponse> jobs = jobPostingService.getJobsByEmployer(userId);

        return ResponseEntity.ok(
            ApiResponse.success("Jobs retrieved successfully", jobs)
        );
    }

    /**
     * Get a specific job by ID
     */
    @GetMapping("/{jobId}")
    public ResponseEntity<ApiResponse<JobPostingResponse>> getJob(
            @RequestHeader(value = "X-User-Id", required = true) Long userId,
            @PathVariable Long jobId) {

        log.info("GET /api/employer/jobs/{} - User ID: {}", jobId, userId);

        if (userId == null || userId <= 0) {
            throw new BadRequestException("X-User-Id header is required and must be a positive number");
        }

        JobPostingResponse job = jobPostingService.getJobById(userId, jobId);

        return ResponseEntity.ok(
            ApiResponse.success("Job retrieved successfully", job)
        );
    }

    /**
     * Create a new job posting
     */
    @PostMapping
    public ResponseEntity<ApiResponse<JobPostingResponse>> createJob(
            @RequestHeader(value = "X-User-Id", required = true) Long userId,
            @Valid @RequestBody JobPostingRequest request) {

        log.info("POST /api/employer/jobs - User ID: {}", userId);

        if (userId == null || userId <= 0) {
            throw new BadRequestException("X-User-Id header is required and must be a positive number");
        }

        JobPostingResponse job = jobPostingService.createJob(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Job created successfully", job));
    }

    /**
     * Update an existing job posting
     */
    @PutMapping("/{jobId}")
    public ResponseEntity<ApiResponse<JobPostingResponse>> updateJob(
            @RequestHeader(value = "X-User-Id", required = true) Long userId,
            @PathVariable Long jobId,
            @Valid @RequestBody JobPostingRequest request) {

        log.info("PUT /api/employer/jobs/{} - User ID: {}", jobId, userId);

        if (userId == null || userId <= 0) {
            throw new BadRequestException("X-User-Id header is required and must be a positive number");
        }

        JobPostingResponse job = jobPostingService.updateJob(userId, jobId, request);

        return ResponseEntity.ok(
            ApiResponse.success("Job updated successfully", job)
        );
    }

    /**
     * Delete a job posting
     */
    @DeleteMapping("/{jobId}")
    public ResponseEntity<ApiResponse<Void>> deleteJob(
            @RequestHeader(value = "X-User-Id", required = true) Long userId,
            @PathVariable Long jobId) {

        log.info("DELETE /api/employer/jobs/{} - User ID: {}", jobId, userId);

        if (userId == null || userId <= 0) {
            throw new BadRequestException("X-User-Id header is required and must be a positive number");
        }

        jobPostingService.deleteJob(userId, jobId);

        return ResponseEntity.ok(
            ApiResponse.success("Job deleted successfully", null)
        );
    }

    /**
     * Close a job posting
     */
    @PatchMapping("/{jobId}/close")
    public ResponseEntity<ApiResponse<JobPostingResponse>> closeJob(
            @RequestHeader(value = "X-User-Id", required = true) Long userId,
            @PathVariable Long jobId) {

        log.info("PATCH /api/employer/jobs/{}/close - User ID: {}", jobId, userId);

        if (userId == null || userId <= 0) {
            throw new BadRequestException("X-User-Id header is required and must be a positive number");
        }

        JobPostingResponse job = jobPostingService.closeJob(userId, jobId);

        return ResponseEntity.ok(
            ApiResponse.success("Job closed successfully", job)
        );
    }

    /**
     * Reopen a job posting
     */
    @PatchMapping("/{jobId}/reopen")
    public ResponseEntity<ApiResponse<JobPostingResponse>> reopenJob(
            @RequestHeader(value = "X-User-Id", required = true) Long userId,
            @PathVariable Long jobId) {

        log.info("PATCH /api/employer/jobs/{}/reopen - User ID: {}", jobId, userId);

        if (userId == null || userId <= 0) {
            throw new BadRequestException("X-User-Id header is required and must be a positive number");
        }

        JobPostingResponse job = jobPostingService.reopenJob(userId, jobId);

        return ResponseEntity.ok(
            ApiResponse.success("Job reopened successfully", job)
        );
    }

    /**
     * Mark a job posting as filled
     */
    @PatchMapping("/{jobId}/fill")
    public ResponseEntity<ApiResponse<JobPostingResponse>> fillJob(
            @RequestHeader(value = "X-User-Id", required = true) Long userId,
            @PathVariable Long jobId) {

        log.info("PATCH /api/employer/jobs/{}/fill - User ID: {}", jobId, userId);

        if (userId == null || userId <= 0) {
            throw new BadRequestException("X-User-Id header is required and must be a positive number");
        }

        JobPostingResponse job = jobPostingService.fillJob(userId, jobId);

        return ResponseEntity.ok(
            ApiResponse.success("Job marked as filled successfully", job)
        );
    }
}
