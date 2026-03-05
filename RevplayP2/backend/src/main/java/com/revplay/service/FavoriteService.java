package com.revplay.service;

import java.util.List;

public interface FavoriteService {

    void addFavorite(String username, Long songId);

    void removeFavorite(String username, Long songId);

    List<Long> getFavorites(String username);
}