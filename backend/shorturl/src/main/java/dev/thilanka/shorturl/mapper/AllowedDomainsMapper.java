package dev.thilanka.shorturl.mapper;

import dev.thilanka.shorturl.dto.AllowedDomainsPostDto;
import dev.thilanka.shorturl.entity.AllowedDomains;

public class AllowedDomainsMapper {
    public AllowedDomainsPostDto AllowedDomainsToPostDto(AllowedDomains allowedDomains){
        return new AllowedDomainsPostDto(allowedDomains.getAllowedDomainName(), allowedDomains.isEnabled());
    }
}
