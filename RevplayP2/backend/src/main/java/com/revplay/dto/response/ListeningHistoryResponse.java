package com.revplay.dto.response;

public class ListeningHistoryResponse {
    private Long historyId;
    private Long userId;
    private Long songId;
    private String songTitle;
    private String songUrl;
    private String playedAt;

    public ListeningHistoryResponse() {
    }

    public ListeningHistoryResponse(Long historyId, Long userId, Long songId, String songTitle, String songUrl, String playedAt) {
        this.historyId = historyId;
        this.userId = userId;
        this.songId = songId;
        this.songTitle = songTitle;
        this.songUrl = songUrl;
        this.playedAt = playedAt;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(String playedAt) {
        this.playedAt = playedAt;
    }
}
