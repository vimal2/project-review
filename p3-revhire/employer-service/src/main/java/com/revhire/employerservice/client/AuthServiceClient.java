package com.revhire.employerservice.client;

import com.revhire.employerservice.dto.ApiResponse;
import com.revhire.employerservice.dto.UserDetailsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "AUTH-SERVICE")
public interface AuthServiceClient {

    /**
     * Get user details by user ID
     * @param userId the user ID
     * @return ApiResponse containing user details
     */
    @GetMapping("/api/auth/users/{userId}")
    ApiResponse<UserDetailsResponse> getUserById(@PathVariable("userId") Long userId);
}
