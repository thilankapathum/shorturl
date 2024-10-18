package dev.thilanka.shorturl.security.config;

import lombok.*;

//-- DTO TO RETURN JWT AFTER AUTHENTICATION IS COMPLETE --
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String jwt;
}
