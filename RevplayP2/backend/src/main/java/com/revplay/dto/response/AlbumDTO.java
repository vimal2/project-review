package com.revplay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO {

    private Long id;
    private String name;
    private LocalDate releaseDate;

    private List<SongDTO> songs;
}
