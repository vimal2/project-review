package com.revplay.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ARTIST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "artist_seq_gen")
    @SequenceGenerator(
            name = "artist_seq_gen",
            sequenceName = "ARTIST_SEQ",
            allocationSize = 1
    )
    @Column(name = "ARTIST_ID")
    private Long id;

    @Column(name = "ARTIST_NAME", nullable = false, length = 100)
    private String artistName;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "BIO", length = 1000)
    private String bio;

    @Column(name = "GENRE", length = 100)
    private String genre;

    @Column(name = "PROFILE_IMAGE_URL")
    private String profileImageUrl;

    @Column(name = "BANNER_IMAGE_URL")
    private String bannerImageUrl;

    @Column(name = "INSTAGRAM_LINK")
    private String instagramLink;

    @Column(name = "TWITTER_LINK")
    private String twitterLink;

    @Column(name = "YOUTUBE_LINK")
    private String youtubeLink;

    @Column(name = "WEBSITE_LINK")
    private String websiteLink;

    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Song> songs;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Album> albums;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
