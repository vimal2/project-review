package com.revhire.employerservice.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class ApplicantResponse {

    private Long applicationId;
    private Long jobId;
    private String jobTitle;
    private Long applicantId;
    private String applicantUsername;
    private String applicantFullName;
    private String applicantEmail;
    private String applicantSkills;
    private String resumeSummary;
    private String resumeFileName;
    private String resumeFileType;
    private Long resumeFileSize;
    private String resumeFileReference;
    private String notes;
    private String status;
    private LocalDateTime appliedAt;

    public ApplicantResponse() {
    }

    public ApplicantResponse(Long applicationId, Long jobId, String jobTitle, Long applicantId,
                            String applicantUsername, String applicantFullName, String applicantEmail,
                            String applicantSkills, String resumeSummary, String resumeFileName,
                            String resumeFileType, Long resumeFileSize, String resumeFileReference,
                            String notes, String status, LocalDateTime appliedAt) {
        this.applicationId = applicationId;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.applicantId = applicantId;
        this.applicantUsername = applicantUsername;
        this.applicantFullName = applicantFullName;
        this.applicantEmail = applicantEmail;
        this.applicantSkills = applicantSkills;
        this.resumeSummary = resumeSummary;
        this.resumeFileName = resumeFileName;
        this.resumeFileType = resumeFileType;
        this.resumeFileSize = resumeFileSize;
        this.resumeFileReference = resumeFileReference;
        this.notes = notes;
        this.status = status;
        this.appliedAt = appliedAt;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
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

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public String getApplicantUsername() {
        return applicantUsername;
    }

    public void setApplicantUsername(String applicantUsername) {
        this.applicantUsername = applicantUsername;
    }

    public String getApplicantFullName() {
        return applicantFullName;
    }

    public void setApplicantFullName(String applicantFullName) {
        this.applicantFullName = applicantFullName;
    }

    public String getApplicantEmail() {
        return applicantEmail;
    }

    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    public String getApplicantSkills() {
        return applicantSkills;
    }

    public void setApplicantSkills(String applicantSkills) {
        this.applicantSkills = applicantSkills;
    }

    public String getResumeSummary() {
        return resumeSummary;
    }

    public void setResumeSummary(String resumeSummary) {
        this.resumeSummary = resumeSummary;
    }

    public String getResumeFileName() {
        return resumeFileName;
    }

    public void setResumeFileName(String resumeFileName) {
        this.resumeFileName = resumeFileName;
    }

    public String getResumeFileType() {
        return resumeFileType;
    }

    public void setResumeFileType(String resumeFileType) {
        this.resumeFileType = resumeFileType;
    }

    public Long getResumeFileSize() {
        return resumeFileSize;
    }

    public void setResumeFileSize(Long resumeFileSize) {
        this.resumeFileSize = resumeFileSize;
    }

    public String getResumeFileReference() {
        return resumeFileReference;
    }

    public void setResumeFileReference(String resumeFileReference) {
        this.resumeFileReference = resumeFileReference;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicantResponse that = (ApplicantResponse) o;
        return Objects.equals(applicationId, that.applicationId) &&
               Objects.equals(jobId, that.jobId) &&
               Objects.equals(jobTitle, that.jobTitle) &&
               Objects.equals(applicantId, that.applicantId) &&
               Objects.equals(applicantUsername, that.applicantUsername) &&
               Objects.equals(applicantFullName, that.applicantFullName) &&
               Objects.equals(applicantEmail, that.applicantEmail) &&
               Objects.equals(applicantSkills, that.applicantSkills) &&
               Objects.equals(resumeSummary, that.resumeSummary) &&
               Objects.equals(resumeFileName, that.resumeFileName) &&
               Objects.equals(resumeFileType, that.resumeFileType) &&
               Objects.equals(resumeFileSize, that.resumeFileSize) &&
               Objects.equals(resumeFileReference, that.resumeFileReference) &&
               Objects.equals(notes, that.notes) &&
               Objects.equals(status, that.status) &&
               Objects.equals(appliedAt, that.appliedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationId, jobId, jobTitle, applicantId, applicantUsername,
                          applicantFullName, applicantEmail, applicantSkills, resumeSummary,
                          resumeFileName, resumeFileType, resumeFileSize, resumeFileReference,
                          notes, status, appliedAt);
    }

    @Override
    public String toString() {
        return "ApplicantResponse{" +
                "applicationId=" + applicationId +
                ", jobId=" + jobId +
                ", jobTitle='" + jobTitle + '\'' +
                ", applicantId=" + applicantId +
                ", applicantUsername='" + applicantUsername + '\'' +
                ", applicantFullName='" + applicantFullName + '\'' +
                ", applicantEmail='" + applicantEmail + '\'' +
                ", applicantSkills='" + applicantSkills + '\'' +
                ", resumeSummary='" + resumeSummary + '\'' +
                ", resumeFileName='" + resumeFileName + '\'' +
                ", resumeFileType='" + resumeFileType + '\'' +
                ", resumeFileSize=" + resumeFileSize +
                ", resumeFileReference='" + resumeFileReference + '\'' +
                ", notes='" + notes + '\'' +
                ", status='" + status + '\'' +
                ", appliedAt=" + appliedAt +
                '}';
    }
}
