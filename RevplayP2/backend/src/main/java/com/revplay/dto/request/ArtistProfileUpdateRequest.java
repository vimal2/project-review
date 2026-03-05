package com.revplay.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistProfileUpdateRequest {

    private String artistName;
    private String bio;
    private String genre;

    private String instagramLink;
    private String twitterLink;
    private String youtubeLink;
    private String websiteLink;
}