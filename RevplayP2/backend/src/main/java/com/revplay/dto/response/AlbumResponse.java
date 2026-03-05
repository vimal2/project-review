package com.revplay.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumResponse {

    private Long id;
    private String name;
    private String description;

    private LocalDate releaseDate;
    private String coverImageUrl;

    private String artistName;
    private Integer totalSongs;

    private LocalDateTime createdAt;
}