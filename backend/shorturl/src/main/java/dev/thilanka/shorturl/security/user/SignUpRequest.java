package dev.thilanka.shorturl.security.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @NotNull(message = "First Name should not be empty")
    @NotBlank(message = "First Name should not be empty")
    private String firstName;
    @NotNull(message = "Last Name should not be empty")
    @NotBlank(message = "Last Name should not be empty")
    private String lastName;
    @NotNull(message = "Email should not be empty")
    @NotBlank(message = "Email should not be empty")
    @Email(message = "Enter a valid Email")
    private String email;
    @NotNull(message = "Username should not be empty")
    @NotBlank(message = "Username should not be empty")
    private String username;
    @NotNull(message = "Password should not be empty")
    @NotBlank(message = "Password should not be empty")
    @Size(min = 8, max = 24, message = "Password should be 8-24 characters long")
    private String password;
}
