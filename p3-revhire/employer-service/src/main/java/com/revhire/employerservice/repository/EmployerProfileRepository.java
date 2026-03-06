package com.revhire.employerservice.repository;

import com.revhire.employerservice.entity.EmployerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployerProfileRepository extends JpaRepository<EmployerProfile, Long> {

    /**
     * Find employer profile by user ID
     * @param userId the user ID
     * @return Optional containing the employer profile if found
     */
    Optional<EmployerProfile> findByUserId(Long userId);

    /**
     * Check if an employer profile exists for the given user ID
     * @param userId the user ID
     * @return true if profile exists, false otherwise
     */
    boolean existsByUserId(Long userId);
}
