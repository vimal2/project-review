package com.revature.applicationservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "AUTH-SERVICE")
public interface AuthServiceClient {

    @GetMapping("/api/auth/users/{userId}")
    Map<String, Object> getUserDetails(@PathVariable Long userId);
}
