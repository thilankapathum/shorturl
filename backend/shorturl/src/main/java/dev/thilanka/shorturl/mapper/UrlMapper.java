package dev.thilanka.shorturl.mapper;


import dev.thilanka.shorturl.dto.UrlBasicDto;
import dev.thilanka.shorturl.entity.Url;

public class UrlMapper {
    public UrlBasicDto UrlToBasic(Url url){
        return new UrlBasicDto(url.getLongUrl(), url.getShortUrl());
    }

    public Url UrlBasicDtoToUrl(UrlBasicDto urlBasicDto){
        Url url = new Url();
        url.setLongUrl(urlBasicDto.getLongUrl());
        url.setShortUrl(urlBasicDto.getShortUrl());
        return url;
    }
}
