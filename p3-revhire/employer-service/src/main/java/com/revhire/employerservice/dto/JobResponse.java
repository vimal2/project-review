package com.revhire.employerservice.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class JobResponse {
    private Long id;
    private Long employerId;
    private String title;
    private String description;
    private String location;
    private String jobType;
    private String experienceLevel;
    private Double salary;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public JobResponse() {
    }

    public JobResponse(Long id, Long employerId, String title, String description, String location,
                      String jobType, String experienceLevel, Double salary, String status,
                      LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.employerId = employerId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.jobType = jobType;
        this.experienceLevel = experienceLevel;
        this.salary = salary;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Long employerId) {
        this.employerId = employerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
        JobResponse that = (JobResponse) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(employerId, that.employerId) &&
               Objects.equals(title, that.title) &&
               Objects.equals(description, that.description) &&
               Objects.equals(location, that.location) &&
               Objects.equals(jobType, that.jobType) &&
               Objects.equals(experienceLevel, that.experienceLevel) &&
               Objects.equals(salary, that.salary) &&
               Objects.equals(status, that.status) &&
               Objects.equals(createdAt, that.createdAt) &&
               Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employerId, title, description, location, jobType,
                          experienceLevel, salary, status, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "JobResponse{" +
                "id=" + id +
                ", employerId=" + employerId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", jobType='" + jobType + '\'' +
                ", experienceLevel='" + experienceLevel + '\'' +
                ", salary=" + salary +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long employerId;
        private String title;
        private String description;
        private String location;
        private String jobType;
        private String experienceLevel;
        private Double salary;
        private String status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder employerId(Long employerId) {
            this.employerId = employerId;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Builder jobType(String jobType) {
            this.jobType = jobType;
            return this;
        }

        public Builder experienceLevel(String experienceLevel) {
            this.experienceLevel = experienceLevel;
            return this;
        }

        public Builder salary(Double salary) {
            this.salary = salary;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public JobResponse build() {
            return new JobResponse(id, employerId, title, description, location, jobType,
                    experienceLevel, salary, status, createdAt, updatedAt);
        }
    }
}
