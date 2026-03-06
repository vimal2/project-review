package com.revhire.employerservice.service;

import com.revhire.employerservice.client.ApplicationServiceClient;
import com.revhire.employerservice.client.NotificationClient;
import com.revhire.employerservice.dto.ApplicantResponse;
import com.revhire.employerservice.dto.ApiResponse;
import com.revhire.employerservice.dto.ApplicationResponse;
import com.revhire.employerservice.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicantService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ApplicantService.class);

    private final ApplicationServiceClient applicationServiceClient;
    private final NotificationClient notificationClient;

    public ApplicantService(ApplicationServiceClient applicationServiceClient,
                           NotificationClient notificationClient) {
        this.applicationServiceClient = applicationServiceClient;
        this.notificationClient = notificationClient;
    }

    /**
     * Get all applicants for an employer's jobs
     */
    @Transactional(readOnly = true)
    public List<ApplicantResponse> getApplicantsForEmployer(Long employerId, String status, String search) {
        log.info("Fetching applicants for employer ID: {}, status: {}, search: {}", employerId, status, search);

        try {
            ApiResponse<List<ApplicationResponse>> response =
                    applicationServiceClient.getApplicationsByEmployer(employerId);

            if (response == null || response.getData() == null) {
                return List.of();
            }

            List<ApplicationResponse> applications = response.getData();

            // Filter by status if provided
            if (status != null && !status.isBlank()) {
                applications = applications.stream()
                        .filter(app -> status.equalsIgnoreCase(app.getStatus()))
                        .collect(Collectors.toList());
            }

            // Filter by search if provided
            if (search != null && !search.isBlank()) {
                String searchLower = search.toLowerCase();
                applications = applications.stream()
                        .filter(app -> matchesSearch(app, searchLower))
                        .collect(Collectors.toList());
            }

            return applications.stream()
                    .map(this::mapToApplicantResponse)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error fetching applicants: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch applicants", e);
        }
    }

    /**
     * Get applicants for a specific job
     */
    @Transactional(readOnly = true)
    public List<ApplicantResponse> getApplicantsForJob(Long employerId, Long jobId) {
        log.info("Fetching applicants for job ID: {} and employer ID: {}", jobId, employerId);

        try {
            ApiResponse<List<ApplicationResponse>> response =
                    applicationServiceClient.getApplicationsByEmployer(employerId);

            if (response == null || response.getData() == null) {
                return List.of();
            }

            return response.getData().stream()
                    .filter(app -> app.getJobId().equals(jobId))
                    .map(this::mapToApplicantResponse)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error fetching applicants for job: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch applicants for job", e);
        }
    }

    /**
     * Update application status
     */
    @Transactional
    public ApplicantResponse updateApplicationStatus(Long employerId, Long applicationId, String newStatus) {
        log.info("Updating application ID: {} to status: {} for employer ID: {}",
                 applicationId, newStatus, employerId);

        if (newStatus == null || newStatus.isBlank()) {
            throw new BadRequestException("Status cannot be null or empty");
        }

        // Note: In a real implementation, this would call the application service
        // to update the status. For now, this is a placeholder.
        try {
            // Send notification about status change
            sendStatusChangeNotification(applicationId, newStatus);

            // Return placeholder response
            ApplicantResponse response = new ApplicantResponse();
            response.setApplicationId(applicationId);
            response.setStatus(newStatus);
            return response;

        } catch (Exception e) {
            log.error("Error updating application status: {}", e.getMessage());
            throw new RuntimeException("Failed to update application status", e);
        }
    }

    /**
     * Update application notes
     */
    @Transactional
    public ApplicantResponse updateApplicationNotes(Long employerId, Long applicationId, String notes) {
        log.info("Updating notes for application ID: {} for employer ID: {}", applicationId, employerId);

        // Note: In a real implementation, this would call the application service
        // to update the notes. For now, this is a placeholder.
        try {
            ApplicantResponse response = new ApplicantResponse();
            response.setApplicationId(applicationId);
            response.setNotes(notes);
            return response;

        } catch (Exception e) {
            log.error("Error updating application notes: {}", e.getMessage());
            throw new RuntimeException("Failed to update application notes", e);
        }
    }

    /**
     * Check if application matches search criteria
     */
    private boolean matchesSearch(ApplicationResponse app, String searchLower) {
        return (app.getApplicantName() != null && app.getApplicantName().toLowerCase().contains(searchLower)) ||
               (app.getApplicantEmail() != null && app.getApplicantEmail().toLowerCase().contains(searchLower)) ||
               (app.getJobTitle() != null && app.getJobTitle().toLowerCase().contains(searchLower));
    }

    /**
     * Map ApplicationResponse to ApplicantResponse
     */
    private ApplicantResponse mapToApplicantResponse(ApplicationResponse app) {
        ApplicantResponse response = new ApplicantResponse();
        response.setApplicationId(app.getId());
        response.setJobId(app.getJobId());
        response.setJobTitle(app.getJobTitle());
        response.setApplicantId(app.getApplicantId());
        response.setApplicantUsername(app.getApplicantEmail()); // Using email as username
        response.setApplicantFullName(app.getApplicantName());
        response.setApplicantEmail(app.getApplicantEmail());
        response.setStatus(app.getStatus());
        response.setAppliedAt(app.getAppliedAt());
        return response;
    }

    /**
     * Send notification about status change
     */
    private void sendStatusChangeNotification(Long applicationId, String newStatus) {
        try {
            // In a real implementation, we would get the applicant ID from the application
            // and send a notification. For now, this is a placeholder.
            log.info("Status change notification would be sent for application ID: {} with status: {}",
                     applicationId, newStatus);
        } catch (Exception e) {
            log.error("Failed to send status change notification: {}", e.getMessage());
        }
    }
}
