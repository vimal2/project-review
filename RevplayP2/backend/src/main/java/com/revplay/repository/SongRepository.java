package com.revplay.repository;

import com.revplay.entity.Song;
import com.revplay.entity.Visibility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findByArtistId(Long artistId);

    List<Song> findByVisibility(Visibility visibility);

    Optional<Song> findByIdAndVisibility(Long id, Visibility visibility);

    List<Song> findByArtistIdAndVisibility(Long artistId, Visibility visibility);

    long countByAlbumId(Long albumId);
}
