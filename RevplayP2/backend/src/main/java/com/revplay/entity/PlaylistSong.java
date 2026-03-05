package com.revplay.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "playlist_songs")
public class PlaylistSong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long songId;
    private Integer position;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    public PlaylistSong() {}

    public Long getId() {
        return id;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
}
