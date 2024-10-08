package dev.thilanka.shorturl.mapper;


import dev.thilanka.shorturl.dto.UrlBasicDto;
import dev.thilanka.shorturl.entity.Url;

public class UrlMapper {
    public UrlBasicDto UrlToBasic(Url url) {
        return new UrlBasicDto(url.getLongUrl(), url.getShortUrl());
    }

    //-- Include Base URL of the site as prefix for the Short URL
    public UrlBasicDto UrlToBasic(Url url, String baseUrl) {
        String fullShortUrl = baseUrl + url.getShortUrl();
        return new UrlBasicDto(url.getLongUrl(), fullShortUrl);
    }

    public Url UrlBasicDtoToUrl(UrlBasicDto urlBasicDto) {
        Url url = new Url();
        url.setLongUrl(urlBasicDto.getLongUrl());
        url.setShortUrl(urlBasicDto.getShortUrl());
        return url;
    }
}
