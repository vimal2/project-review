package com.revplay.service.impl;

import com.revplay.dto.request.UserProfileUpdateRequest;
import com.revplay.dto.response.UserProfileResponse;
import com.revplay.dto.response.UserStatsResponse;
import com.revplay.entity.Role;
import com.revplay.entity.User;
import com.revplay.repository.FavoriteRepository;
import com.revplay.repository.ListeningHistoryRepository;
import com.revplay.repository.PlaylistRepository;
import com.revplay.repository.UserRepository;
import com.revplay.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;
    private final FavoriteRepository favoriteRepository;
    private final ListeningHistoryRepository listeningHistoryRepository;

    public UserServiceImpl(UserRepository userRepository,
                           PlaylistRepository playlistRepository,
                           FavoriteRepository favoriteRepository,
                           ListeningHistoryRepository listeningHistoryRepository) {
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
        this.favoriteRepository = favoriteRepository;
        this.listeningHistoryRepository = listeningHistoryRepository;
    }

    @Override
    public UserProfileResponse getProfile(String username) {
        User user = getOrCreateUser(username);
        return new UserProfileResponse(
                user.getUsername(),
                user.getEmail(),
                user.getDisplayName(),
                user.getBio(),
                user.getProfileImage()
        );
    }

    @Override
    public UserProfileResponse updateProfile(String username, UserProfileUpdateRequest updatedUser) {
        User user = getOrCreateUser(username);

        user.setUsername(resolveValue(updatedUser.getUsername(), user.getUsername()));
        user.setEmail(resolveValue(updatedUser.getEmail(), user.getEmail()));
        user.setDisplayName(resolveValue(updatedUser.getDisplayName(), user.getDisplayName()));
        user.setBio(resolveValue(updatedUser.getBio(), user.getBio()));
        user.setProfileImage(resolveValue(updatedUser.getProfileImage(), user.getProfileImage()));
        User saved = userRepository.save(user);

        return new UserProfileResponse(
                saved.getUsername(),
                saved.getEmail(),
                saved.getDisplayName(),
                saved.getBio(),
                saved.getProfileImage()
        );
    }

    private String resolveValue(String requestedValue, String currentValue) {
        if (requestedValue == null) {
            return currentValue;
        }

        String trimmed = requestedValue.trim();
        return trimmed.isEmpty() ? currentValue : trimmed;
    }

    @Override
    public UserStatsResponse getStats(String username) {
        User user = getOrCreateUser(username);
        long totalPlaylists = playlistRepository.countByUser(user);
        long totalFavorites = favoriteRepository.countByUser(user);
        long totalListeningSeconds = listeningHistoryRepository
                .sumTotalListeningDurationByUserId(user.getId());
        long totalListeningMinutes = (totalListeningSeconds + 59) / 60;

        return new UserStatsResponse(totalPlaylists, totalFavorites, totalListeningMinutes);
    }

    private User getOrCreateUser(String username) {
        java.util.List<User> users = userRepository.findAllByUsernameOrderByIdAsc(username);
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
