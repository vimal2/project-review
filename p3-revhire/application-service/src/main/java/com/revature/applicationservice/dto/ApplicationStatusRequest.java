package com.revature.applicationservice.dto;

import com.revature.applicationservice.enums.ApplicationStatus;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class ApplicationStatusRequest {

    @NotNull(message = "Status is required")
    private ApplicationStatus status;

    public ApplicationStatusRequest() {
    }

    public ApplicationStatusRequest(ApplicationStatus status) {
        this.status = status;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationStatusRequest that = (ApplicationStatusRequest) o;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }

    @Override
    public String toString() {
        return "ApplicationStatusRequest{" +
                "status=" + status +
                '}';
    }
}
