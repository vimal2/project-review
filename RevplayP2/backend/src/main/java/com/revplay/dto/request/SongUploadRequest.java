package com.revplay.dto.request;
import com.revplay.entity.Visibility;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongUploadRequest {

    private String title;
    private String genre;
    private Integer duration;          // in seconds
    private String audioFileUrl;       // later replace with MultipartFile
    private Visibility visibility;         // PUBLIC / UNLISTED
}
