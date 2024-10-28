package dev.thilanka.shorturl.entity.domains;

import java.util.List;

public interface DomainsService {
    DomainRequest createAllowedDomain(DomainRequest domainRequest);
    List<DomainRequest> getAllowedDomains();
    String domainExtractor(String domain);
//    Domains findByDomainName(String domainName);
    boolean isUrlDomainAllowed(String domainName);
    boolean isEmailDomainAllowed(String domainName);
    String emailDomainExtractor(String email);
}
