package com.revplay.repository;

import com.revplay.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    // Get all albums by artist
    List<Album> findByArtistId(Long artistId);

    // Check if album exists for specific artist
    boolean existsByIdAndArtistId(Long albumId, Long artistId);
}