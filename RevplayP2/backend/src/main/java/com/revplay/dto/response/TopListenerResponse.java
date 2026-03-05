package com.revplay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TopListenerResponse {
    private String listenerName;
    private Long listenCount;
}
