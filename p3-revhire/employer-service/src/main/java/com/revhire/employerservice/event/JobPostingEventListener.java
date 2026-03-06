package com.revhire.employerservice.event;

import com.revhire.employerservice.client.NotificationClient;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Listener for job posting events
 * Handles event processing asynchronously and triggers notifications
 */
@Component
public class JobPostingEventListener {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JobPostingEventListener.class);

    private final NotificationClient notificationClient;

    public JobPostingEventListener(NotificationClient notificationClient) {
        this.notificationClient = notificationClient;
    }

    /**
     * Handle job posting events asynchronously
     */
    @Async
    @EventListener
    public void handleJobPostingEvent(JobPostingEvent event) {
        log.info("Received job posting event: Type={}, JobId={}",
                event.getEventType(), event.getJobId());

        try {
            switch (event.getEventType()) {
                case JOB_CREATED:
                    handleJobCreated(event);
                    break;
                case JOB_UPDATED:
                    handleJobUpdated(event);
                    break;
                case JOB_CLOSED:
                    handleJobStatusChanged(event, "closed");
                    break;
                case JOB_REOPENED:
                    handleJobStatusChanged(event, "reopened");
                    break;
                case JOB_FILLED:
                    handleJobStatusChanged(event, "filled");
                    break;
                case JOB_DELETED:
                    handleJobDeleted(event);
                    break;
                default:
                    log.warn("Unknown event type: {}", event.getEventType());
            }
        } catch (Exception e) {
            log.error("Error handling job posting event: {}", e.getMessage(), e);
        }
    }

    /**
     * Handle job created event
     */
    private void handleJobCreated(JobPostingEvent event) {
        log.info("Processing job created event for job: {}", event.getTitle());

        // Send notification to employer
        try {
            NotificationClient.NotificationRequest notification =
                    new NotificationClient.NotificationRequest(
                            event.getEmployerId(),
                            "Your job posting '" + event.getTitle() + "' has been created successfully",
                            "JOB_POSTED",
                            event.getJobId(),
                            "JOB_POSTING"
                    );
            notificationClient.sendNotification(notification);
            log.info("Job creation notification sent");
        } catch (Exception e) {
            log.error("Failed to send job creation notification: {}", e.getMessage());
        }

        // Additional processing: match with job seekers, send recommendations, etc.
        // This would typically call notification service with matching job seekers
    }

    /**
     * Handle job updated event
     */
    private void handleJobUpdated(JobPostingEvent event) {
        log.info("Processing job updated event for job: {}", event.getTitle());

        // Notify employer about update
        try {
            NotificationClient.NotificationRequest notification =
                    new NotificationClient.NotificationRequest(
                            event.getEmployerId(),
                            "Your job posting '" + event.getTitle() + "' has been updated",
                            "JOB_UPDATED",
                            event.getJobId(),
                            "JOB_POSTING"
                    );
            notificationClient.sendNotification(notification);
        } catch (Exception e) {
            log.error("Failed to send job update notification: {}", e.getMessage());
        }
    }

    /**
     * Handle job status changed event
     */
    private void handleJobStatusChanged(JobPostingEvent event, String statusText) {
        log.info("Processing job status change to '{}' for job ID: {}", statusText, event.getJobId());

        try {
            NotificationClient.NotificationRequest notification =
                    new NotificationClient.NotificationRequest(
                            event.getEmployerId(),
                            "Your job posting has been " + statusText,
                            "JOB_STATUS_CHANGED",
                            event.getJobId(),
                            "JOB_POSTING"
                    );
            notificationClient.sendNotification(notification);
        } catch (Exception e) {
            log.error("Failed to send job status change notification: {}", e.getMessage());
        }
    }

    /**
     * Handle job deleted event
     */
    private void handleJobDeleted(JobPostingEvent event) {
        log.info("Processing job deleted event for job ID: {}", event.getJobId());

        try {
            NotificationClient.NotificationRequest notification =
                    new NotificationClient.NotificationRequest(
                            event.getEmployerId(),
                            "Your job posting has been deleted",
                            "JOB_DELETED",
                            event.getJobId(),
                            "JOB_POSTING"
                    );
            notificationClient.sendNotification(notification);
        } catch (Exception e) {
            log.error("Failed to send job deletion notification: {}", e.getMessage());
        }
    }
}
