package dev.thilanka.shorturl.mapper;

import dev.thilanka.shorturl.entity.alloweddomains.AllowedDomainRequest;
import dev.thilanka.shorturl.entity.alloweddomains.AllowedDomains;

public class AllowedDomainsMapper {
    public AllowedDomainRequest AllowedDomainsToPostDto(AllowedDomains allowedDomains){
        return new AllowedDomainRequest(allowedDomains.getAllowedDomainName(), allowedDomains.isEnabled());
    }
}
