package com.revhire.employerservice.client;

import com.revhire.employerservice.dto.ApiResponse;
import com.revhire.employerservice.dto.ApplicationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "APPLICATION-SERVICE")
public interface ApplicationServiceClient {

    /**
     * Get all applications for an employer's jobs
     * @param employerId the employer user ID
     * @return ApiResponse containing list of applications
     */
    @GetMapping("/api/applications/employer")
    ApiResponse<List<ApplicationResponse>> getApplicationsByEmployer(@RequestParam("employerId") Long employerId);

    /**
     * Get application statistics for an employer
     * @param employerId the employer user ID
     * @return ApiResponse containing application statistics
     */
    @GetMapping("/api/applications/employer/statistics")
    ApiResponse<ApplicationStatistics> getApplicationStatistics(@RequestParam("employerId") Long employerId);

    /**
     * Application statistics DTO
     */
    class ApplicationStatistics {
        public Long totalApplications;
        public Map<String, Long> applicationsByStatus;
    }
}
