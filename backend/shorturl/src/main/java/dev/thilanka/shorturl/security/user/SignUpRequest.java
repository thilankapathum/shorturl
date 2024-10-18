package dev.thilanka.shorturl.security.user;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
}
