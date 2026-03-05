package com.revplay.service;

import com.revplay.dto.response.SongResponse;
import com.revplay.dto.response.TopListenerResponse;
import com.revplay.dto.response.TrendResponse;
import java.util.List;
import java.util.Map;

public interface AnalyticsService {

    Map<String, Long> getOverview(Long artistId);

    List<SongResponse> getSongPerformance(Long artistId);

    List<SongResponse> getTopSongs(Long artistId);

    List<TrendResponse> getListeningTrends(Long artistId, String type);

    List<TopListenerResponse> getTopListeners(Long artistId);
}
