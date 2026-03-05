package com.revplay.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SongDTO {

    private Long id;
    private String title;
    private String genre;
    private Integer duration;
    private LocalDate releaseDate;
    private Long artistId;
    private String artistName;
    private Long albumId;
    private String albumName;
}
