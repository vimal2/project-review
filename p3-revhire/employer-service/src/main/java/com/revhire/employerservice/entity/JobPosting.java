package com.revhire.employerservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "job_postings")
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Employer ID is required")
    @Column(name = "employer_id", nullable = false)
    private Long employerId;

    @Size(max = 120, message = "Company name must not exceed 120 characters")
    @Column(name = "company_name", length = 120)
    private String companyName;

    @NotBlank(message = "Job title is required")
    @Size(min = 5, max = 150, message = "Job title must be between 5 and 150 characters")
    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @NotBlank(message = "Job description is required")
    @Size(min = 50, max = 2000, message = "Job description must be between 50 and 2000 characters")
    @Column(name = "description", nullable = false, length = 2000)
    private String description;

    @Size(max = 1000, message = "Skills must not exceed 1000 characters")
    @Column(name = "skills", length = 1000)
    private String skills;

    @Size(max = 250, message = "Education must not exceed 250 characters")
    @Column(name = "education", length = 250)
    private String education;

    @NotNull(message = "Maximum experience years is required")
    @Min(value = 0, message = "Experience must be at least 0")
    @Max(value = 30, message = "Experience must not exceed 30 years")
    @Column(name = "max_experience_years", nullable = false)
    private Integer maxExperienceYears;

    @NotBlank(message = "Location is required")
    @Size(max = 120, message = "Location must not exceed 120 characters")
    @Column(name = "location", nullable = false, length = 120)
    private String location;

    @NotNull(message = "Minimum salary is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Minimum salary must be at least 0")
    @Column(name = "min_salary", nullable = false, precision = 12, scale = 2)
    private BigDecimal minSalary;

    @NotNull(message = "Maximum salary is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Maximum salary must be at least 0")
    @Column(name = "max_salary", nullable = false, precision = 12, scale = 2)
    private BigDecimal maxSalary;

    @NotBlank(message = "Job type is required")
    @Size(max = 50, message = "Job type must not exceed 50 characters")
    @Column(name = "job_type", nullable = false, length = 50)
    private String jobType;

    @NotNull(message = "Number of openings is required")
    @Min(value = 1, message = "Openings must be at least 1")
    @Max(value = 500, message = "Openings must not exceed 500")
    @Column(name = "openings", nullable = false)
    private Integer openings;

    @Column(name = "application_deadline")
    private LocalDate applicationDeadline;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private JobStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.status == null) {
            this.status = JobStatus.OPEN;
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public JobPosting() {
    }

    public JobPosting(Long id, Long employerId, String companyName, String title, String description,
                     String skills, String education, Integer maxExperienceYears, String location,
                     BigDecimal minSalary, BigDecimal maxSalary, String jobType, Integer openings,
                     LocalDate applicationDeadline, JobStatus status, LocalDateTime createdAt,
                     LocalDateTime updatedAt) {
        this.id = id;
        this.employerId = employerId;
        this.companyName = companyName;
        this.title = title;
        this.description = description;
        this.skills = skills;
        this.education = education;
        this.maxExperienceYears = maxExperienceYears;
        this.location = location;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.jobType = jobType;
        this.openings = openings;
        this.applicationDeadline = applicationDeadline;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Integer getMaxExperienceYears() {
        return maxExperienceYears;
    }

    public void setMaxExperienceYears(Integer maxExperienceYears) {
        this.maxExperienceYears = maxExperienceYears;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(BigDecimal minSalary) {
        this.minSalary = minSalary;
    }

    public BigDecimal getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(BigDecimal maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Integer getOpenings() {
        return openings;
    }

    public void setOpenings(Integer openings) {
        this.openings = openings;
    }

    public LocalDate getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(LocalDate applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
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
        JobPosting that = (JobPosting) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(employerId, that.employerId) &&
               Objects.equals(companyName, that.companyName) &&
               Objects.equals(title, that.title) &&
               Objects.equals(description, that.description) &&
               Objects.equals(skills, that.skills) &&
               Objects.equals(education, that.education) &&
               Objects.equals(maxExperienceYears, that.maxExperienceYears) &&
               Objects.equals(location, that.location) &&
               Objects.equals(minSalary, that.minSalary) &&
               Objects.equals(maxSalary, that.maxSalary) &&
               Objects.equals(jobType, that.jobType) &&
               Objects.equals(openings, that.openings) &&
               Objects.equals(applicationDeadline, that.applicationDeadline) &&
               status == that.status &&
               Objects.equals(createdAt, that.createdAt) &&
               Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employerId, companyName, title, description, skills, education,
                          maxExperienceYears, location, minSalary, maxSalary, jobType, openings,
                          applicationDeadline, status, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "JobPosting{" +
                "id=" + id +
                ", employerId=" + employerId +
                ", companyName='" + companyName + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", skills='" + skills + '\'' +
                ", education='" + education + '\'' +
                ", maxExperienceYears=" + maxExperienceYears +
                ", location='" + location + '\'' +
                ", minSalary=" + minSalary +
                ", maxSalary=" + maxSalary +
                ", jobType='" + jobType + '\'' +
                ", openings=" + openings +
                ", applicationDeadline=" + applicationDeadline +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
