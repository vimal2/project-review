package com.revature.applicationservice.dto;

import java.util.Objects;

public class ApplicationStatsResponse {

    private Long totalApplications;
    private Long appliedCount;
    private Long underReviewCount;
    private Long shortlistedCount;
    private Long rejectedCount;
    private Long withdrawnCount;

    public ApplicationStatsResponse() {
    }

    public ApplicationStatsResponse(Long totalApplications, Long appliedCount, Long underReviewCount,
                                   Long shortlistedCount, Long rejectedCount, Long withdrawnCount) {
        this.totalApplications = totalApplications;
        this.appliedCount = appliedCount;
        this.underReviewCount = underReviewCount;
        this.shortlistedCount = shortlistedCount;
        this.rejectedCount = rejectedCount;
        this.withdrawnCount = withdrawnCount;
    }

    public Long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(Long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public Long getAppliedCount() {
        return appliedCount;
    }

    public void setAppliedCount(Long appliedCount) {
        this.appliedCount = appliedCount;
    }

    public Long getUnderReviewCount() {
        return underReviewCount;
    }

    public void setUnderReviewCount(Long underReviewCount) {
        this.underReviewCount = underReviewCount;
    }

    public Long getShortlistedCount() {
        return shortlistedCount;
    }

    public void setShortlistedCount(Long shortlistedCount) {
        this.shortlistedCount = shortlistedCount;
    }

    public Long getRejectedCount() {
        return rejectedCount;
    }

    public void setRejectedCount(Long rejectedCount) {
        this.rejectedCount = rejectedCount;
    }

    public Long getWithdrawnCount() {
        return withdrawnCount;
    }

    public void setWithdrawnCount(Long withdrawnCount) {
        this.withdrawnCount = withdrawnCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationStatsResponse that = (ApplicationStatsResponse) o;
        return Objects.equals(totalApplications, that.totalApplications) &&
               Objects.equals(appliedCount, that.appliedCount) &&
               Objects.equals(underReviewCount, that.underReviewCount) &&
               Objects.equals(shortlistedCount, that.shortlistedCount) &&
               Objects.equals(rejectedCount, that.rejectedCount) &&
               Objects.equals(withdrawnCount, that.withdrawnCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalApplications, appliedCount, underReviewCount, shortlistedCount, rejectedCount, withdrawnCount);
    }

    @Override
    public String toString() {
        return "ApplicationStatsResponse{" +
                "totalApplications=" + totalApplications +
                ", appliedCount=" + appliedCount +
                ", underReviewCount=" + underReviewCount +
                ", shortlistedCount=" + shortlistedCount +
                ", rejectedCount=" + rejectedCount +
                ", withdrawnCount=" + withdrawnCount +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long totalApplications;
        private Long appliedCount;
        private Long underReviewCount;
        private Long shortlistedCount;
        private Long rejectedCount;
        private Long withdrawnCount;

        public Builder totalApplications(Long totalApplications) {
            this.totalApplications = totalApplications;
            return this;
        }

        public Builder appliedCount(Long appliedCount) {
            this.appliedCount = appliedCount;
            return this;
        }

        public Builder underReviewCount(Long underReviewCount) {
            this.underReviewCount = underReviewCount;
            return this;
        }

        public Builder shortlistedCount(Long shortlistedCount) {
            this.shortlistedCount = shortlistedCount;
            return this;
        }

        public Builder rejectedCount(Long rejectedCount) {
            this.rejectedCount = rejectedCount;
            return this;
        }

        public Builder withdrawnCount(Long withdrawnCount) {
            this.withdrawnCount = withdrawnCount;
            return this;
        }

        public ApplicationStatsResponse build() {
            return new ApplicationStatsResponse(totalApplications, appliedCount, underReviewCount,
                    shortlistedCount, rejectedCount, withdrawnCount);
        }
    }
}
