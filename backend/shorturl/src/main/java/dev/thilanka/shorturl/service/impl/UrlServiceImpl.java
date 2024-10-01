package dev.thilanka.shorturl.service.impl;

import dev.thilanka.shorturl.dto.LongUrlPostDto;
import dev.thilanka.shorturl.dto.UrlBasicDto;
import dev.thilanka.shorturl.entity.Url;
import dev.thilanka.shorturl.mapper.UrlMapper;
import dev.thilanka.shorturl.repository.UrlRepository;
import dev.thilanka.shorturl.service.UrlService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UrlServiceImpl implements UrlService {

    @Autowired
    private UrlRepository urlRepository;

    //-- GET ALL URLS WITH ALL INFO
    @Override
    public List<Url> getAllUrls() {
        return urlRepository.findAll();
    }

    //-- GET ALL URLS WITH ONLY BASIC INFO
    @Override
    public List<UrlBasicDto> getAllUrlBasic() {
        List<Url> urls = urlRepository.findAll();
        return urls.stream().map((url) -> new UrlMapper().UrlToBasic(url)).toList();
    }

    @Override
    public UrlBasicDto createShortUrl(LongUrlPostDto longUrlPostDto) {

        Url url = urlRepository.findByLongUrl(longUrlPostDto.getLongUrl());

        //-- CHECK LONG-URL EXISTS
        if (Objects.isNull(url)) {
            Url savedUrl = new Url();
            savedUrl.setShortUrl(generateShortUrl(6, 128));
            savedUrl.setLongUrl(longUrlPostDto.getLongUrl());
            return new UrlMapper().UrlToBasic(urlRepository.save(savedUrl));
        } else {
            return new UrlMapper().UrlToBasic(url);
        }
    }

    @Override
    public String getLongUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl);
        if (Objects.isNull(url)){
            throw new RuntimeException("Short URL is invalid");
        } else {
            return url.getLongUrl();
        }
    }

    private String generateShortUrl(int charLength, int loopTimes) {
        int counter = 0;
        String shortUrl;

        //-- CHECKING GENERATED SHORT-URL ALREADY EXISTS
        do {
            if (counter == loopTimes - 1) {
                System.out.println("Loop times reached.");
                throw new RuntimeException("Loop times reached. Try again");
            }
            shortUrl = RandomStringUtils.randomAlphanumeric(charLength);
            System.out.println("Short URL: " + shortUrl);
            counter++;
        } while (!Objects.isNull(urlRepository.findByShortUrl(shortUrl)));
        return shortUrl;
    }

}
