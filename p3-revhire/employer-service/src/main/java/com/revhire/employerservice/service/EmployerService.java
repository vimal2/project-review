package com.revhire.employerservice.service;

import com.revhire.employerservice.client.ApplicationServiceClient;
import com.revhire.employerservice.client.AuthServiceClient;
import com.revhire.employerservice.client.JobServiceClient;
import com.revhire.employerservice.dto.*;
import com.revhire.employerservice.entity.EmployerProfile;
import com.revhire.employerservice.exception.BadRequestException;
import com.revhire.employerservice.exception.NotFoundException;
import com.revhire.employerservice.repository.EmployerProfileRepository;
import com.revhire.employerservice.util.InputSanitizer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmployerService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EmployerService.class);

    private final EmployerProfileRepository employerProfileRepository;
    private final AuthServiceClient authServiceClient;
    private final JobServiceClient jobServiceClient;
    private final ApplicationServiceClient applicationServiceClient;

    public EmployerService(EmployerProfileRepository employerProfileRepository,
                          AuthServiceClient authServiceClient,
                          JobServiceClient jobServiceClient,
                          ApplicationServiceClient applicationServiceClient) {
        this.employerProfileRepository = employerProfileRepository;
        this.authServiceClient = authServiceClient;
        this.jobServiceClient = jobServiceClient;
        this.applicationServiceClient = applicationServiceClient;
    }

    /**
     * Get company profile for an employer
     * @param userId the user ID of the employer
     * @return EmployerCompanyProfileResponse
     */
    @Transactional(readOnly = true)
    public EmployerCompanyProfileResponse getCompanyProfile(Long userId) {
        log.info("Fetching company profile for user ID: {}", userId);

        if (userId == null || userId <= 0) {
            throw new BadRequestException("Invalid user ID");
        }

        EmployerProfile profile = employerProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Employer profile not found for user ID: " + userId));

        return mapToResponse(profile);
    }

    /**
     * Update company profile for an employer
     * @param userId the user ID of the employer
     * @param request the update request
     * @return updated EmployerCompanyProfileResponse
     */
    @Transactional
    public EmployerCompanyProfileResponse updateCompanyProfile(Long userId, EmployerCompanyProfileRequest request) {
        log.info("Updating company profile for user ID: {}", userId);

        if (userId == null || userId <= 0) {
            throw new BadRequestException("Invalid user ID");
        }

        if (request == null) {
            throw new BadRequestException("Request body cannot be null");
        }

        // Sanitize input
        sanitizeRequest(request);

        // Find existing profile or create new one
        EmployerProfile profile = employerProfileRepository.findByUserId(userId)
                .orElse(new EmployerProfile());

        // Set user ID for new profiles
        if (profile.getId() == null) {
            profile.setUserId(userId);
        }

        // Update profile fields
        profile.setCompanyName(request.getCompanyName());
        profile.setIndustry(request.getIndustry());
        profile.setCompanySize(request.getCompanySize());
        profile.setCompanyDescription(request.getCompanyDescription());
        profile.setWebsite(request.getWebsite());
        profile.setCompanyLocation(request.getCompanyLocation());

        EmployerProfile savedProfile = employerProfileRepository.save(profile);
        log.info("Company profile updated successfully for user ID: {}", userId);

        return mapToResponse(savedProfile);
    }

    /**
     * Get statistics for an employer (jobs and applications)
     * @param userId the user ID of the employer
     * @return EmployerStatisticsResponse
     */
    @Transactional(readOnly = true)
    public EmployerStatisticsResponse getStatistics(Long userId) {
        log.info("Fetching statistics for employer user ID: {}", userId);

        if (userId == null || userId <= 0) {
            throw new BadRequestException("Invalid user ID");
        }

        // Verify employer profile exists
        if (!employerProfileRepository.existsByUserId(userId)) {
            throw new NotFoundException("Employer profile not found for user ID: " + userId);
        }

        // Fetch job statistics
        JobServiceClient.JobStatistics jobStats = null;
        try {
            ApiResponse<JobServiceClient.JobStatistics> jobStatsResponse = jobServiceClient.getJobStatistics(userId);
            jobStats = jobStatsResponse.getData();
        } catch (Exception e) {
            log.error("Error fetching job statistics: {}", e.getMessage());
            jobStats = new JobServiceClient.JobStatistics();
            jobStats.totalJobs = 0L;
            jobStats.activeJobs = 0L;
            jobStats.closedJobs = 0L;
        }

        // Fetch application statistics
        ApplicationServiceClient.ApplicationStatistics appStats = null;
        try {
            ApiResponse<ApplicationServiceClient.ApplicationStatistics> appStatsResponse =
                applicationServiceClient.getApplicationStatistics(userId);
            appStats = appStatsResponse.getData();
        } catch (Exception e) {
            log.error("Error fetching application statistics: {}", e.getMessage());
            appStats = new ApplicationServiceClient.ApplicationStatistics();
            appStats.totalApplications = 0L;
            appStats.applicationsByStatus = new HashMap<>();
        }

        // Build combined statistics response
        Map<String, Long> applicationsByStatus = appStats.applicationsByStatus != null ?
            appStats.applicationsByStatus : new HashMap<>();

        return EmployerStatisticsResponse.builder()
                .totalJobsPosted(jobStats.totalJobs != null ? jobStats.totalJobs : 0L)
                .activeJobs(jobStats.activeJobs != null ? jobStats.activeJobs : 0L)
                .closedJobs(jobStats.closedJobs != null ? jobStats.closedJobs : 0L)
                .totalApplicationsReceived(appStats.totalApplications != null ? appStats.totalApplications : 0L)
                .applicationsByStatus(applicationsByStatus)
                .pendingApplications(applicationsByStatus.getOrDefault("PENDING", 0L))
                .reviewedApplications(applicationsByStatus.getOrDefault("REVIEWED", 0L))
                .acceptedApplications(applicationsByStatus.getOrDefault("ACCEPTED", 0L))
                .rejectedApplications(applicationsByStatus.getOrDefault("REJECTED", 0L))
                .build();
    }

    /**
     * Sanitize input request
     */
    private void sanitizeRequest(EmployerCompanyProfileRequest request) {
        if (request.getCompanyName() != null) {
            request.setCompanyName(InputSanitizer.sanitize(request.getCompanyName()));
        }
        if (request.getIndustry() != null) {
            request.setIndustry(InputSanitizer.sanitize(request.getIndustry()));
        }
        if (request.getCompanySize() != null) {
            request.setCompanySize(InputSanitizer.sanitize(request.getCompanySize()));
        }
        if (request.getCompanyDescription() != null) {
            request.setCompanyDescription(InputSanitizer.sanitize(request.getCompanyDescription()));
        }
        if (request.getWebsite() != null) {
            request.setWebsite(InputSanitizer.sanitize(request.getWebsite()));
        }
        if (request.getCompanyLocation() != null) {
            request.setCompanyLocation(InputSanitizer.sanitize(request.getCompanyLocation()));
        }
    }

    /**
     * Map entity to response DTO
     */
    private EmployerCompanyProfileResponse mapToResponse(EmployerProfile profile) {
        return EmployerCompanyProfileResponse.builder()
                .id(profile.getId())
                .userId(profile.getUserId())
                .companyName(profile.getCompanyName())
                .industry(profile.getIndustry())
                .companySize(profile.getCompanySize())
                .companyDescription(profile.getCompanyDescription())
                .website(profile.getWebsite())
                .companyLocation(profile.getCompanyLocation())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }
}
