package dev.thilanka.shorturl.entity.url;

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
public class CustomUrlRequest {
    @NotNull(message = "Long URL should not be empty!")
    private String longUrl;

    @NotNull(message = "Short URL should not be empty!")
    @Size(min = 2, max = 6,message = "Short URL should be 2-6 characters in length")
    private String shortUrl;
}
