package com.revature.applicationservice.repository;

import com.revature.applicationservice.entity.JobApplication;
import com.revature.applicationservice.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByJobSeekerIdOrderByAppliedAtDesc(Long jobSeekerId);

    List<JobApplication> findByEmployerId(Long employerId);

    List<JobApplication> findByEmployerIdAndJobId(Long employerId, Long jobId);

    boolean existsByJobIdAndJobSeekerId(Long jobId, Long jobSeekerId);

    Optional<JobApplication> findByJobIdAndJobSeekerId(Long jobId, Long jobSeekerId);

    Optional<JobApplication> findByIdAndJobSeekerId(Long id, Long jobSeekerId);

    Optional<JobApplication> findByIdAndEmployerId(Long applicationId, Long employerId);

    Long countByEmployerId(Long employerId);

    Long countByEmployerIdAndStatus(Long employerId, ApplicationStatus status);

    @Modifying
    void deleteByJobId(Long jobId);

    @Query("SELECT ja.jobId FROM JobApplication ja WHERE ja.jobSeekerId = :jobSeekerId")
    List<Long> findAllJobIdsByJobSeekerId(@Param("jobSeekerId") Long jobSeekerId);
}
