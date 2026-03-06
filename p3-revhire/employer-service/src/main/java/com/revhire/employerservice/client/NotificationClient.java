package com.revhire.employerservice.client;

import com.revhire.employerservice.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "NOTIFICATION-SERVICE")
public interface NotificationClient {

    /**
     * Send notification to users
     * @param request notification request
     * @return ApiResponse
     */
    @PostMapping("/api/notifications/send")
    ApiResponse<Void> sendNotification(@RequestBody NotificationRequest request);

    /**
     * Notification request DTO
     */
    class NotificationRequest {
        public Long userId;
        public String message;
        public String type;
        public Long relatedEntityId;
        public String relatedEntityType;

        public NotificationRequest() {
        }

        public NotificationRequest(Long userId, String message, String type, Long relatedEntityId, String relatedEntityType) {
            this.userId = userId;
            this.message = message;
            this.type = type;
            this.relatedEntityId = relatedEntityId;
            this.relatedEntityType = relatedEntityType;
        }
    }
}
