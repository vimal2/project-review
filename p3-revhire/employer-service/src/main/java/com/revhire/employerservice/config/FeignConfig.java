package com.revhire.employerservice.config;

import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    /**
     * Configure Feign logging level
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * Custom error decoder for Feign clients
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomFeignErrorDecoder();
    }

    /**
     * Custom error decoder implementation
     */
    static class CustomFeignErrorDecoder implements ErrorDecoder {
        private final ErrorDecoder defaultErrorDecoder = new Default();

        @Override
        public Exception decode(String methodKey, feign.Response response) {
            // You can customize error handling here based on response status
            switch (response.status()) {
                case 400:
                    return new RuntimeException("Bad Request to external service");
                case 404:
                    return new RuntimeException("Resource not found in external service");
                case 503:
                    return new RuntimeException("External service unavailable");
                default:
                    return defaultErrorDecoder.decode(methodKey, response);
            }
        }
    }
}
