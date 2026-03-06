package com.revhire.employerservice.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class EmployerProfileResponse {

    private Long id;
    private Long userId;
    private String companyName;
    private String industry;
    private String companySize;
    private String companyDescription;
    private String website;
    private String companyLocation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EmployerProfileResponse() {
    }

    public EmployerProfileResponse(Long id, Long userId, String companyName, String industry,
                                  String companySize, String companyDescription, String website,
                                  String companyLocation, LocalDateTime createdAt, LocalDateTime updatedAt) {
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
        EmployerProfileResponse that = (EmployerProfileResponse) o;
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
        return "EmployerProfileResponse{" +
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
