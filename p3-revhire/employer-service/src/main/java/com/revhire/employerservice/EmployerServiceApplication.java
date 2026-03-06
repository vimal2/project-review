package com.revhire.employerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main application class for Employer Service
 * This service manages employer company profiles and aggregates statistics
 * from job-service and application-service
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class EmployerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployerServiceApplication.class, args);
    }
}
