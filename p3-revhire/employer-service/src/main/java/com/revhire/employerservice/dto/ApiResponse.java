package com.revhire.employerservice.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponse() {
    }

    public ApiResponse(boolean success, String message, T data, LocalDateTime timestamp) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
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
        ApiResponse<?> that = (ApiResponse<?>) o;
        return success == that.success &&
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

    public static <T> ApiResponse<T> success(String message, T data) {
        return new Builder<T>()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(String message) {
        return new Builder<T>()
                .success(false)
                .message(message)
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> Builder<T> builder() {
        return new Builder<T>();
    }

    public static class Builder<T> {
        private boolean success;
        private String message;
        private T data;
        private LocalDateTime timestamp;

        public Builder<T> success(boolean success) {
            this.success = success;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Builder<T> timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ApiResponse<T> build() {
            return new ApiResponse<>(success, message, data, timestamp);
        }
    }
}
