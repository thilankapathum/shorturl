package dev.thilanka.shorturl.entity.alloweddomains;

import java.util.List;

public interface AllowedDomainsService {
    AllowedDomainRequest createAllowedDomain(AllowedDomainRequest allowedDomainRequest);
    List<AllowedDomainRequest> getAllowedDomains();
    String domainExtractor(String domain);
//    AllowedDomains findByDomainName(String domainName);
    boolean isDomainNameAllowed(String domainName);
}
