package com.revature.revhire.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class ChangePasswordRequest {

    @NotBlank(message = "Current password is required")
    private String currentPassword;

    @NotBlank(message = "New password is required")
    @Size(min = 6, message = "New password must be at least 6 characters")
    private String newPassword;

    public ChangePasswordRequest() {
    }

    public ChangePasswordRequest(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
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
        ChangePasswordRequest that = (ChangePasswordRequest) o;
        return Objects.equals(currentPassword, that.currentPassword) &&
               Objects.equals(newPassword, that.newPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPassword, newPassword);
    }

    @Override
    public String toString() {
        return "ChangePasswordRequest{" +
                "currentPassword='" + currentPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
