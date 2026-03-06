package com.revature.revhire.authservice.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class ApiResponse {

    private Boolean success;
    private String message;
    private Object data;
    private LocalDateTime timestamp;

    public ApiResponse() {
    }

    public ApiResponse(Boolean success, String message, Object data, LocalDateTime timestamp) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiResponse that = (ApiResponse) o;
        return Objects.equals(success, that.success) &&
               Objects.equals(message, that.message) &&
               Objects.equals(data, that.data) &&
               Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, message, data, timestamp);
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", timestamp=" + timestamp +
                '}';
    }

    public static ApiResponse success(String message) {
        return ApiResponse.builder()
                .success(true)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ApiResponse success(String message, Object data) {
        return ApiResponse.builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ApiResponse error(String message) {
        return ApiResponse.builder()
                .success(false)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Boolean success;
        private String message;
        private Object data;
        private LocalDateTime timestamp;

        public Builder success(Boolean success) {
            this.success = success;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ApiResponse build() {
            return new ApiResponse(success, message, data, timestamp);
        }
    }
}
