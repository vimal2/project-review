package com.revhire.employerservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Configuration for asynchronous processing
 * Required for @Async annotation to work in event listeners
 */
@Configuration
@EnableAsync
public class AsyncConfig {
    // Async processing is enabled
    // Default executor will be used unless custom executor is defined
}
