package com.revplay.service.impl;

import com.revplay.entity.Favorite;
import com.revplay.entity.Role;
import com.revplay.entity.User;
import com.revplay.repository.FavoriteRepository;
import com.revplay.repository.UserRepository;
import com.revplay.service.FavoriteService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository,
                               UserRepository userRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void addFavorite(String username, Long songId) {
        User user = getOrCreateUser(username);
        if (favoriteRepository.existsByUserAndSongId(user, songId)) {
            return;
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setSongId(songId);

        favoriteRepository.save(favorite);
    }

    @Override
    @Transactional
    public void removeFavorite(String username, Long songId) {
        User user = findExistingUser(username);
        if (user == null) {
            return;
        }

        List<Favorite> favorites = favoriteRepository.findAllByUserAndSongId(user, songId);
        if (favorites.isEmpty()) {
            return;
        }
        favoriteRepository.deleteAll(favorites);
    }

    @Override
    public List<Long> getFavorites(String username) {
        User user = findExistingUser(username);
        if (user == null) {
            return List.of();
        }

        return favoriteRepository.findByUser(user)
                .stream()
                .map(Favorite::getSongId)
                .collect(Collectors.toList());
    }

    private User getOrCreateUser(String username) {
        User existing = findExistingUser(username);
        if (existing != null) {
            return existing;
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

    private User findExistingUser(String username) {
        List<User> users = userRepository.findAllByUsernameOrderByIdAsc(username);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }
}
