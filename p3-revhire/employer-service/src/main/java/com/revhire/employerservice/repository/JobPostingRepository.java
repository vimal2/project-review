package com.revhire.employerservice.repository;

import com.revhire.employerservice.entity.JobPosting;
import com.revhire.employerservice.entity.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    /**
     * Find all job postings by employer ID, ordered by creation date descending
     * @param employerId the employer ID
     * @return List of job postings
     */
    List<JobPosting> findByEmployerIdOrderByCreatedAtDesc(Long employerId);

    /**
     * Find all job postings by status, ordered by creation date descending
     * @param status the job status
     * @return List of job postings
     */
    List<JobPosting> findByStatusOrderByCreatedAtDesc(JobStatus status);

    /**
     * Find job posting by ID and employer ID
     * @param id the job posting ID
     * @param employerId the employer ID
     * @return Optional containing the job posting if found
     */
    Optional<JobPosting> findByIdAndEmployerId(Long id, Long employerId);

    /**
     * Count total jobs by employer ID
     * @param employerId the employer ID
     * @return count of jobs
     */
    long countByEmployerId(Long employerId);

    /**
     * Count jobs by employer ID and status
     * @param employerId the employer ID
     * @param status the job status
     * @return count of jobs
     */
    long countByEmployerIdAndStatus(Long employerId, JobStatus status);

    /**
     * Find job postings by employer ID and status
     * @param employerId the employer ID
     * @param status the job status
     * @return List of job postings
     */
    List<JobPosting> findByEmployerIdAndStatus(Long employerId, JobStatus status);

    /**
     * Search job postings with filters
     * @param location location filter (optional)
     * @param skills skills filter (optional)
     * @param jobType job type filter (optional)
     * @param status job status
     * @return List of job postings
     */
    @Query("SELECT j FROM JobPosting j WHERE j.status = :status " +
           "AND (:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) " +
           "AND (:skills IS NULL OR LOWER(j.skills) LIKE LOWER(CONCAT('%', :skills, '%'))) " +
           "AND (:jobType IS NULL OR LOWER(j.jobType) = LOWER(:jobType)) " +
           "ORDER BY j.createdAt DESC")
    List<JobPosting> searchJobs(@Param("location") String location,
                                @Param("skills") String skills,
                                @Param("jobType") String jobType,
                                @Param("status") JobStatus status);
}
