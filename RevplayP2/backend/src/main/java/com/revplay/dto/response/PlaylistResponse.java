package com.revplay.dto.response;

import java.util.List;

public class PlaylistResponse {

    private Long id;
    private String name;
    private String description;
    private List<Long> songIds;

    public PlaylistResponse(Long id, String name, String description, List<Long> songIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.songIds = songIds;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Long> getSongIds() {
        return songIds;
    }
}
