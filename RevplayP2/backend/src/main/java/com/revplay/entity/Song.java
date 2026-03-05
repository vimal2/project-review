package com.revplay.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "SONG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "song_seq_gen")
    @SequenceGenerator(
            name = "song_seq_gen",
            sequenceName = "SONG_SEQ",
            allocationSize = 1
    )
    @Column(name = "SONG_ID")
    private Long id;

    @Column(name = "TITLE", nullable = false, length = 150)
    private String title;

    @Column(name = "GENRE", length = 100)
    private String genre;

    @Column(name = "DURATION", nullable = false)
    private Integer duration;

    @Column(name = "AUDIO_FILE_URL", nullable = false)
    private String audioFileUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "VISIBILITY", nullable = false)
    private Visibility visibility;

    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTIST_ID", nullable = false)
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ALBUM_ID")
    private Album album;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
