package com.revature.applicationservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "JOBSEEKER-SERVICE")
public interface JobSeekerServiceClient {

    @GetMapping("/api/jobseeker/{jobSeekerId}/profile")
    Map<String, Object> getJobSeekerProfile(@PathVariable Long jobSeekerId);

    @GetMapping("/api/jobseeker/{jobSeekerId}/resume")
    Map<String, Object> getJobSeekerResume(@PathVariable Long jobSeekerId);
}
