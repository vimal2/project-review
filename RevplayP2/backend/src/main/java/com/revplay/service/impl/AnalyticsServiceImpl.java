package com.revplay.service.impl;

import com.revplay.dto.response.SongResponse;
import com.revplay.dto.response.TopListenerResponse;
import com.revplay.dto.response.TrendResponse;
import com.revplay.entity.Song;
import com.revplay.repository.*;
import com.revplay.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.time.temporal.IsoFields;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final SongRepository songRepository;
    private final FavoriteRepository favoriteRepository;
    private final ListeningHistoryRepository historyRepository;

    @Override
    public Map<String, Long> getOverview(Long artistId) {

        List<Song> songs = songRepository.findByArtistId(artistId);
        Map<Long, Long> playCountMap = getPlayCountMap(artistId);

        long totalSongs = songs.size();
        long totalPlays = songs.stream()
                .mapToLong(song -> playCountMap.getOrDefault(song.getId(), 0L))
                .sum();

        long totalFavorites = songs.stream()
                .mapToLong(song -> favoriteRepository.countBySongId(song.getId()))
                .sum();

        Map<String, Long> result = new HashMap<>();
        result.put("totalSongs", totalSongs);
        result.put("totalPlays", totalPlays);
        result.put("totalFavorites", totalFavorites);

        return result;
    }

    @Override
    public List<SongResponse> getSongPerformance(Long artistId) {
        Map<Long, Long> playCountMap = getPlayCountMap(artistId);

        return songRepository.findByArtistId(artistId)
                .stream()
                .map(song -> SongResponse.builder()
                        .id(song.getId())
                        .title(song.getTitle())
                        .playCount(playCountMap.getOrDefault(song.getId(), 0L))
                        .favoriteCount(favoriteRepository.countBySongId(song.getId()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<TrendResponse> getListeningTrends(Long artistId, String type) {
        List<Object[]> rawData = historyRepository.getDailyTrends(artistId);
        Map<String, Long> grouped = new LinkedHashMap<>();
        String normalizedType = type == null ? "monthly" : type.toLowerCase();

        for (Object[] obj : rawData) {
            LocalDate date = toLocalDate(obj[0]);
            if (date == null) {
                continue;
            }
            long count = ((Number) obj[1]).longValue();
            String key;

            switch (normalizedType) {
                case "weekly":
                    key = date.getYear() + "-W" + String.format("%02d", date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
                    break;
                case "monthly":
                    key = date.format(DateTimeFormatter.ofPattern("yyyy-MM"));
                    break;
                default:
                    key = date.toString();
            }

            grouped.put(key, grouped.getOrDefault(key, 0L) + count);
        }

        return grouped.entrySet().stream()
                .map(e -> new TrendResponse(e.getKey(), e.getValue()))
                .toList();
    }

    @Override
    public List<SongResponse> getTopSongs(Long artistId) {
        Map<Long, Long> playCountMap = getPlayCountMap(artistId);

        return songRepository.findByArtistId(artistId)
                .stream()
                .sorted(Comparator.comparingLong((Song song) ->
                        playCountMap.getOrDefault(song.getId(), 0L)).reversed())
                .map(song -> SongResponse.builder()
                        .id(song.getId())
                        .title(song.getTitle())
                        .playCount(playCountMap.getOrDefault(song.getId(), 0L))
                        .favoriteCount(favoriteRepository.countBySongId(song.getId()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<TopListenerResponse> getTopListeners(Long artistId) {
        return historyRepository.getTopListeners(artistId).stream()
                .map(row -> new TopListenerResponse(
                        String.valueOf(row[0]),
                        ((Number) row[1]).longValue()
                ))
                .toList();
    }

    private Map<Long, Long> getPlayCountMap(Long artistId) {
        Map<Long, Long> playCountMap = new HashMap<>();
        for (Object[] row : historyRepository.countPlaysBySongForArtist(artistId)) {
            Long songId = ((Number) row[0]).longValue();
            Long count = ((Number) row[1]).longValue();
            playCountMap.put(songId, count);
        }
        return playCountMap;
    }

    private LocalDate toLocalDate(Object value) {
        if (value instanceof LocalDate localDate) {
            return localDate;
        }
        if (value instanceof java.sql.Date sqlDate) {
            return sqlDate.toLocalDate();
        }
        if (value instanceof java.util.Date date) {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        if (value instanceof String text) {
            try {
                return LocalDate.parse(text);
            } catch (RuntimeException ignored) {
                return null;
            }
        }
        return null;
    }
}
