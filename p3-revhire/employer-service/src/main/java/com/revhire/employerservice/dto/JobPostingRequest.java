package com.revhire.employerservice.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class JobPostingRequest {

    @Size(max = 120, message = "Company name must not exceed 120 characters")
    private String companyName;

    @NotBlank(message = "Job title is required")
    @Size(min = 5, max = 150, message = "Job title must be between 5 and 150 characters")
    private String title;

    @NotBlank(message = "Job description is required")
    @Size(min = 50, max = 2000, message = "Job description must be between 50 and 2000 characters")
    private String description;

    @NotBlank(message = "Skills are required")
    @Size(max = 1000, message = "Skills must not exceed 1000 characters")
    private String skills;

    @Size(max = 250, message = "Education must not exceed 250 characters")
    private String education;

    @NotNull(message = "Maximum experience years is required")
    @Min(value = 0, message = "Experience must be at least 0")
    @Max(value = 30, message = "Experience must not exceed 30 years")
    private Integer maxExperienceYears;

    @NotBlank(message = "Location is required")
    @Size(max = 120, message = "Location must not exceed 120 characters")
    private String location;

    @NotNull(message = "Minimum salary is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Minimum salary must be at least 0")
    private BigDecimal minSalary;

    @NotNull(message = "Maximum salary is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Maximum salary must be at least 0")
    private BigDecimal maxSalary;

    @NotBlank(message = "Job type is required")
    @Size(max = 50, message = "Job type must not exceed 50 characters")
    private String jobType;

    @NotNull(message = "Number of openings is required")
    @Min(value = 1, message = "Openings must be at least 1")
    @Max(value = 500, message = "Openings must not exceed 500")
    private Integer openings;

    private LocalDate applicationDeadline;

    public JobPostingRequest() {
    }

    public JobPostingRequest(String companyName, String title, String description, String skills,
                            String education, Integer maxExperienceYears, String location,
                            BigDecimal minSalary, BigDecimal maxSalary, String jobType,
                            Integer openings, LocalDate applicationDeadline) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobPostingRequest that = (JobPostingRequest) o;
        return Objects.equals(companyName, that.companyName) &&
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
               Objects.equals(applicationDeadline, that.applicationDeadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, title, description, skills, education, maxExperienceYears,
                          location, minSalary, maxSalary, jobType, openings, applicationDeadline);
    }

    @Override
    public String toString() {
        return "JobPostingRequest{" +
                "companyName='" + companyName + '\'' +
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
                '}';
    }
}
