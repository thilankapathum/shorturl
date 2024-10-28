package dev.thilanka.shorturl.mapper;

import dev.thilanka.shorturl.entity.domains.DomainRequest;
import dev.thilanka.shorturl.entity.domains.Domains;

public class AllowedDomainsMapper {
    public DomainRequest AllowedDomainsToPostDto(Domains domains){
        return new DomainRequest(domains.getDomainName(), domains.isEnabled(), domains.isUrlAllowed(), domains.isEmailAllowed());
    }
}
