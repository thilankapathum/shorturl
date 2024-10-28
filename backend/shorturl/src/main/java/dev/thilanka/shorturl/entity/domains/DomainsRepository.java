package dev.thilanka.shorturl.entity.domains;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DomainsRepository extends MongoRepository<Domains, String> {
    Domains findByDomainName(String domainName);
    //Optional<Domains> findByAllowedDomainName(String domainName);
}
