package com.revature.applicationservice.dto;

import com.revature.applicationservice.enums.ApplicationStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class JobSeekerApplicationResponse {

    private Long id;
    private Long jobId;
    private String jobTitle;
    private String companyName;
    private String location;
    private ApplicationStatus status;
    private String coverLetter;
    private String withdrawReason;
    private LocalDateTime appliedAt;
    private LocalDateTime updatedAt;

    public JobSeekerApplicationResponse() {
    }

    public JobSeekerApplicationResponse(Long id, Long jobId, String jobTitle, String companyName, String location,
                                       ApplicationStatus status, String coverLetter, String withdrawReason,
                                       LocalDateTime appliedAt, LocalDateTime updatedAt) {
        this.id = id;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.location = location;
        this.status = status;
        this.coverLetter = coverLetter;
        this.withdrawReason = withdrawReason;
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

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public String getWithdrawReason() {
        return withdrawReason;
    }

    public void setWithdrawReason(String withdrawReason) {
        this.withdrawReason = withdrawReason;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobSeekerApplicationResponse that = (JobSeekerApplicationResponse) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(jobId, that.jobId) &&
               Objects.equals(jobTitle, that.jobTitle) &&
               Objects.equals(companyName, that.companyName) &&
               Objects.equals(location, that.location) &&
               status == that.status &&
               Objects.equals(coverLetter, that.coverLetter) &&
               Objects.equals(withdrawReason, that.withdrawReason) &&
               Objects.equals(appliedAt, that.appliedAt) &&
               Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, jobId, jobTitle, companyName, location, status, coverLetter, withdrawReason, appliedAt, updatedAt);
    }

    @Override
    public String toString() {
        return "JobSeekerApplicationResponse{" +
                "id=" + id +
                ", jobId=" + jobId +
                ", jobTitle='" + jobTitle + '\'' +
                ", companyName='" + companyName + '\'' +
                ", location='" + location + '\'' +
                ", status=" + status +
                ", coverLetter='" + coverLetter + '\'' +
                ", withdrawReason='" + withdrawReason + '\'' +
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
        private String jobTitle;
        private String companyName;
        private String location;
        private ApplicationStatus status;
        private String coverLetter;
        private String withdrawReason;
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

        public Builder jobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
            return this;
        }

        public Builder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Builder status(ApplicationStatus status) {
            this.status = status;
            return this;
        }

        public Builder coverLetter(String coverLetter) {
            this.coverLetter = coverLetter;
            return this;
        }

        public Builder withdrawReason(String withdrawReason) {
            this.withdrawReason = withdrawReason;
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

        public JobSeekerApplicationResponse build() {
            return new JobSeekerApplicationResponse(id, jobId, jobTitle, companyName, location, status,
                    coverLetter, withdrawReason, appliedAt, updatedAt);
        }
    }
}
