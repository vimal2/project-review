package com.revplay.repository;

import com.revplay.entity.PlaylistSong;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PlaylistSongRepository extends JpaRepository<PlaylistSong, Long> {

    List<PlaylistSong> findByPlaylistIdOrderByPositionAsc(Long playlistId);

    List<PlaylistSong> findAllByPlaylistIdAndSongId(Long playlistId, Long songId);

    Optional<PlaylistSong> findByPlaylistIdAndSongId(Long playlistId, Long songId);

    long countByPlaylistId(Long playlistId);
}
