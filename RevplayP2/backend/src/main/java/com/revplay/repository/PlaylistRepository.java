package com.revplay.repository;

import com.revplay.entity.Playlist;
import com.revplay.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> findByUser(User user);
    java.util.Optional<Playlist> findByIdAndUser(Long id, User user);

    long countByUser(User user);
}
