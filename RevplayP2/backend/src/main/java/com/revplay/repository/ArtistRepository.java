package com.revplay.repository;

import com.revplay.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    // Find artist by email (for login / validation)
    Optional<Artist> findByEmail(String email);

    // Check if email already exists
    boolean existsByEmail(String email);
}