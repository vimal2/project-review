package com.revature.revhire.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class ResetPasswordRequest {

    @NotBlank(message = "Reset token is required")
    private String resetToken;

    @NotBlank(message = "New password is required")
    @Size(min = 6, message = "New password must be at least 6 characters")
    private String newPassword;

    public ResetPasswordRequest() {
    }

    public ResetPasswordRequest(String resetToken, String newPassword) {
        this.resetToken = resetToken;
        this.newPassword = newPassword;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResetPasswordRequest that = (ResetPasswordRequest) o;
        return Objects.equals(resetToken, that.resetToken) &&
               Objects.equals(newPassword, that.newPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resetToken, newPassword);
    }

    @Override
    public String toString() {
        return "ResetPasswordRequest{" +
                "resetToken='" + resetToken + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
