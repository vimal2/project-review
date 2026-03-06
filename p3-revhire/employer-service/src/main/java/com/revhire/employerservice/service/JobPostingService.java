package com.revhire.employerservice.service;

import com.revhire.employerservice.client.NotificationClient;
import com.revhire.employerservice.dto.JobPostingRequest;
import com.revhire.employerservice.dto.JobPostingResponse;
import com.revhire.employerservice.entity.EmployerProfile;
import com.revhire.employerservice.entity.JobPosting;
import com.revhire.employerservice.entity.JobStatus;
import com.revhire.employerservice.exception.BadRequestException;
import com.revhire.employerservice.exception.ForbiddenException;
import com.revhire.employerservice.exception.NotFoundException;
import com.revhire.employerservice.repository.EmployerProfileRepository;
import com.revhire.employerservice.repository.JobPostingRepository;
import com.revhire.employerservice.util.InputSanitizer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobPostingService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JobPostingService.class);

    private final JobPostingRepository jobPostingRepository;
    private final EmployerProfileRepository employerProfileRepository;
    private final NotificationClient notificationClient;

    public JobPostingService(JobPostingRepository jobPostingRepository,
                            EmployerProfileRepository employerProfileRepository,
                            NotificationClient notificationClient) {
        this.jobPostingRepository = jobPostingRepository;
        this.employerProfileRepository = employerProfileRepository;
        this.notificationClient = notificationClient;
    }

    /**
     * Create a new job posting
     */
    @Transactional
    public JobPostingResponse createJob(Long userId, JobPostingRequest request) {
        log.info("Creating job posting for employer user ID: {}", userId);

        validateJobRequest(request);

        // Get employer profile for company name
        String companyName = getCompanyName(userId, request.getCompanyName());

        JobPosting job = new JobPosting();
        job.setEmployerId(userId);
        job.setCompanyName(companyName);
        mapRequestToEntity(request, job);
        job.setStatus(JobStatus.OPEN);

        JobPosting savedJob = jobPostingRepository.save(job);
        log.info("Job posting created with ID: {}", savedJob.getId());

        // Publish job posting event for notifications
        publishJobPostingEvent(userId, savedJob);

        return mapToResponse(savedJob);
    }

    /**
     * Update an existing job posting
     */
    @Transactional
    public JobPostingResponse updateJob(Long userId, Long jobId, JobPostingRequest request) {
        log.info("Updating job posting ID: {} for employer user ID: {}", jobId, userId);

        validateJobRequest(request);

        JobPosting job = getJobByIdAndEmployerId(jobId, userId);

        // Update fields
        String companyName = getCompanyName(userId, request.getCompanyName());
        job.setCompanyName(companyName);
        mapRequestToEntity(request, job);

        JobPosting updatedJob = jobPostingRepository.save(job);
        log.info("Job posting updated with ID: {}", updatedJob.getId());

        return mapToResponse(updatedJob);
    }

    /**
     * Delete a job posting
     */
    @Transactional
    public void deleteJob(Long userId, Long jobId) {
        log.info("Deleting job posting ID: {} for employer user ID: {}", jobId, userId);

        JobPosting job = getJobByIdAndEmployerId(jobId, userId);
        jobPostingRepository.delete(job);

        log.info("Job posting deleted with ID: {}", jobId);
    }

    /**
     * Close a job posting
     */
    @Transactional
    public JobPostingResponse closeJob(Long userId, Long jobId) {
        return updateJobStatus(userId, jobId, JobStatus.CLOSED);
    }

    /**
     * Reopen a job posting
     */
    @Transactional
    public JobPostingResponse reopenJob(Long userId, Long jobId) {
        return updateJobStatus(userId, jobId, JobStatus.OPEN);
    }

    /**
     * Mark a job posting as filled
     */
    @Transactional
    public JobPostingResponse fillJob(Long userId, Long jobId) {
        return updateJobStatus(userId, jobId, JobStatus.FILLED);
    }

    /**
     * Get all jobs for an employer
     */
    @Transactional(readOnly = true)
    public List<JobPostingResponse> getJobsByEmployer(Long userId) {
        log.info("Fetching jobs for employer user ID: {}", userId);

        List<JobPosting> jobs = jobPostingRepository.findByEmployerIdOrderByCreatedAtDesc(userId);
        return jobs.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get a specific job by ID for an employer
     */
    @Transactional(readOnly = true)
    public JobPostingResponse getJobById(Long userId, Long jobId) {
        log.info("Fetching job ID: {} for employer user ID: {}", jobId, userId);

        JobPosting job = getJobByIdAndEmployerId(jobId, userId);
        return mapToResponse(job);
    }

    /**
     * Get all open jobs (for public job listings)
     */
    @Transactional(readOnly = true)
    public List<JobPostingResponse> getAllOpenJobs() {
        log.info("Fetching all open jobs");

        List<JobPosting> jobs = jobPostingRepository.findByStatusOrderByCreatedAtDesc(JobStatus.OPEN);
        return jobs.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Update job status
     */
    private JobPostingResponse updateJobStatus(Long userId, Long jobId, JobStatus status) {
        log.info("Updating job ID: {} status to {} for employer user ID: {}", jobId, status, userId);

        JobPosting job = getJobByIdAndEmployerId(jobId, userId);
        job.setStatus(status);

        JobPosting updatedJob = jobPostingRepository.save(job);
        log.info("Job posting status updated to {} for job ID: {}", status, jobId);

        return mapToResponse(updatedJob);
    }

    /**
     * Get job by ID and verify ownership
     */
    private JobPosting getJobByIdAndEmployerId(Long jobId, Long employerId) {
        JobPosting job = jobPostingRepository.findById(jobId)
                .orElseThrow(() -> new NotFoundException("Job posting not found with ID: " + jobId));

        if (!job.getEmployerId().equals(employerId)) {
            throw new ForbiddenException("You can only access your own job postings");
        }

        return job;
    }

    /**
     * Get company name from profile or request
     */
    private String getCompanyName(Long userId, String requestedCompanyName) {
        if (requestedCompanyName != null && !requestedCompanyName.isBlank()) {
            return InputSanitizer.sanitize(requestedCompanyName);
        }

        return employerProfileRepository.findByUserId(userId)
                .map(EmployerProfile::getCompanyName)
                .filter(name -> name != null && !name.isBlank())
                .orElse("Company");
    }

    /**
     * Validate job request
     */
    private void validateJobRequest(JobPostingRequest request) {
        if (request == null) {
            throw new BadRequestException("Job posting request cannot be null");
        }

        if (request.getMinSalary() != null && request.getMaxSalary() != null &&
            request.getMinSalary().compareTo(request.getMaxSalary()) > 0) {
            throw new BadRequestException("Minimum salary cannot exceed maximum salary");
        }

        if (request.getApplicationDeadline() != null &&
            !request.getApplicationDeadline().isAfter(LocalDate.now())) {
            throw new BadRequestException("Application deadline must be a future date");
        }
    }

    /**
     * Map request to entity
     */
    private void mapRequestToEntity(JobPostingRequest request, JobPosting job) {
        job.setTitle(InputSanitizer.sanitize(request.getTitle()));
        job.setDescription(InputSanitizer.sanitize(request.getDescription()));
        job.setSkills(InputSanitizer.sanitize(request.getSkills()));
        job.setEducation(InputSanitizer.sanitize(request.getEducation()));
        job.setMaxExperienceYears(request.getMaxExperienceYears());
        job.setLocation(InputSanitizer.sanitize(request.getLocation()));
        job.setMinSalary(request.getMinSalary());
        job.setMaxSalary(request.getMaxSalary());
        job.setJobType(InputSanitizer.sanitize(request.getJobType()));
        job.setOpenings(request.getOpenings());
        job.setApplicationDeadline(request.getApplicationDeadline());
    }

    /**
     * Map entity to response
     */
    private JobPostingResponse mapToResponse(JobPosting job) {
        JobPostingResponse response = new JobPostingResponse();
        response.setId(job.getId());
        response.setEmployerId(job.getEmployerId());
        response.setCompanyName(job.getCompanyName());
        response.setTitle(job.getTitle());
        response.setDescription(job.getDescription());
        response.setSkills(job.getSkills());
        response.setEducation(job.getEducation());
        response.setMaxExperienceYears(job.getMaxExperienceYears());
        response.setLocation(job.getLocation());
        response.setMinSalary(job.getMinSalary());
        response.setMaxSalary(job.getMaxSalary());
        response.setJobType(job.getJobType());
        response.setOpenings(job.getOpenings());
        response.setApplicationDeadline(job.getApplicationDeadline());
        response.setStatus(job.getStatus());
        response.setCreatedAt(job.getCreatedAt());
        response.setUpdatedAt(job.getUpdatedAt());
        return response;
    }

    /**
     * Publish job posting event for notifications
     */
    private void publishJobPostingEvent(Long userId, JobPosting job) {
        try {
            NotificationClient.NotificationRequest notification = new NotificationClient.NotificationRequest(
                    userId,
                    "New job posted: " + job.getTitle(),
                    "JOB_POSTED",
                    job.getId(),
                    "JOB_POSTING"
            );
            notificationClient.sendNotification(notification);
            log.info("Job posting event published for job ID: {}", job.getId());
        } catch (Exception e) {
            log.error("Failed to publish job posting event: {}", e.getMessage());
        }
    }
}
