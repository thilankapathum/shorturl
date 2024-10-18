package dev.thilanka.shorturl.entity.url;

import java.util.List;

public interface UrlService {

    List<Url> getAllUrls();
    List<CustomUrlRequest> getAllUrlBasic();
    CustomUrlRequest createShortUrl(ShortUrlRequest shortUrlRequest);
    CustomUrlRequest createShortUrl(CustomUrlRequest customUrlRequest);

    String getLongUrl(String shortUrl);
}
