package com.revplay.service;

import com.revplay.dto.response.ListeningHistoryResponse;

import java.util.List;

public interface ListeningHistoryService {
    void recordPlay(Long userId, Long songId);

    List<ListeningHistoryResponse> getRecentHistory(Long userId, int limit);

    long clearHistory(Long userId);
}
