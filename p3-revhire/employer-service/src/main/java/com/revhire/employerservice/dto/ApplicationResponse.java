package com.revhire.employerservice.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class ApplicationResponse {
    private Long id;
    private Long jobId;
    private Long jobseekerId;
    private String status;
    private String coverLetter;
    private LocalDateTime appliedAt;
    private LocalDateTime updatedAt;
    private Long applicantId;
    private String applicantName;
    private String applicantEmail;
    private String jobTitle;

    public ApplicationResponse() {
    }

    public ApplicationResponse(Long id, Long jobId, Long jobseekerId, String status, String coverLetter,
                              LocalDateTime appliedAt, LocalDateTime updatedAt) {
        this.id = id;
        this.jobId = jobId;
        this.jobseekerId = jobseekerId;
        this.status = status;
        this.coverLetter = coverLetter;
        this.appliedAt = appliedAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getJobseekerId() {
        return jobseekerId;
    }

    public void setJobseekerId(Long jobseekerId) {
        this.jobseekerId = jobseekerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantEmail() {
        return applicantEmail;
    }

    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationResponse that = (ApplicationResponse) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(jobId, that.jobId) &&
               Objects.equals(jobseekerId, that.jobseekerId) &&
               Objects.equals(status, that.status) &&
               Objects.equals(coverLetter, that.coverLetter) &&
               Objects.equals(appliedAt, that.appliedAt) &&
               Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, jobId, jobseekerId, status, coverLetter, appliedAt, updatedAt);
    }

    @Override
    public String toString() {
        return "ApplicationResponse{" +
                "id=" + id +
                ", jobId=" + jobId +
                ", jobseekerId=" + jobseekerId +
                ", status='" + status + '\'' +
                ", coverLetter='" + coverLetter + '\'' +
                ", appliedAt=" + appliedAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long jobId;
        private Long jobseekerId;
        private String status;
        private String coverLetter;
        private LocalDateTime appliedAt;
        private LocalDateTime updatedAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder jobId(Long jobId) {
            this.jobId = jobId;
            return this;
        }

        public Builder jobseekerId(Long jobseekerId) {
            this.jobseekerId = jobseekerId;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder coverLetter(String coverLetter) {
            this.coverLetter = coverLetter;
            return this;
        }

        public Builder appliedAt(LocalDateTime appliedAt) {
            this.appliedAt = appliedAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ApplicationResponse build() {
            return new ApplicationResponse(id, jobId, jobseekerId, status, coverLetter, appliedAt, updatedAt);
        }
    }
}
