package com.revhire.employerservice.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableFeignClients(basePackages = "com.revhire.employerservice.client")
@EnableTransactionManagement
public class AppConfig {
    // Additional application configuration can be added here
}
