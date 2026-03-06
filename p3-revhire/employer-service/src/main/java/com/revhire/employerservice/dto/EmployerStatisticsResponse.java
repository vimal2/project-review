package com.revhire.employerservice.dto;

import java.util.Map;
import java.util.Objects;

public class EmployerStatisticsResponse {
    private Long totalJobsPosted;
    private Long activeJobs;
    private Long closedJobs;
    private Long totalApplicationsReceived;
    private Map<String, Long> applicationsByStatus;
    private Long pendingApplications;
    private Long reviewedApplications;
    private Long acceptedApplications;
    private Long rejectedApplications;

    public EmployerStatisticsResponse() {
    }

    public EmployerStatisticsResponse(Long totalJobsPosted, Long activeJobs, Long closedJobs,
                                     Long totalApplicationsReceived, Map<String, Long> applicationsByStatus,
                                     Long pendingApplications, Long reviewedApplications,
                                     Long acceptedApplications, Long rejectedApplications) {
        this.totalJobsPosted = totalJobsPosted;
        this.activeJobs = activeJobs;
        this.closedJobs = closedJobs;
        this.totalApplicationsReceived = totalApplicationsReceived;
        this.applicationsByStatus = applicationsByStatus;
        this.pendingApplications = pendingApplications;
        this.reviewedApplications = reviewedApplications;
        this.acceptedApplications = acceptedApplications;
        this.rejectedApplications = rejectedApplications;
    }

    public Long getTotalJobsPosted() {
        return totalJobsPosted;
    }

    public void setTotalJobsPosted(Long totalJobsPosted) {
        this.totalJobsPosted = totalJobsPosted;
    }

    public Long getActiveJobs() {
        return activeJobs;
    }

    public void setActiveJobs(Long activeJobs) {
        this.activeJobs = activeJobs;
    }

    public Long getClosedJobs() {
        return closedJobs;
    }

    public void setClosedJobs(Long closedJobs) {
        this.closedJobs = closedJobs;
    }

    public Long getTotalApplicationsReceived() {
        return totalApplicationsReceived;
    }

    public void setTotalApplicationsReceived(Long totalApplicationsReceived) {
        this.totalApplicationsReceived = totalApplicationsReceived;
    }

    public Map<String, Long> getApplicationsByStatus() {
        return applicationsByStatus;
    }

    public void setApplicationsByStatus(Map<String, Long> applicationsByStatus) {
        this.applicationsByStatus = applicationsByStatus;
    }

    public Long getPendingApplications() {
        return pendingApplications;
    }

    public void setPendingApplications(Long pendingApplications) {
        this.pendingApplications = pendingApplications;
    }

    public Long getReviewedApplications() {
        return reviewedApplications;
    }

    public void setReviewedApplications(Long reviewedApplications) {
        this.reviewedApplications = reviewedApplications;
    }

    public Long getAcceptedApplications() {
        return acceptedApplications;
    }

    public void setAcceptedApplications(Long acceptedApplications) {
        this.acceptedApplications = acceptedApplications;
    }

    public Long getRejectedApplications() {
        return rejectedApplications;
    }

    public void setRejectedApplications(Long rejectedApplications) {
        this.rejectedApplications = rejectedApplications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployerStatisticsResponse that = (EmployerStatisticsResponse) o;
        return Objects.equals(totalJobsPosted, that.totalJobsPosted) &&
               Objects.equals(activeJobs, that.activeJobs) &&
               Objects.equals(closedJobs, that.closedJobs) &&
               Objects.equals(totalApplicationsReceived, that.totalApplicationsReceived) &&
               Objects.equals(applicationsByStatus, that.applicationsByStatus) &&
               Objects.equals(pendingApplications, that.pendingApplications) &&
               Objects.equals(reviewedApplications, that.reviewedApplications) &&
               Objects.equals(acceptedApplications, that.acceptedApplications) &&
               Objects.equals(rejectedApplications, that.rejectedApplications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalJobsPosted, activeJobs, closedJobs, totalApplicationsReceived,
                          applicationsByStatus, pendingApplications, reviewedApplications,
                          acceptedApplications, rejectedApplications);
    }

    @Override
    public String toString() {
        return "EmployerStatisticsResponse{" +
                "totalJobsPosted=" + totalJobsPosted +
                ", activeJobs=" + activeJobs +
                ", closedJobs=" + closedJobs +
                ", totalApplicationsReceived=" + totalApplicationsReceived +
                ", applicationsByStatus=" + applicationsByStatus +
                ", pendingApplications=" + pendingApplications +
                ", reviewedApplications=" + reviewedApplications +
                ", acceptedApplications=" + acceptedApplications +
                ", rejectedApplications=" + rejectedApplications +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long totalJobsPosted;
        private Long activeJobs;
        private Long closedJobs;
        private Long totalApplicationsReceived;
        private Map<String, Long> applicationsByStatus;
        private Long pendingApplications;
        private Long reviewedApplications;
        private Long acceptedApplications;
        private Long rejectedApplications;

        public Builder totalJobsPosted(Long totalJobsPosted) {
            this.totalJobsPosted = totalJobsPosted;
            return this;
        }

        public Builder activeJobs(Long activeJobs) {
            this.activeJobs = activeJobs;
            return this;
        }

        public Builder closedJobs(Long closedJobs) {
            this.closedJobs = closedJobs;
            return this;
        }

        public Builder totalApplicationsReceived(Long totalApplicationsReceived) {
            this.totalApplicationsReceived = totalApplicationsReceived;
            return this;
        }

        public Builder applicationsByStatus(Map<String, Long> applicationsByStatus) {
            this.applicationsByStatus = applicationsByStatus;
            return this;
        }

        public Builder pendingApplications(Long pendingApplications) {
            this.pendingApplications = pendingApplications;
            return this;
        }

        public Builder reviewedApplications(Long reviewedApplications) {
            this.reviewedApplications = reviewedApplications;
            return this;
        }

        public Builder acceptedApplications(Long acceptedApplications) {
            this.acceptedApplications = acceptedApplications;
            return this;
        }

        public Builder rejectedApplications(Long rejectedApplications) {
            this.rejectedApplications = rejectedApplications;
            return this;
        }

        public EmployerStatisticsResponse build() {
            return new EmployerStatisticsResponse(totalJobsPosted, activeJobs, closedJobs,
                    totalApplicationsReceived, applicationsByStatus, pendingApplications,
                    reviewedApplications, acceptedApplications, rejectedApplications);
        }
    }
}
