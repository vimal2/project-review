package com.revature.applicationservice.controller;

import com.revature.applicationservice.dto.ApiResponse;
import com.revature.applicationservice.dto.ApplyRequest;
import com.revature.applicationservice.dto.JobSeekerApplicationResponse;
import com.revature.applicationservice.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<JobSeekerApplicationResponse>> applyToJob(
            @RequestHeader("X-User-Id") Long jobSeekerId,
            @RequestParam Long jobId,
            @Valid @RequestBody ApplyRequest request) {
        JobSeekerApplicationResponse response = applicationService.applyToJob(jobSeekerId, jobId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Application submitted successfully", response));
    }

    @DeleteMapping("/job/{jobId}")
    public ResponseEntity<ApiResponse<Void>> deleteApplicationsByJobId(@PathVariable Long jobId) {
        applicationService.deleteApplicationsByJobId(jobId);
        return ResponseEntity.ok(ApiResponse.success("Applications deleted successfully"));
    }
}
