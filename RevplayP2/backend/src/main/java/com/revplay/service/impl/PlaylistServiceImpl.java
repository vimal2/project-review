package com.revplay.service.impl;

import com.revplay.dto.response.PlaylistResponse;
import com.revplay.entity.Playlist;
import com.revplay.entity.PlaylistSong;
import com.revplay.entity.Role;
import com.revplay.entity.User;
import com.revplay.repository.PlaylistRepository;
import com.revplay.repository.PlaylistSongRepository;
import com.revplay.repository.UserRepository;
import com.revplay.service.PlaylistService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistSongRepository playlistSongRepository;
    private final UserRepository userRepository;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository,
                               PlaylistSongRepository playlistSongRepository,
                               UserRepository userRepository) {
        this.playlistRepository = playlistRepository;
        this.playlistSongRepository = playlistSongRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PlaylistResponse createPlaylist(String username, String name, String description) {
        User user = getOrCreateUser(username);
        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setDescription(description);
        playlist.setUser(user);
        Playlist saved = playlistRepository.save(playlist);
        return toResponse(saved);
    }

    @Override
    public List<PlaylistResponse> getUserPlaylists(String username) {
        User user = getOrCreateUser(username);
        return playlistRepository.findByUser(user)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public PlaylistResponse updatePlaylist(String username, Long playlistId, String name, String description) {
        Playlist playlist = getUserPlaylist(username, playlistId);
        playlist.setName(name);
        playlist.setDescription(description);
        return toResponse(playlistRepository.save(playlist));
    }

    @Override
    public void deletePlaylist(String username, Long playlistId) {
        Playlist playlist = getUserPlaylist(username, playlistId);
        playlistRepository.delete(playlist);
    }

    @Override
    @Transactional
    public PlaylistResponse addSong(String username, Long playlistId, Long songId) {
        Playlist playlist = getUserPlaylist(username, playlistId);

        boolean exists = playlistSongRepository
                .findByPlaylistIdAndSongId(playlistId, songId)
                .isPresent();
        if (!exists) {
            PlaylistSong playlistSong = new PlaylistSong();
            playlistSong.setPlaylist(playlist);
            playlistSong.setSongId(songId);
            playlistSong.setPosition((int) playlistSongRepository.countByPlaylistId(playlistId) + 1);
            playlistSongRepository.save(playlistSong);
        }
        return toResponse(playlist);
    }

    @Override
    @Transactional
    public PlaylistResponse removeSong(String username, Long playlistId, Long songId) {
        Playlist playlist = getUserPlaylist(username, playlistId);
        List<PlaylistSong> entries = playlistSongRepository.findAllByPlaylistIdAndSongId(playlistId, songId);
        if (entries.isEmpty()) {
            return toResponse(playlist);
        }
        playlistSongRepository.deleteAll(entries);
        normalizeOrder(playlistId);
        return toResponse(playlist);
    }

    private void normalizeOrder(Long playlistId) {
        List<PlaylistSong> songs = playlistSongRepository.findByPlaylistIdOrderByPositionAsc(playlistId);
        for (int i = 0; i < songs.size(); i++) {
            PlaylistSong song = songs.get(i);
            song.setPosition(i + 1);
            playlistSongRepository.save(song);
        }
    }

    private PlaylistResponse toResponse(Playlist playlist) {
        List<Long> songIds = playlistSongRepository.findByPlaylistIdOrderByPositionAsc(playlist.getId())
                .stream()
                .map(PlaylistSong::getSongId)
                .toList();
        return new PlaylistResponse(
                playlist.getId(),
                playlist.getName(),
                playlist.getDescription(),
                songIds
        );
    }

    private Playlist getUserPlaylist(String username, Long playlistId) {
        User user = getOrCreateUser(username);
        return playlistRepository.findByIdAndUser(playlistId, user)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
    }

    private User getOrCreateUser(String username) {
        List<User> users = userRepository.findAllByUsernameOrderByIdAsc(username);
        if (!users.isEmpty()) {
            return users.get(0);
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(username + "@revplay.local");
        user.setPassword("placeholder");
        user.setRole(Role.USER);
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setDisplayName("New Listener");
        user.setBio("Add your bio");
        user.setProfileImage("https://placehold.co/120x120");
        return userRepository.save(user);
    }
}
