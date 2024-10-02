package dev.thilanka.shorturl.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UrlBasicDto {
    @NotNull(message = "Long URL should not be empty!")
    private String longUrl;

    @NotNull(message = "Short URL should not be empty!")
    @Size(min = 4, max = 6,message = "Short URL should be 4-6 characters in length")
    private String shortUrl;
}
