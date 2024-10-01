package dev.thilanka.shorturl.service;

import dev.thilanka.shorturl.dto.LongUrlPostDto;
import dev.thilanka.shorturl.dto.UrlBasicDto;
import dev.thilanka.shorturl.entity.Url;

import java.util.List;

public interface UrlService {

    List<Url> getAllUrls();
    List<UrlBasicDto> getAllUrlBasic();
    UrlBasicDto createShortUrl(LongUrlPostDto longUrlPostDto);
    String getLongUrl(String shortUrl);
}
