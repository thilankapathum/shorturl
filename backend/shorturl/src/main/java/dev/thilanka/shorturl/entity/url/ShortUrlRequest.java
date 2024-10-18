package dev.thilanka.shorturl.entity.url;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlRequest {
    @NotNull(message = "Long URL should not be empty!")
    private String longUrl;
}
