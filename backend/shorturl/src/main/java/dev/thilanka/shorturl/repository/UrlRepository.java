package dev.thilanka.shorturl.repository;

import dev.thilanka.shorturl.entity.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UrlRepository extends MongoRepository<Url,String> {
    Url findByShortUrl(String shortUrl);
    //Optional<Url> findByLongUrl(String longUrl);
    Url findByLongUrl(String longUrl);
}
