package dev.thilanka.shorturl.entity.url;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<Url,String> {
    Url findByShortUrl(String shortUrl);
    //Optional<Url> findByLongUrl(String longUrl);
    Url findByLongUrl(String longUrl);
}
