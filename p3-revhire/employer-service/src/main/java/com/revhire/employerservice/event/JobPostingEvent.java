package com.revhire.employerservice.event;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Event published when a job posting is created, updated, or status changed
 */
public class JobPostingEvent {

    private Long jobId;
    private Long employerId;
    private String companyName;
    private String title;
    private String skills;
    private String location;
    private String jobType;
    private String status;
    private EventType eventType;
    private LocalDateTime timestamp;

    public enum EventType {
        JOB_CREATED,
        JOB_UPDATED,
        JOB_CLOSED,
        JOB_REOPENED,
        JOB_FILLED,
        JOB_DELETED
    }

    public JobPostingEvent() {
    }

    public JobPostingEvent(Long jobId, Long employerId, String companyName, String title,
                          String skills, String location, String jobType, String status,
                          EventType eventType, LocalDateTime timestamp) {
        this.jobId = jobId;
        this.employerId = employerId;
        this.companyName = companyName;
        this.title = title;
        this.skills = skills;
        this.location = location;
        this.jobType = jobType;
        this.status = status;
        this.eventType = eventType;
        this.timestamp = timestamp;
    }

    public JobPostingEvent(Long jobId, Long employerId, String companyName, String title,
                          String skills, String location, EventType eventType) {
        this.jobId = jobId;
        this.employerId = employerId;
        this.companyName = companyName;
        this.title = title;
        this.skills = skills;
        this.location = location;
        this.eventType = eventType;
        this.timestamp = LocalDateTime.now();
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Long employerId) {
        this.employerId = employerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobPostingEvent that = (JobPostingEvent) o;
        return Objects.equals(jobId, that.jobId) &&
               Objects.equals(employerId, that.employerId) &&
               Objects.equals(companyName, that.companyName) &&
               Objects.equals(title, that.title) &&
               Objects.equals(skills, that.skills) &&
               Objects.equals(location, that.location) &&
               Objects.equals(jobType, that.jobType) &&
               Objects.equals(status, that.status) &&
               eventType == that.eventType &&
               Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, employerId, companyName, title, skills, location,
                          jobType, status, eventType, timestamp);
    }

    @Override
    public String toString() {
        return "JobPostingEvent{" +
                "jobId=" + jobId +
                ", employerId=" + employerId +
                ", companyName='" + companyName + '\'' +
                ", title='" + title + '\'' +
                ", skills='" + skills + '\'' +
                ", location='" + location + '\'' +
                ", jobType='" + jobType + '\'' +
                ", status='" + status + '\'' +
                ", eventType=" + eventType +
                ", timestamp=" + timestamp +
                '}';
    }
}
