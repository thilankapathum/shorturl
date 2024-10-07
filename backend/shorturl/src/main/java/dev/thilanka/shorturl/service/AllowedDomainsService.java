package dev.thilanka.shorturl.service;

import dev.thilanka.shorturl.dto.AllowedDomainsPostDto;

import java.util.List;

public interface AllowedDomainsService {
    AllowedDomainsPostDto createAllowedDomain(AllowedDomainsPostDto allowedDomainsPostDto);
    List<AllowedDomainsPostDto> getAllowedDomains();
    String domainExtractor(String domain);
//    AllowedDomains findByDomainName(String domainName);
    boolean isDomainNameAllowed(String domainName);
}
