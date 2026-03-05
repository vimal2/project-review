package com.revplay.service.impl;

import com.revplay.dto.response.ListeningHistoryResponse;
import com.revplay.entity.ListeningHistory;
import com.revplay.entity.Song;
import com.revplay.repository.ListeningHistoryRepository;
import com.revplay.repository.SongRepository;
import com.revplay.service.ListeningHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ListeningHistoryServiceImpl implements ListeningHistoryService {

    private static final int MAX_LIMIT = 50;
    private static final Logger log = LoggerFactory.getLogger(ListeningHistoryServiceImpl.class);

    private final ListeningHistoryRepository listeningHistoryRepository;
    private final SongRepository songRepository;

    public ListeningHistoryServiceImpl(ListeningHistoryRepository listeningHistoryRepository, SongRepository songRepository) {
        this.listeningHistoryRepository = listeningHistoryRepository;
        this.songRepository = songRepository;
    }

    @Override
    @Transactional
    public void recordPlay(Long userId, Long songId) {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> {
                    log.warn("Cannot record play. songId={} not found for userId={}", songId, userId);
                    return new ResponseStatusException(NOT_FOUND, "Song not found");
                });

        ListeningHistory history = new ListeningHistory();
        history.setUserId(userId);
        history.setSong(song);
        history.setPlayedAt(LocalDateTime.now());
        listeningHistoryRepository.save(history);

        log.info("Recorded listening history: userId={}, songId={}, historyId={}",
                userId, songId, history.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListeningHistoryResponse> getRecentHistory(Long userId, int limit) {
        int safeLimit = Math.min(Math.max(limit, 1), MAX_LIMIT);
        List<ListeningHistoryResponse> response = listeningHistoryRepository
                .findByUserIdOrderByPlayedAtDesc(userId, PageRequest.of(0, safeLimit))
                .stream()
                .map(this::toResponse)
                .toList();

        log.info("Fetched listening history: userId={}, requestedLimit={}, appliedLimit={}, resultCount={}",
                userId, limit, safeLimit, response.size());
        return response;
    }

    @Override
    @Transactional
    public long clearHistory(Long userId) {
        long count = listeningHistoryRepository.countByUserId(userId);
        listeningHistoryRepository.deleteByUserId(userId);
        log.info("Cleared listening history: userId={}, removedCount={}", userId, count);
        return count;
    }

    private ListeningHistoryResponse toResponse(ListeningHistory history) {
        return new ListeningHistoryResponse(
                history.getId(),
                history.getUserId(),
                history.getSong().getId(),
                history.getSong().getTitle(),
                history.getSong().getAudioFileUrl(),
                history.getPlayedAt().toString()
        );
    }
}
