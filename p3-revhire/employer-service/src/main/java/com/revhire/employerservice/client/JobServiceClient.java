package com.revhire.employerservice.client;

import com.revhire.employerservice.dto.ApiResponse;
import com.revhire.employerservice.dto.JobResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "JOB-SERVICE")
public interface JobServiceClient {

    /**
     * Get all jobs posted by an employer
     * @param employerId the employer user ID
     * @return ApiResponse containing list of jobs
     */
    @GetMapping("/api/jobs/employer")
    ApiResponse<List<JobResponse>> getJobsByEmployer(@RequestParam("employerId") Long employerId);

    /**
     * Get job statistics for an employer
     * @param employerId the employer user ID
     * @return ApiResponse containing job statistics
     */
    @GetMapping("/api/jobs/employer/statistics")
    ApiResponse<JobStatistics> getJobStatistics(@RequestParam("employerId") Long employerId);

    /**
     * Job statistics DTO
     */
    class JobStatistics {
        public Long totalJobs;
        public Long activeJobs;
        public Long closedJobs;
    }
}
