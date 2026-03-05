package com.revplay.dto.request;

import jakarta.validation.constraints.NotNull;

public class PlaySongRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long songId;

    public PlaySongRequest() {
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
}
