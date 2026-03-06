package com.revature.applicationservice.dto;

import jakarta.validation.constraints.Size;

import java.util.Objects;

public class ApplyRequest {

    @Size(max = 5000, message = "Cover letter cannot exceed 5000 characters")
    private String coverLetter;

    public ApplyRequest() {
    }

    public ApplyRequest(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplyRequest that = (ApplyRequest) o;
        return Objects.equals(coverLetter, that.coverLetter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coverLetter);
    }

    @Override
    public String toString() {
        return "ApplyRequest{" +
                "coverLetter='" + coverLetter + '\'' +
                '}';
    }
}
