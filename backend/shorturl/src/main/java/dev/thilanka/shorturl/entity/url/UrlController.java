package dev.thilanka.shorturl.entity.url;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @Value("${shorturl.service.defaultredirect}")
    private boolean DEFAULT_REDIRECT;

    @Value("${shorturl.service.defaultredirecturl}")
    private String DEFAULT_REDIRECT_URL;

    //-- CREATE NEW AUTO-GENERATED SHORT-URL
    @PostMapping("/api/url")
    ResponseEntity<CustomUrlRequest> createUrl(@Valid @RequestBody ShortUrlRequest shortUrlRequest) {
        CustomUrlRequest savedUrl = urlService.createShortUrl(shortUrlRequest);
        return ResponseEntity.ok(savedUrl);
    }

    //-- CREATE NEW CUSTOM SHORT-URL WITH GIVEN SHORT-URL
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")     //-- Only Admins & Managers can create custom shortURLs.
    @PostMapping("/api/custom-url")
    ResponseEntity<CustomUrlRequest> createUrl(@Valid @RequestBody CustomUrlRequest customUrlRequest) {
        CustomUrlRequest savedUrl = urlService.createShortUrl(customUrlRequest);
        return ResponseEntity.ok(savedUrl);
    }

    //-- GET ALL URLS WITH ONLY BASIC INFO
    @GetMapping("/api/url")
    ResponseEntity<List<CustomUrlRequest>> getAllUrls() {
        List<CustomUrlRequest> customUrlRequests = urlService.getAllUrlBasic();
        return ResponseEntity.ok(customUrlRequests);
    }



    //-- REDIRECT TO LONG-URL USING EXISTING SHORT-URL
    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable("shortUrl") String shortUrl) {
        String longUrl = urlService.getLongUrl(shortUrl);
        return ResponseEntity.status(302).location(URI.create(longUrl)).build();
    }

    //-- REDIRECT TO A PRE-DEFINED DEFAULT URL AT BASE URL. REMOVE THIS IF THIS FUNCTION IS NOT NEEDED
    @GetMapping("")
    public ResponseEntity<Void> redirectToDefault() {
        if (DEFAULT_REDIRECT) {
            return ResponseEntity.status(302).location(URI.create(DEFAULT_REDIRECT_URL)).build();
        } else return null;
    }

}
