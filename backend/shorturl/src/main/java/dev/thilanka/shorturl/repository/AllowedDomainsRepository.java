package dev.thilanka.shorturl.repository;

import dev.thilanka.shorturl.entity.AllowedDomains;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AllowedDomainsRepository extends MongoRepository<AllowedDomains, String> {
    AllowedDomains findByAllowedDomainName(String domainName);
    //Optional<AllowedDomains> findByAllowedDomainName(String domainName);
}
