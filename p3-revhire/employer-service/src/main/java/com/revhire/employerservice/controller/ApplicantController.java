package com.revhire.employerservice.controller;

import com.revhire.employerservice.dto.ApiResponse;
import com.revhire.employerservice.dto.ApplicantResponse;
import com.revhire.employerservice.exception.BadRequestException;
import com.revhire.employerservice.service.ApplicantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employer/applicants")
public class ApplicantController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ApplicantController.class);

    private final ApplicantService applicantService;

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    /**
     * Get all applicants for employer's jobs with optional filtering
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ApplicantResponse>>> getApplicants(
            @RequestHeader(value = "X-User-Id", required = true) Long userId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search) {

        log.info("GET /api/employer/applicants - User ID: {}, Status: {}, Search: {}",
                 userId, status, search);

        if (userId == null || userId <= 0) {
            throw new BadRequestException("X-User-Id header is required and must be a positive number");
        }

        List<ApplicantResponse> applicants = applicantService.getApplicantsForEmployer(userId, status, search);

        return ResponseEntity.ok(
            ApiResponse.success("Applicants retrieved successfully", applicants)
        );
    }

    /**
     * Get applicants for a specific job
     */
    @GetMapping("/job/{jobId}")
    public ResponseEntity<ApiResponse<List<ApplicantResponse>>> getApplicantsForJob(
            @RequestHeader(value = "X-User-Id", required = true) Long userId,
            @PathVariable Long jobId) {

        log.info("GET /api/employer/applicants/job/{} - User ID: {}", jobId, userId);

        if (userId == null || userId <= 0) {
            throw new BadRequestException("X-User-Id header is required and must be a positive number");
        }

        List<ApplicantResponse> applicants = applicantService.getApplicantsForJob(userId, jobId);

        return ResponseEntity.ok(
            ApiResponse.success("Applicants for job retrieved successfully", applicants)
        );
    }

    /**
     * Update application status
     */
    @PatchMapping("/{applicationId}/status")
    public ResponseEntity<ApiResponse<ApplicantResponse>> updateStatus(
            @RequestHeader(value = "X-User-Id", required = true) Long userId,
            @PathVariable Long applicationId,
            @RequestBody Map<String, String> body) {

        log.info("PATCH /api/employer/applicants/{}/status - User ID: {}", applicationId, userId);

        if (userId == null || userId <= 0) {
            throw new BadRequestException("X-User-Id header is required and must be a positive number");
        }

        String status = body.get("status");
        if (status == null || status.isBlank()) {
            throw new BadRequestException("Status is required");
        }

        ApplicantResponse applicant = applicantService.updateApplicationStatus(userId, applicationId, status);

        return ResponseEntity.ok(
            ApiResponse.success("Application status updated successfully", applicant)
        );
    }

    /**
     * Shortlist an applicant
     */
    @PatchMapping("/{applicationId}/shortlist")
    public ResponseEntity<ApiResponse<ApplicantResponse>> shortlist(
            @RequestHeader(value = "X-User-Id", required = true) Long userId,
            @PathVariable Long applicationId) {

        log.info("PATCH /api/employer/applicants/{}/shortlist - User ID: {}", applicationId, userId);

        if (userId == null || userId <= 0) {
            throw new BadRequestException("X-User-Id header is required and must be a positive number");
        }

        ApplicantResponse applicant = applicantService.updateApplicationStatus(userId, applicationId, "SHORTLISTED");

        return ResponseEntity.ok(
            ApiResponse.success("Applicant shortlisted successfully", applicant)
        );
    }

    /**
     * Reject an applicant
     */
    @PatchMapping("/{applicationId}/reject")
    public ResponseEntity<ApiResponse<ApplicantResponse>> reject(
            @RequestHeader(value = "X-User-Id", required = true) Long userId,
            @PathVariable Long applicationId) {

        log.info("PATCH /api/employer/applicants/{}/reject - User ID: {}", applicationId, userId);

        if (userId == null || userId <= 0) {
            throw new BadRequestException("X-User-Id header is required and must be a positive number");
        }

        ApplicantResponse applicant = applicantService.updateApplicationStatus(userId, applicationId, "REJECTED");

        return ResponseEntity.ok(
            ApiResponse.success("Applicant rejected successfully", applicant)
        );
    }

    /**
     * Mark applicant as under review
     */
    @PatchMapping("/{applicationId}/under-review")
    public ResponseEntity<ApiResponse<ApplicantResponse>> underReview(
            @RequestHeader(value = "X-User-Id", required = true) Long userId,
            @PathVariable Long applicationId) {

        log.info("PATCH /api/employer/applicants/{}/under-review - User ID: {}", applicationId, userId);

        if (userId == null || userId <= 0) {
            throw new BadRequestException("X-User-Id header is required and must be a positive number");
        }

        ApplicantResponse applicant = applicantService.updateApplicationStatus(userId, applicationId, "UNDER_REVIEW");

        return ResponseEntity.ok(
            ApiResponse.success("Applicant marked as under review successfully", applicant)
        );
    }

    /**
     * Update applicant notes
     */
    @PatchMapping("/{applicationId}/notes")
    public ResponseEntity<ApiResponse<ApplicantResponse>> updateNotes(
            @RequestHeader(value = "X-User-Id", required = true) Long userId,
            @PathVariable Long applicationId,
            @RequestBody Map<String, String> body) {

        log.info("PATCH /api/employer/applicants/{}/notes - User ID: {}", applicationId, userId);

        if (userId == null || userId <= 0) {
            throw new BadRequestException("X-User-Id header is required and must be a positive number");
        }

        String notes = body.get("notes");
        ApplicantResponse applicant = applicantService.updateApplicationNotes(userId, applicationId, notes);

        return ResponseEntity.ok(
            ApiResponse.success("Applicant notes updated successfully", applicant)
        );
    }
}
