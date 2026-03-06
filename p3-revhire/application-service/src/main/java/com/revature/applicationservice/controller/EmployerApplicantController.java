package com.revature.applicationservice.controller;

import com.revature.applicationservice.dto.ApiResponse;
import com.revature.applicationservice.dto.ApplicationNotesRequest;
import com.revature.applicationservice.dto.ApplicationStatsResponse;
import com.revature.applicationservice.dto.EmployerApplicantResponse;
import com.revature.applicationservice.enums.ApplicationStatus;
import com.revature.applicationservice.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employer")
public class EmployerApplicantController {

    private final ApplicationService applicationService;

    public EmployerApplicantController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/applicants")
    public ResponseEntity<ApiResponse<List<EmployerApplicantResponse>>> getApplicants(
            @RequestHeader("X-User-Id") Long employerId,
            @RequestParam(required = false) ApplicationStatus status,
            @RequestParam(required = false) String search) {
        List<EmployerApplicantResponse> applicants = applicationService.getEmployerApplicants(employerId, status, search);
        return ResponseEntity.ok(ApiResponse.success("Applicants fetched successfully", applicants));
    }

    @GetMapping("/jobs/{jobId}/applications")
    public ResponseEntity<ApiResponse<List<EmployerApplicantResponse>>> getApplicationsForJob(
            @RequestHeader("X-User-Id") Long employerId,
            @PathVariable Long jobId) {
        List<EmployerApplicantResponse> applicants = applicationService.getApplicantsForJob(employerId, jobId);
        return ResponseEntity.ok(ApiResponse.success("Applications fetched successfully", applicants));
    }

    @PatchMapping("/applicants/{applicationId}/status")
    public ResponseEntity<ApiResponse<EmployerApplicantResponse>> updateApplicationStatus(
            @RequestHeader("X-User-Id") Long employerId,
            @PathVariable Long applicationId,
            @RequestParam ApplicationStatus status) {
        EmployerApplicantResponse response = applicationService.updateApplicationStatus(employerId, applicationId, status);
        return ResponseEntity.ok(ApiResponse.success("Application status updated successfully", response));
    }

    @PatchMapping("/applicants/{applicationId}/shortlist")
    public ResponseEntity<ApiResponse<EmployerApplicantResponse>> shortlistApplicant(
            @RequestHeader("X-User-Id") Long employerId,
            @PathVariable Long applicationId) {
        EmployerApplicantResponse response = applicationService.updateApplicationStatus(
                employerId, applicationId, ApplicationStatus.SHORTLISTED);
        return ResponseEntity.ok(ApiResponse.success("Applicant shortlisted successfully", response));
    }

    @PatchMapping("/applicants/{applicationId}/reject")
    public ResponseEntity<ApiResponse<EmployerApplicantResponse>> rejectApplicant(
            @RequestHeader("X-User-Id") Long employerId,
            @PathVariable Long applicationId) {
        EmployerApplicantResponse response = applicationService.updateApplicationStatus(
                employerId, applicationId, ApplicationStatus.REJECTED);
        return ResponseEntity.ok(ApiResponse.success("Applicant rejected successfully", response));
    }

    @PatchMapping("/applicants/{applicationId}/under-review")
    public ResponseEntity<ApiResponse<EmployerApplicantResponse>> markUnderReview(
            @RequestHeader("X-User-Id") Long employerId,
            @PathVariable Long applicationId) {
        EmployerApplicantResponse response = applicationService.updateApplicationStatus(
                employerId, applicationId, ApplicationStatus.UNDER_REVIEW);
        return ResponseEntity.ok(ApiResponse.success("Application marked as under review", response));
    }

    @PatchMapping("/applicants/{applicationId}/notes")
    public ResponseEntity<ApiResponse<EmployerApplicantResponse>> updateApplicationNotes(
            @RequestHeader("X-User-Id") Long employerId,
            @PathVariable Long applicationId,
            @Valid @RequestBody ApplicationNotesRequest request) {
        EmployerApplicantResponse response = applicationService.updateApplicationNotes(
                employerId, applicationId, request.getNotes());
        return ResponseEntity.ok(ApiResponse.success("Application notes updated successfully", response));
    }

    @GetMapping("/applications/{applicationId}/resume")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getApplicantResume(
            @RequestHeader("X-User-Id") Long employerId,
            @PathVariable Long applicationId) {
        Map<String, Object> resume = applicationService.getApplicantResume(employerId, applicationId);
        return ResponseEntity.ok(ApiResponse.success("Resume fetched successfully", resume));
    }

    @GetMapping("/application-stats")
    public ResponseEntity<ApiResponse<ApplicationStatsResponse>> getApplicationStats(
            @RequestHeader("X-User-Id") Long employerId) {
        ApplicationStatsResponse stats = applicationService.getApplicationStats(employerId);
        return ResponseEntity.ok(ApiResponse.success("Application stats fetched successfully", stats));
    }
}
