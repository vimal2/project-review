package com.revature.applicationservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "JOB-SERVICE")
public interface JobServiceClient {

    @GetMapping("/api/jobs/{jobId}")
    Map<String, Object> getJobDetails(@PathVariable Long jobId);
}
