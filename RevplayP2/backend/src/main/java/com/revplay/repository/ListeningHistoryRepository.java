package com.revplay.repository;

import com.revplay.entity.ListeningHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListeningHistoryRepository extends JpaRepository<ListeningHistory, Long> {

    List<ListeningHistory> findByUserIdOrderByPlayedAtDesc(Long userId, Pageable pageable);

    long countByUserId(Long userId);

    @Query("""
            SELECT COALESCE(SUM(h.song.duration), 0)
            FROM ListeningHistory h
            WHERE h.userId = :userId
            """)
    Long sumTotalListeningDurationByUserId(@Param("userId") Long userId);

    void deleteByUserId(Long userId);

    @Query("""
            SELECT FUNCTION('DATE', h.playedAt), COUNT(h.id)
            FROM ListeningHistory h
            WHERE h.song.artist.id = :artistId
            GROUP BY FUNCTION('DATE', h.playedAt)
            ORDER BY FUNCTION('DATE', h.playedAt)
            """)
    List<Object[]> getDailyTrends(@Param("artistId") Long artistId);

    @Query("""
            SELECT CONCAT('User ', h.userId), COUNT(h.id)
            FROM ListeningHistory h
            WHERE h.song.artist.id = :artistId
            GROUP BY h.userId
            ORDER BY COUNT(h.id) DESC
            """)
    List<Object[]> getTopListeners(@Param("artistId") Long artistId);

    @Query("""
            SELECT h.song.id, COUNT(h.id)
            FROM ListeningHistory h
            WHERE h.song.artist.id = :artistId
            GROUP BY h.song.id
            """)
    List<Object[]> countPlaysBySongForArtist(@Param("artistId") Long artistId);
}
