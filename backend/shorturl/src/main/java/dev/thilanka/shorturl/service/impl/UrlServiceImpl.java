package dev.thilanka.shorturl.service.impl;

import dev.thilanka.shorturl.dto.LongUrlPostDto;
import dev.thilanka.shorturl.dto.UrlBasicDto;
import dev.thilanka.shorturl.entity.Url;
import dev.thilanka.shorturl.exception.ResourceAlreadyExistsException;
import dev.thilanka.shorturl.exception.ResourceNotFoundException;
import dev.thilanka.shorturl.mapper.UrlMapper;
import dev.thilanka.shorturl.repository.AllowedDomainsRepository;
import dev.thilanka.shorturl.repository.UrlRepository;
import dev.thilanka.shorturl.service.AllowedDomainsService;
import dev.thilanka.shorturl.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    //-- Defined baseurl at application-dev.yml
    @Value("${shorturl.service.baseurl}")
    private String BASE_URL;

    //-- Defined whether to allow all URLs to be shortened at application-dev.yml
    @Value("${shorturl.service.allowallurls}")
    private boolean ALLOW_ALL_URLS;

    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private AllowedDomainsService allowedDomainsService;
    @Autowired
    private AllowedDomainsRepository allowedDomainsRepository;

    //-- GET ALL URLS WITH ALL INFO
    @Override
    public List<Url> getAllUrls() {
        return urlRepository.findAll();
    }

    //-- GET ALL URLS WITH ONLY BASIC INFO
    @Override
    public List<UrlBasicDto> getAllUrlBasic() {
        List<Url> urls = urlRepository.findAll();
        return urls.stream().map((url) -> new UrlMapper().UrlToBasic(url, BASE_URL)).toList();
    }

    //-- CREATE SHORT-URL WITH ONLY LONG-URL
    @Override
    public UrlBasicDto createShortUrl(LongUrlPostDto longUrlPostDto) {

        //-- CHECK WHETHER TLD IS ALLOWED (Whether all URL shortening is allowed OR Whether Domain of Long URL is included in Allowed Domains Database)
        String topLevelDomain = allowedDomainsService.domainExtractor(longUrlPostDto.getLongUrl());

        if (ALLOW_ALL_URLS || allowedDomainsService.isDomainNameAllowed(topLevelDomain)) {

            Url url = urlRepository.findByLongUrl(longUrlPostDto.getLongUrl());

            //-- CHECK FOR EXISTING LONG-URL
            if (Objects.isNull(url)) {
                Url savedUrl = new Url();
                savedUrl.setShortUrl(generateShortUrl(6, 128));
                savedUrl.setLongUrl(longUrlPostDto.getLongUrl());

                //-- Save new URL combination
                return new UrlMapper().UrlToBasic(urlRepository.save(savedUrl), BASE_URL);
            } else {
                return new UrlMapper().UrlToBasic(url, BASE_URL);
            }
        } else {
            throw new IllegalArgumentException("Domain of the URL is not accepted");
        }
    }

    //-- CREATE CUSTOM SHORT-URL
    @Override
    public UrlBasicDto createShortUrl(UrlBasicDto urlBasicDto) {
        Url url = new Url();

        //-- CHECK FOR EXISTING SHORT-URL && CHECK SHORT-URL FOR INVALID CHARACTERS
        if (isAlphanumeric(urlBasicDto.getShortUrl())) {
            if (Objects.isNull(urlRepository.findByShortUrl(urlBasicDto.getShortUrl()))) {
                url.setShortUrl(urlBasicDto.getShortUrl());
            } else {
                throw new ResourceAlreadyExistsException("Short URL", BASE_URL + urlBasicDto.getShortUrl());
            }
        } else {
            throw new IllegalArgumentException("Short URL contains invalid characters");
        }

        //-- CHECK WHETHER TLD IS ALLOWED (Whether all URL shortening is allowed OR Whether Domain of Long URL is included in Allowed Domains Database)
        String topLevelDomain = allowedDomainsService.domainExtractor(urlBasicDto.getLongUrl());

        if (ALLOW_ALL_URLS || allowedDomainsService.isDomainNameAllowed(topLevelDomain)) {

            //-- CHECK FOR EXISTING LONG-URL
            if (Objects.isNull(urlRepository.findByLongUrl(urlBasicDto.getLongUrl()))) {
                url.setLongUrl(urlBasicDto.getLongUrl());
            } else {
                String shortUrl = urlRepository.findByLongUrl(urlBasicDto.getLongUrl()).getShortUrl();
                throw new ResourceAlreadyExistsException("Long URL", "Short URL", BASE_URL + shortUrl);
            }
        } else {
            throw new IllegalArgumentException("Domain of the URL is not accepted");
        }

        //-- SAVE URL COMBINATION & RETURN DTO
        return new UrlMapper().UrlToBasic(urlRepository.save(url), BASE_URL);
    }

    @Override
    public String getLongUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl);
        if (Objects.isNull(url)) {
            throw new ResourceNotFoundException("Long URL", "Short URL", BASE_URL + shortUrl);
        } else {
            return url.getLongUrl();
        }
    }

    //-- GENERATE NEW SHORT-URL
    private String generateShortUrl(int charLength, int loopTimes) {
        int counter = 0;
        String shortUrl;

        //-- CHECKING GENERATED SHORT-URL ALREADY EXISTS
        do {
            if (counter == loopTimes - 1) {
                System.out.println("Loop times reached.");  //-- To avoid infinitely creating ShortURLs.
                throw new RuntimeException("Loop times reached. Try again");
            }
            shortUrl = generateRandomString(charLength);
            System.out.println("Short URL: " + BASE_URL + shortUrl);
            counter++;
        } while (!Objects.isNull(urlRepository.findByShortUrl(shortUrl)));
        return shortUrl;
    }

    //-- RANDOM STRING GENERATOR
    private String generateRandomString(int count) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder(count);

        for (int i = 0; i < count; i++) {
            int index = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(index));
        }

        return stringBuilder.toString();
    }

    //-- ALPHANUMERIC CHARACTER CHECKER
    private boolean isAlphanumeric(String string) {
        return string != null && string.matches("[a-zA-Z0-9]+");
    }

}
