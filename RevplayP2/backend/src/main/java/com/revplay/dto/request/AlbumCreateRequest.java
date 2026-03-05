package com.revplay.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumCreateRequest {

    private String name;
    private String description;
    private LocalDate releaseDate;
    private String coverImageUrl;
}