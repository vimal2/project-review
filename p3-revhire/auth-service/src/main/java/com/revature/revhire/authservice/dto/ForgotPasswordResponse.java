package com.revature.revhire.authservice.dto;

import java.util.Objects;

public class ForgotPasswordResponse {

    private String resetToken;
    private String message;
    private Long expiresInMinutes;

    public ForgotPasswordResponse() {
    }

    public ForgotPasswordResponse(String resetToken, String message, Long expiresInMinutes) {
        this.resetToken = resetToken;
        this.message = message;
        this.expiresInMinutes = expiresInMinutes;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getExpiresInMinutes() {
        return expiresInMinutes;
    }

    public void setExpiresInMinutes(Long expiresInMinutes) {
        this.expiresInMinutes = expiresInMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForgotPasswordResponse that = (ForgotPasswordResponse) o;
        return Objects.equals(resetToken, that.resetToken) &&
               Objects.equals(message, that.message) &&
               Objects.equals(expiresInMinutes, that.expiresInMinutes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resetToken, message, expiresInMinutes);
    }

    @Override
    public String toString() {
        return "ForgotPasswordResponse{" +
                "resetToken='" + resetToken + '\'' +
                ", message='" + message + '\'' +
                ", expiresInMinutes=" + expiresInMinutes +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String resetToken;
        private String message;
        private Long expiresInMinutes;

        public Builder resetToken(String resetToken) {
            this.resetToken = resetToken;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder expiresInMinutes(Long expiresInMinutes) {
            this.expiresInMinutes = expiresInMinutes;
            return this;
        }

        public ForgotPasswordResponse build() {
            return new ForgotPasswordResponse(resetToken, message, expiresInMinutes);
        }
    }
}
