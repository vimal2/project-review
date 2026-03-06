package com.revhire.employerservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class EmployerCompanyProfileRequest {

    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 200, message = "Company name must be between 2 and 200 characters")
    private String companyName;

    @Size(max = 100, message = "Industry must not exceed 100 characters")
    private String industry;

    @Size(max = 50, message = "Company size must not exceed 50 characters")
    private String companySize;

    @Size(max = 2000, message = "Company description must not exceed 2000 characters")
    private String companyDescription;

    @Size(max = 255, message = "Website URL must not exceed 255 characters")
    private String website;

    @Size(max = 255, message = "Company location must not exceed 255 characters")
    private String companyLocation;

    public EmployerCompanyProfileRequest() {
    }

    public EmployerCompanyProfileRequest(String companyName, String industry, String companySize,
                                        String companyDescription, String website, String companyLocation) {
        this.companyName = companyName;
        this.industry = industry;
        this.companySize = companySize;
        this.companyDescription = companyDescription;
        this.website = website;
        this.companyLocation = companyLocation;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployerCompanyProfileRequest that = (EmployerCompanyProfileRequest) o;
        return Objects.equals(companyName, that.companyName) &&
               Objects.equals(industry, that.industry) &&
               Objects.equals(companySize, that.companySize) &&
               Objects.equals(companyDescription, that.companyDescription) &&
               Objects.equals(website, that.website) &&
               Objects.equals(companyLocation, that.companyLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, industry, companySize, companyDescription, website, companyLocation);
    }

    @Override
    public String toString() {
        return "EmployerCompanyProfileRequest{" +
                "companyName='" + companyName + '\'' +
                ", industry='" + industry + '\'' +
                ", companySize='" + companySize + '\'' +
                ", companyDescription='" + companyDescription + '\'' +
                ", website='" + website + '\'' +
                ", companyLocation='" + companyLocation + '\'' +
                '}';
    }
}
