package dev.thilanka.shorturl.entity.alloweddomains;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AllowedDomainsRepository extends MongoRepository<AllowedDomains, String> {
    AllowedDomains findByAllowedDomainName(String domainName);
    //Optional<AllowedDomains> findByAllowedDomainName(String domainName);
}
