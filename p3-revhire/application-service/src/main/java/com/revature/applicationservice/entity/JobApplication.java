package com.revature.applicationservice.entity;

import com.revature.applicationservice.enums.ApplicationStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "job_applications")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long jobId;

    @Column(nullable = false)
    private Long jobSeekerId;

    @Column(nullable = false)
    private Long employerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(columnDefinition = "TEXT")
    private String coverLetter;

    @Column(columnDefinition = "TEXT")
    private String withdrawReason;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime appliedAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public JobApplication() {
    }

    public JobApplication(Long id, Long jobId, Long jobSeekerId, Long employerId, ApplicationStatus status,
                         String notes, String coverLetter, String withdrawReason, LocalDateTime appliedAt,
                         LocalDateTime updatedAt) {
        this.id = id;
        this.jobId = jobId;
        this.jobSeekerId = jobSeekerId;
        this.employerId = employerId;
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

    public Long getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(Long jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    public Long getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Long employerId) {
        this.employerId = employerId;
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
        JobApplication that = (JobApplication) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(jobId, that.jobId) &&
               Objects.equals(jobSeekerId, that.jobSeekerId) &&
               Objects.equals(employerId, that.employerId) &&
               status == that.status &&
               Objects.equals(notes, that.notes) &&
               Objects.equals(coverLetter, that.coverLetter) &&
               Objects.equals(withdrawReason, that.withdrawReason) &&
               Objects.equals(appliedAt, that.appliedAt) &&
               Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, jobId, jobSeekerId, employerId, status, notes, coverLetter, withdrawReason, appliedAt, updatedAt);
    }

    @Override
    public String toString() {
        return "JobApplication{" +
                "id=" + id +
                ", jobId=" + jobId +
                ", jobSeekerId=" + jobSeekerId +
                ", employerId=" + employerId +
                ", status=" + status +
                ", notes='" + notes + '\'' +
                ", coverLetter='" + coverLetter + '\'' +
                ", withdrawReason='" + withdrawReason + '\'' +
                ", appliedAt=" + appliedAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
