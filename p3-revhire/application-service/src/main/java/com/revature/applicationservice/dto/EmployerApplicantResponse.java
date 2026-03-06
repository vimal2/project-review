package com.revature.applicationservice.dto;

import com.revature.applicationservice.enums.ApplicationStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class EmployerApplicantResponse {

    private Long id;
    private Long jobId;
    private String jobTitle;
    private Long jobSeekerId;
    private String jobSeekerName;
    private String jobSeekerEmail;
    private String jobSeekerPhone;
    private ApplicationStatus status;
    private String notes;
    private String coverLetter;
    private String withdrawReason;
    private LocalDateTime appliedAt;
    private LocalDateTime updatedAt;

    public EmployerApplicantResponse() {
    }

    public EmployerApplicantResponse(Long id, Long jobId, String jobTitle, Long jobSeekerId, String jobSeekerName,
                                    String jobSeekerEmail, String jobSeekerPhone, ApplicationStatus status,
                                    String notes, String coverLetter, String withdrawReason,
                                    LocalDateTime appliedAt, LocalDateTime updatedAt) {
        this.id = id;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.jobSeekerId = jobSeekerId;
        this.jobSeekerName = jobSeekerName;
        this.jobSeekerEmail = jobSeekerEmail;
        this.jobSeekerPhone = jobSeekerPhone;
        this.status = status;
        this.notes = notes;
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

    public Long getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(Long jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    public String getJobSeekerName() {
        return jobSeekerName;
    }

    public void setJobSeekerName(String jobSeekerName) {
        this.jobSeekerName = jobSeekerName;
    }

    public String getJobSeekerEmail() {
        return jobSeekerEmail;
    }

    public void setJobSeekerEmail(String jobSeekerEmail) {
        this.jobSeekerEmail = jobSeekerEmail;
    }

    public String getJobSeekerPhone() {
        return jobSeekerPhone;
    }

    public void setJobSeekerPhone(String jobSeekerPhone) {
        this.jobSeekerPhone = jobSeekerPhone;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
        EmployerApplicantResponse that = (EmployerApplicantResponse) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(jobId, that.jobId) &&
               Objects.equals(jobTitle, that.jobTitle) &&
               Objects.equals(jobSeekerId, that.jobSeekerId) &&
               Objects.equals(jobSeekerName, that.jobSeekerName) &&
               Objects.equals(jobSeekerEmail, that.jobSeekerEmail) &&
               Objects.equals(jobSeekerPhone, that.jobSeekerPhone) &&
               status == that.status &&
               Objects.equals(notes, that.notes) &&
               Objects.equals(coverLetter, that.coverLetter) &&
               Objects.equals(withdrawReason, that.withdrawReason) &&
               Objects.equals(appliedAt, that.appliedAt) &&
               Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, jobId, jobTitle, jobSeekerId, jobSeekerName, jobSeekerEmail, jobSeekerPhone,
                status, notes, coverLetter, withdrawReason, appliedAt, updatedAt);
    }

    @Override
    public String toString() {
        return "EmployerApplicantResponse{" +
                "id=" + id +
                ", jobId=" + jobId +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobSeekerId=" + jobSeekerId +
                ", jobSeekerName='" + jobSeekerName + '\'' +
                ", jobSeekerEmail='" + jobSeekerEmail + '\'' +
                ", jobSeekerPhone='" + jobSeekerPhone + '\'' +
                ", status=" + status +
                ", notes='" + notes + '\'' +
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
        private Long jobSeekerId;
        private String jobSeekerName;
        private String jobSeekerEmail;
        private String jobSeekerPhone;
        private ApplicationStatus status;
        private String notes;
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

        public Builder jobSeekerId(Long jobSeekerId) {
            this.jobSeekerId = jobSeekerId;
            return this;
        }

        public Builder jobSeekerName(String jobSeekerName) {
            this.jobSeekerName = jobSeekerName;
            return this;
        }

        public Builder jobSeekerEmail(String jobSeekerEmail) {
            this.jobSeekerEmail = jobSeekerEmail;
            return this;
        }

        public Builder jobSeekerPhone(String jobSeekerPhone) {
            this.jobSeekerPhone = jobSeekerPhone;
            return this;
        }

        public Builder status(ApplicationStatus status) {
            this.status = status;
            return this;
        }

        public Builder notes(String notes) {
            this.notes = notes;
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

        public EmployerApplicantResponse build() {
            return new EmployerApplicantResponse(id, jobId, jobTitle, jobSeekerId, jobSeekerName,
                    jobSeekerEmail, jobSeekerPhone, status, notes, coverLetter, withdrawReason,
                    appliedAt, updatedAt);
        }
    }
}
