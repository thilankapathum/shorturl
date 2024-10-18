package dev.thilanka.shorturl.mapper;


import dev.thilanka.shorturl.entity.url.CustomUrlRequest;
import dev.thilanka.shorturl.entity.url.Url;

public class UrlMapper {
    public CustomUrlRequest UrlToBasic(Url url) {
        return new CustomUrlRequest(url.getLongUrl(), url.getShortUrl());
    }

    //-- Include Base URL of the site as prefix for the Short URL
    public CustomUrlRequest UrlToBasic(Url url, String baseUrl) {
        String fullShortUrl = baseUrl + url.getShortUrl();
        return new CustomUrlRequest(url.getLongUrl(), fullShortUrl);
    }

    public Url UrlBasicDtoToUrl(CustomUrlRequest customUrlRequest) {
        Url url = new Url();
        url.setLongUrl(customUrlRequest.getLongUrl());
        url.setShortUrl(customUrlRequest.getShortUrl());
        return url;
    }
}
