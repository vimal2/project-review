package com.revature.applicationservice.controller;

import com.revature.applicationservice.dto.ApiResponse;
import com.revature.applicationservice.dto.ApplyRequest;
import com.revature.applicationservice.dto.JobSeekerApplicationResponse;
import com.revature.applicationservice.dto.WithdrawRequest;
import com.revature.applicationservice.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobseeker/jobs")
public class JobSeekerApplicationController {

    private final ApplicationService applicationService;

    public JobSeekerApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/applied")
    public ResponseEntity<ApiResponse<List<Long>>> getAppliedJobIds(
            @RequestHeader("X-User-Id") Long jobSeekerId) {
        List<Long> appliedJobIds = applicationService.getAppliedJobIds(jobSeekerId);
        return ResponseEntity.ok(ApiResponse.success("Applied job IDs fetched successfully", appliedJobIds));
    }

    @GetMapping("/applications")
    public ResponseEntity<ApiResponse<List<JobSeekerApplicationResponse>>> getApplications(
            @RequestHeader("X-User-Id") Long jobSeekerId) {
        List<JobSeekerApplicationResponse> applications = applicationService.getJobSeekerApplications(jobSeekerId);
        return ResponseEntity.ok(ApiResponse.success("Applications fetched successfully", applications));
    }

    @PostMapping("/{jobId}/apply")
    public ResponseEntity<ApiResponse<JobSeekerApplicationResponse>> applyToJob(
            @RequestHeader("X-User-Id") Long jobSeekerId,
            @PathVariable Long jobId,
            @Valid @RequestBody ApplyRequest request) {
        JobSeekerApplicationResponse response = applicationService.applyToJob(jobSeekerId, jobId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Application submitted successfully", response));
    }

    @PostMapping("/{jobId}/withdraw")
    public ResponseEntity<ApiResponse<JobSeekerApplicationResponse>> withdrawApplication(
            @RequestHeader("X-User-Id") Long jobSeekerId,
            @PathVariable Long jobId,
            @Valid @RequestBody WithdrawRequest request) {
        JobSeekerApplicationResponse response = applicationService.withdrawApplication(jobSeekerId, jobId, request);
        return ResponseEntity.ok(ApiResponse.success("Application withdrawn successfully", response));
    }

    @PostMapping("/applications/{applicationId}/withdraw")
    public ResponseEntity<ApiResponse<JobSeekerApplicationResponse>> withdrawApplicationById(
            @RequestHeader("X-User-Id") Long jobSeekerId,
            @PathVariable Long applicationId,
            @Valid @RequestBody WithdrawRequest request) {
        JobSeekerApplicationResponse response = applicationService.withdrawApplicationById(jobSeekerId, applicationId, request);
        return ResponseEntity.ok(ApiResponse.success("Application withdrawn successfully", response));
    }
}
