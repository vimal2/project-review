package com.revplay.dto.response;

public class UserStatsResponse {

    private long totalPlaylists;
    private long totalFavorites;
    private long totalListeningMinutes;

    public UserStatsResponse(long totalPlaylists,
                             long totalFavorites,
                             long totalListeningMinutes) {
        this.totalPlaylists = totalPlaylists;
        this.totalFavorites = totalFavorites;
        this.totalListeningMinutes = totalListeningMinutes;
    }

    public long getTotalPlaylists() { return totalPlaylists; }
    public long getTotalFavorites() { return totalFavorites; }
    public long getTotalListeningMinutes() { return totalListeningMinutes; }
}
