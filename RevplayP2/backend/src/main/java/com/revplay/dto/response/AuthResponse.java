package com.revplay.dto.response;

import lombok.*;

@Getter
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String username;
    private String role;
    private Long artistId;
}
