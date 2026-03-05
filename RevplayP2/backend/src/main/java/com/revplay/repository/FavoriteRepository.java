package com.revplay.repository;

import com.revplay.entity.Favorite;
import com.revplay.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUser(User user);

    List<Favorite> findAllByUserAndSongId(User user, Long songId);

    boolean existsByUserAndSongId(User user, Long songId);

    long countByUser(User user);

    long countBySongId(Long songId);
}
