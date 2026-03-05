package com.revplay.service;

import com.revplay.dto.response.PlaylistResponse;
import java.util.List;

public interface PlaylistService {

    PlaylistResponse createPlaylist(String username, String name, String description);

    List<PlaylistResponse> getUserPlaylists(String username);

    PlaylistResponse updatePlaylist(String username, Long playlistId, String name, String description);

    void deletePlaylist(String username, Long playlistId);

    PlaylistResponse addSong(String username, Long playlistId, Long songId);

    PlaylistResponse removeSong(String username, Long playlistId, Long songId);
}
