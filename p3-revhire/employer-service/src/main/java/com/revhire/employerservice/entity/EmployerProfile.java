package com.revhire.employerservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "employer_profiles")
public class EmployerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User ID is required")
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 200, message = "Company name must be between 2 and 200 characters")
    @Column(name = "company_name", nullable = false, length = 200)
    private String companyName;

    @Size(max = 100, message = "Industry must not exceed 100 characters")
    @Column(name = "industry", length = 100)
    private String industry;

    @Size(max = 50, message = "Company size must not exceed 50 characters")
    @Column(name = "company_size", length = 50)
    private String companySize;

    @Size(max = 2000, message = "Company description must not exceed 2000 characters")
    @Column(name = "company_description", length = 2000)
    private String companyDescription;

    @Size(max = 255, message = "Website URL must not exceed 255 characters")
    @Column(name = "website", length = 255)
    private String website;

    @Size(max = 255, message = "Company location must not exceed 255 characters")
    @Column(name = "company_location", length = 255)
    private String companyLocation;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public EmployerProfile() {
    }

    public EmployerProfile(Long id, Long userId, String companyName, String industry, String companySize,
                          String companyDescription, String website, String companyLocation,
                          LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.companyName = companyName;
        this.industry = industry;
        this.companySize = companySize;
        this.companyDescription = companyDescription;
        this.website = website;
        this.companyLocation = companyLocation;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
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
        EmployerProfile that = (EmployerProfile) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(userId, that.userId) &&
               Objects.equals(companyName, that.companyName) &&
               Objects.equals(industry, that.industry) &&
               Objects.equals(companySize, that.companySize) &&
               Objects.equals(companyDescription, that.companyDescription) &&
               Objects.equals(website, that.website) &&
               Objects.equals(companyLocation, that.companyLocation) &&
               Objects.equals(createdAt, that.createdAt) &&
               Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, companyName, industry, companySize,
                          companyDescription, website, companyLocation, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "EmployerProfile{" +
                "id=" + id +
                ", userId=" + userId +
                ", companyName='" + companyName + '\'' +
                ", industry='" + industry + '\'' +
                ", companySize='" + companySize + '\'' +
                ", companyDescription='" + companyDescription + '\'' +
                ", website='" + website + '\'' +
                ", companyLocation='" + companyLocation + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
