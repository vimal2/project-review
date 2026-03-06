package com.revature.applicationservice.service;

import com.revature.applicationservice.client.AuthServiceClient;
import com.revature.applicationservice.client.JobSeekerServiceClient;
import com.revature.applicationservice.client.JobServiceClient;
import com.revature.applicationservice.client.NotificationServiceClient;
import com.revature.applicationservice.dto.*;
import com.revature.applicationservice.entity.JobApplication;
import com.revature.applicationservice.enums.ApplicationStatus;
import com.revature.applicationservice.exception.ConflictException;
import com.revature.applicationservice.exception.ForbiddenException;
import com.revature.applicationservice.exception.NotFoundException;
import com.revature.applicationservice.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ApplicationService.class);

    private final JobApplicationRepository applicationRepository;
    private final JobServiceClient jobServiceClient;
    private final JobSeekerServiceClient jobSeekerServiceClient;
    private final AuthServiceClient authServiceClient;
    private final NotificationServiceClient notificationServiceClient;

    public ApplicationService(JobApplicationRepository applicationRepository, JobServiceClient jobServiceClient,
                             JobSeekerServiceClient jobSeekerServiceClient, AuthServiceClient authServiceClient,
                             NotificationServiceClient notificationServiceClient) {
        this.applicationRepository = applicationRepository;
        this.jobServiceClient = jobServiceClient;
        this.jobSeekerServiceClient = jobSeekerServiceClient;
        this.authServiceClient = authServiceClient;
        this.notificationServiceClient = notificationServiceClient;
    }

    @Transactional
    public JobSeekerApplicationResponse applyToJob(Long jobSeekerId, Long jobId, ApplyRequest request) {
        log.info("Job seeker {} applying to job {}", jobSeekerId, jobId);

        // Check if already applied
        if (applicationRepository.existsByJobIdAndJobSeekerId(jobId, jobSeekerId)) {
            throw new ConflictException("You have already applied to this job");
        }

        // Fetch job details
        Map<String, Object> jobDetails = jobServiceClient.getJobDetails(jobId);
        Long employerId = getLongFromMap(jobDetails, "employerId");

        // Create application
        JobApplication application = new JobApplication();
        application.setJobId(jobId);
        application.setJobSeekerId(jobSeekerId);
        application.setEmployerId(employerId);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setCoverLetter(request.getCoverLetter());

        JobApplication savedApplication = applicationRepository.save(application);

        // Send notification to employer
        try {
            Map<String, Object> notification = new HashMap<>();
            notification.put("recipientId", employerId);
            notification.put("type", "NEW_APPLICATION");
            notification.put("message", "New application received for job: " + jobDetails.get("title"));
            notification.put("jobId", jobId);
            notification.put("applicationId", savedApplication.getId());
            notificationServiceClient.sendNotification(notification);
        } catch (Exception e) {
            log.error("Failed to send notification", e);
        }

        return mapToJobSeekerResponse(savedApplication, jobDetails);
    }

    @Transactional
    public JobSeekerApplicationResponse withdrawApplication(Long jobSeekerId, Long jobId, WithdrawRequest request) {
        log.info("Job seeker {} withdrawing application for job {}", jobSeekerId, jobId);

        JobApplication application = applicationRepository.findByJobIdAndJobSeekerId(jobId, jobSeekerId)
                .orElseThrow(() -> new NotFoundException("Application not found"));

        if (application.getStatus() == ApplicationStatus.WITHDRAWN) {
            throw new ConflictException("Application already withdrawn");
        }

        application.setStatus(ApplicationStatus.WITHDRAWN);
        application.setWithdrawReason(request.getWithdrawReason());
        JobApplication updatedApplication = applicationRepository.save(application);

        // Fetch job details for response
        Map<String, Object> jobDetails = jobServiceClient.getJobDetails(jobId);

        return mapToJobSeekerResponse(updatedApplication, jobDetails);
    }

    @Transactional
    public JobSeekerApplicationResponse withdrawApplicationById(Long jobSeekerId, Long applicationId, WithdrawRequest request) {
        log.info("Job seeker {} withdrawing application {}", jobSeekerId, applicationId);

        JobApplication application = applicationRepository.findByIdAndJobSeekerId(applicationId, jobSeekerId)
                .orElseThrow(() -> new NotFoundException("Application not found"));

        if (application.getStatus() == ApplicationStatus.WITHDRAWN) {
            throw new ConflictException("Application already withdrawn");
        }

        application.setStatus(ApplicationStatus.WITHDRAWN);
        application.setWithdrawReason(request.getWithdrawReason());
        JobApplication updatedApplication = applicationRepository.save(application);

        // Fetch job details for response
        Map<String, Object> jobDetails = jobServiceClient.getJobDetails(application.getJobId());

        return mapToJobSeekerResponse(updatedApplication, jobDetails);
    }

    public List<JobSeekerApplicationResponse> getJobSeekerApplications(Long jobSeekerId) {
        log.info("Fetching applications for job seeker {}", jobSeekerId);

        List<JobApplication> applications = applicationRepository.findByJobSeekerIdOrderByAppliedAtDesc(jobSeekerId);

        return applications.stream()
                .map(app -> {
                    try {
                        Map<String, Object> jobDetails = jobServiceClient.getJobDetails(app.getJobId());
                        return mapToJobSeekerResponse(app, jobDetails);
                    } catch (Exception e) {
                        log.error("Error fetching job details for job {}", app.getJobId(), e);
                        return mapToJobSeekerResponse(app, new HashMap<>());
                    }
                })
                .collect(Collectors.toList());
    }

    public List<Long> getAppliedJobIds(Long jobSeekerId) {
        log.info("Fetching applied job IDs for job seeker {}", jobSeekerId);
        return applicationRepository.findAllJobIdsByJobSeekerId(jobSeekerId);
    }

    public List<EmployerApplicantResponse> getEmployerApplicants(Long employerId, ApplicationStatus status, String search) {
        log.info("Fetching applicants for employer {}", employerId);

        List<JobApplication> applications = applicationRepository.findByEmployerId(employerId);

        // Filter by status if provided
        if (status != null) {
            applications = applications.stream()
                    .filter(app -> app.getStatus() == status)
                    .collect(Collectors.toList());
        }

        return applications.stream()
                .map(app -> {
                    try {
                        Map<String, Object> jobDetails = jobServiceClient.getJobDetails(app.getJobId());
                        Map<String, Object> jobSeekerProfile = jobSeekerServiceClient.getJobSeekerProfile(app.getJobSeekerId());
                        Map<String, Object> userDetails = authServiceClient.getUserDetails(app.getJobSeekerId());

                        EmployerApplicantResponse response = mapToEmployerResponse(app, jobDetails, jobSeekerProfile, userDetails);

                        // Apply search filter
                        if (search != null && !search.isEmpty()) {
                            String searchLower = search.toLowerCase();
                            String name = response.getJobSeekerName() != null ? response.getJobSeekerName().toLowerCase() : "";
                            String email = response.getJobSeekerEmail() != null ? response.getJobSeekerEmail().toLowerCase() : "";
                            String jobTitle = response.getJobTitle() != null ? response.getJobTitle().toLowerCase() : "";

                            if (!name.contains(searchLower) && !email.contains(searchLower) && !jobTitle.contains(searchLower)) {
                                return null;
                            }
                        }

                        return response;
                    } catch (Exception e) {
                        log.error("Error fetching details for application {}", app.getId(), e);
                        return null;
                    }
                })
                .filter(response -> response != null)
                .collect(Collectors.toList());
    }

    public List<EmployerApplicantResponse> getApplicantsForJob(Long employerId, Long jobId) {
        log.info("Fetching applicants for employer {} and job {}", employerId, jobId);

        List<JobApplication> applications = applicationRepository.findByEmployerIdAndJobId(employerId, jobId);

        return applications.stream()
                .map(app -> {
                    try {
                        Map<String, Object> jobDetails = jobServiceClient.getJobDetails(app.getJobId());
                        Map<String, Object> jobSeekerProfile = jobSeekerServiceClient.getJobSeekerProfile(app.getJobSeekerId());
                        Map<String, Object> userDetails = authServiceClient.getUserDetails(app.getJobSeekerId());

                        return mapToEmployerResponse(app, jobDetails, jobSeekerProfile, userDetails);
                    } catch (Exception e) {
                        log.error("Error fetching details for application {}", app.getId(), e);
                        return null;
                    }
                })
                .filter(response -> response != null)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmployerApplicantResponse updateApplicationStatus(Long employerId, Long applicationId, ApplicationStatus status) {
        log.info("Employer {} updating application {} status to {}", employerId, applicationId, status);

        JobApplication application = applicationRepository.findByIdAndEmployerId(applicationId, employerId)
                .orElseThrow(() -> new NotFoundException("Application not found"));

        if (application.getStatus() == ApplicationStatus.WITHDRAWN) {
            throw new ForbiddenException("Cannot update status of withdrawn application");
        }

        application.setStatus(status);
        JobApplication updatedApplication = applicationRepository.save(application);

        // Send notification to job seeker
        try {
            Map<String, Object> notification = new HashMap<>();
            notification.put("recipientId", application.getJobSeekerId());
            notification.put("type", "APPLICATION_STATUS_UPDATE");
            notification.put("message", "Your application status has been updated to: " + status);
            notification.put("applicationId", applicationId);
            notificationServiceClient.sendNotification(notification);
        } catch (Exception e) {
            log.error("Failed to send notification", e);
        }

        // Fetch details for response
        Map<String, Object> jobDetails = jobServiceClient.getJobDetails(application.getJobId());
        Map<String, Object> jobSeekerProfile = jobSeekerServiceClient.getJobSeekerProfile(application.getJobSeekerId());
        Map<String, Object> userDetails = authServiceClient.getUserDetails(application.getJobSeekerId());

        return mapToEmployerResponse(updatedApplication, jobDetails, jobSeekerProfile, userDetails);
    }

    @Transactional
    public EmployerApplicantResponse updateApplicationNotes(Long employerId, Long applicationId, String notes) {
        log.info("Employer {} updating notes for application {}", employerId, applicationId);

        JobApplication application = applicationRepository.findByIdAndEmployerId(applicationId, employerId)
                .orElseThrow(() -> new NotFoundException("Application not found"));

        application.setNotes(notes);
        JobApplication updatedApplication = applicationRepository.save(application);

        // Fetch details for response
        Map<String, Object> jobDetails = jobServiceClient.getJobDetails(application.getJobId());
        Map<String, Object> jobSeekerProfile = jobSeekerServiceClient.getJobSeekerProfile(application.getJobSeekerId());
        Map<String, Object> userDetails = authServiceClient.getUserDetails(application.getJobSeekerId());

        return mapToEmployerResponse(updatedApplication, jobDetails, jobSeekerProfile, userDetails);
    }

    public Map<String, Object> getApplicantResume(Long employerId, Long applicationId) {
        log.info("Employer {} fetching resume for application {}", employerId, applicationId);

        JobApplication application = applicationRepository.findByIdAndEmployerId(applicationId, employerId)
                .orElseThrow(() -> new NotFoundException("Application not found"));

        return jobSeekerServiceClient.getJobSeekerResume(application.getJobSeekerId());
    }

    public ApplicationStatsResponse getApplicationStats(Long employerId) {
        log.info("Fetching application stats for employer {}", employerId);

        Long totalApplications = applicationRepository.countByEmployerId(employerId);
        Long appliedCount = applicationRepository.countByEmployerIdAndStatus(employerId, ApplicationStatus.APPLIED);
        Long underReviewCount = applicationRepository.countByEmployerIdAndStatus(employerId, ApplicationStatus.UNDER_REVIEW);
        Long shortlistedCount = applicationRepository.countByEmployerIdAndStatus(employerId, ApplicationStatus.SHORTLISTED);
        Long rejectedCount = applicationRepository.countByEmployerIdAndStatus(employerId, ApplicationStatus.REJECTED);
        Long withdrawnCount = applicationRepository.countByEmployerIdAndStatus(employerId, ApplicationStatus.WITHDRAWN);

        return ApplicationStatsResponse.builder()
                .totalApplications(totalApplications)
                .appliedCount(appliedCount)
                .underReviewCount(underReviewCount)
                .shortlistedCount(shortlistedCount)
                .rejectedCount(rejectedCount)
                .withdrawnCount(withdrawnCount)
                .build();
    }

    @Transactional
    public void deleteApplicationsByJobId(Long jobId) {
        log.info("Deleting all applications for job {}", jobId);
        applicationRepository.deleteByJobId(jobId);
    }

    private JobSeekerApplicationResponse mapToJobSeekerResponse(JobApplication application, Map<String, Object> jobDetails) {
        return JobSeekerApplicationResponse.builder()
                .id(application.getId())
                .jobId(application.getJobId())
                .jobTitle(getStringFromMap(jobDetails, "title"))
                .companyName(getStringFromMap(jobDetails, "companyName"))
                .location(getStringFromMap(jobDetails, "location"))
                .status(application.getStatus())
                .coverLetter(application.getCoverLetter())
                .withdrawReason(application.getWithdrawReason())
                .appliedAt(application.getAppliedAt())
                .updatedAt(application.getUpdatedAt())
                .build();
    }

    private EmployerApplicantResponse mapToEmployerResponse(JobApplication application,
                                                             Map<String, Object> jobDetails,
                                                             Map<String, Object> jobSeekerProfile,
                                                             Map<String, Object> userDetails) {
        return EmployerApplicantResponse.builder()
                .id(application.getId())
                .jobId(application.getJobId())
                .jobTitle(getStringFromMap(jobDetails, "title"))
                .jobSeekerId(application.getJobSeekerId())
                .jobSeekerName(getStringFromMap(jobSeekerProfile, "fullName"))
                .jobSeekerEmail(getStringFromMap(userDetails, "email"))
                .jobSeekerPhone(getStringFromMap(jobSeekerProfile, "phone"))
                .status(application.getStatus())
                .notes(application.getNotes())
                .coverLetter(application.getCoverLetter())
                .withdrawReason(application.getWithdrawReason())
                .appliedAt(application.getAppliedAt())
                .updatedAt(application.getUpdatedAt())
                .build();
    }

    private String getStringFromMap(Map<String, Object> map, String key) {
        if (map == null || !map.containsKey(key)) {
            return null;
        }
        Object value = map.get(key);
        return value != null ? value.toString() : null;
    }

    private Long getLongFromMap(Map<String, Object> map, String key) {
        if (map == null || !map.containsKey(key)) {
            throw new NotFoundException("Required field '" + key + "' not found");
        }
        Object value = map.get(key);
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        if (value instanceof String) {
            return Long.parseLong((String) value);
        }
        throw new IllegalArgumentException("Cannot convert value to Long");
    }
}
