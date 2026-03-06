package com.revature.revhire.authservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.cloud.discovery.enabled=false",
    "eureka.client.enabled=false"
})
class AuthServiceApplicationTests {

    @Test
    void contextLoads() {
        // Test that the application context loads successfully
    }
}
