package com.revplay.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ALBUM")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "album_seq_gen")
    @SequenceGenerator(
            name = "album_seq_gen",
            sequenceName = "ALBUM_SEQ",
            allocationSize = 1
    )
    @Column(name = "ALBUM_ID")
    private Long id;

    @Column(name = "NAME", nullable = false, length = 150)
    private String name;

    @Column(name = "DESCRIPTION", length = 1000)
    private String description;

    @Column(name = "RELEASE_DATE")
    private LocalDate releaseDate;

    @Column(name = "COVER_IMAGE_URL")
    private String coverImageUrl;

    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    // ================= RELATIONSHIPS =================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTIST_ID", nullable = false)
    private Artist artist;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<Song> songs;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}