package com.revplay.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistResponse {

    private Long id;
    private String artistName;
    private String email;

    private String bio;
    private String genre;

    private String profileImageUrl;
    private String bannerImageUrl;

    private String instagramLink;
    private String twitterLink;
    private String youtubeLink;
    private String websiteLink;

    private LocalDateTime createdAt;
}