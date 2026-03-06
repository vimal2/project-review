package com.revhire.employerservice.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Service for publishing application events
 * Events can be consumed by listeners within the same service or sent to message queues
 */
@Component
public class EventPublisher {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EventPublisher.class);

    private final ApplicationEventPublisher applicationEventPublisher;

    public EventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Publish job posting event
     */
    public void publishJobPostingEvent(JobPostingEvent event) {
        log.info("Publishing job posting event: Type={}, JobId={}, Title={}",
                event.getEventType(), event.getJobId(), event.getTitle());

        try {
            applicationEventPublisher.publishEvent(event);
            log.info("Job posting event published successfully");
        } catch (Exception e) {
            log.error("Failed to publish job posting event: {}", e.getMessage(), e);
        }
    }

    /**
     * Publish job created event
     */
    public void publishJobCreated(Long jobId, Long employerId, String companyName,
                                  String title, String skills, String location) {
        JobPostingEvent event = new JobPostingEvent(
                jobId, employerId, companyName, title, skills, location,
                JobPostingEvent.EventType.JOB_CREATED
        );
        publishJobPostingEvent(event);
    }

    /**
     * Publish job updated event
     */
    public void publishJobUpdated(Long jobId, Long employerId, String companyName,
                                  String title, String skills, String location) {
        JobPostingEvent event = new JobPostingEvent(
                jobId, employerId, companyName, title, skills, location,
                JobPostingEvent.EventType.JOB_UPDATED
        );
        publishJobPostingEvent(event);
    }

    /**
     * Publish job status changed event
     */
    public void publishJobStatusChanged(Long jobId, Long employerId, String status,
                                       JobPostingEvent.EventType eventType) {
        JobPostingEvent event = new JobPostingEvent();
        event.setJobId(jobId);
        event.setEmployerId(employerId);
        event.setStatus(status);
        event.setEventType(eventType);
        event.setTimestamp(java.time.LocalDateTime.now());
        publishJobPostingEvent(event);
    }
}
