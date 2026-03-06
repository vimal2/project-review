package com.revature.revhire.authservice.dto;

import com.revature.revhire.authservice.enums.EmploymentStatus;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class ProfileCompletionRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Mobile number is required")
    private String mobileNumber;

    @NotBlank(message = "Location is required")
    private String location;

    private EmploymentStatus employmentStatus;

    public ProfileCompletionRequest() {
    }

    public ProfileCompletionRequest(String fullName, String mobileNumber, String location,
                                   EmploymentStatus employmentStatus) {
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.location = location;
        this.employmentStatus = employmentStatus;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public EmploymentStatus getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(EmploymentStatus employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileCompletionRequest that = (ProfileCompletionRequest) o;
        return Objects.equals(fullName, that.fullName) &&
               Objects.equals(mobileNumber, that.mobileNumber) &&
               Objects.equals(location, that.location) &&
               employmentStatus == that.employmentStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, mobileNumber, location, employmentStatus);
    }

    @Override
    public String toString() {
        return "ProfileCompletionRequest{" +
                "fullName='" + fullName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", location='" + location + '\'' +
                ", employmentStatus=" + employmentStatus +
                '}';
    }
}
