package dev.thilanka.shorturl.controller;

import dev.thilanka.shorturl.dto.LongUrlPostDto;
import dev.thilanka.shorturl.dto.UrlBasicDto;
import dev.thilanka.shorturl.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("")
public class UrlController {

    @Autowired
    private UrlService urlService;

    //-- CREATE NEW SHORT-URL
    @PostMapping("/api/url")
    ResponseEntity<UrlBasicDto> createUrl(@Valid @RequestBody LongUrlPostDto longUrlPostDto) {
        UrlBasicDto savedUrl = urlService.createShortUrl(longUrlPostDto);
        return ResponseEntity.ok(savedUrl);
    }

    //-- CREATE NEW SHORT-URL WITH GIVEN SHORT-URL
    @PostMapping("/api/urls")
    ResponseEntity<UrlBasicDto> createUrl(@Valid @RequestBody UrlBasicDto urlBasicDto) {
        UrlBasicDto savedUrl = urlService.createShortUrl(urlBasicDto);
        return ResponseEntity.ok(savedUrl);
    }

    //-- GET ALL URLS WITH ONLY BASIC INFO
    @GetMapping("/api/url")
    ResponseEntity<List<UrlBasicDto>> getAllUrls() {
        List<UrlBasicDto> urlBasicDtos = urlService.getAllUrlBasic();
        return ResponseEntity.ok(urlBasicDtos);
    }

    //-- REDIRECT TO LONG-URL USING EXISTING SHORT-URL
    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable("shortUrl") String shortUrl){
        String longUrl = urlService.getLongUrl(shortUrl);
        return ResponseEntity.status(302).location(URI.create(longUrl)).build();
    }



}
