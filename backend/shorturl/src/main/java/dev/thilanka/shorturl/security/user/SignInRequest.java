package dev.thilanka.shorturl.security.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {
    @NotNull(message = "Username should not be empty")
    @NotBlank(message = "Username should not be empty")
    private String username;
    @NotNull(message = "Password should not be empty")
    @NotBlank(message = "Password should not be empty")
    @Size(min = 8, max = 16,message = "Password should be 8-16 characters long")
    private String password;
}
