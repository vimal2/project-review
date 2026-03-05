package com.revplay.dto.request;

import com.revplay.entity.Visibility;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongUpdateRequest {

    private String title;
    private String genre;
    private Integer duration;
    private Visibility visibility;
}