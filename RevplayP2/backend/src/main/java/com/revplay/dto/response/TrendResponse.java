package com.revplay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TrendResponse {
    private String label;
    private Long count;
}
