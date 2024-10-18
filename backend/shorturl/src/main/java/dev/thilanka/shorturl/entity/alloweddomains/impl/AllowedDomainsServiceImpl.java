package dev.thilanka.shorturl.entity.alloweddomains.impl;

import dev.thilanka.shorturl.entity.alloweddomains.AllowedDomainRequest;
import dev.thilanka.shorturl.entity.alloweddomains.AllowedDomains;
import dev.thilanka.shorturl.exception.ResourceAlreadyExistsException;
import dev.thilanka.shorturl.mapper.AllowedDomainsMapper;
import dev.thilanka.shorturl.entity.alloweddomains.AllowedDomainsRepository;
import dev.thilanka.shorturl.entity.alloweddomains.AllowedDomainsService;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

@Service
public class AllowedDomainsServiceImpl implements AllowedDomainsService {

    @Autowired
    private AllowedDomainsRepository allowedDomainsRepository;

    @Override
    public AllowedDomainRequest createAllowedDomain(AllowedDomainRequest allowedDomainRequest) {

        //-- Check validity of URL
        if (isURLValid(allowedDomainRequest.getAllowedDomainName())) {

            //-- Extract Top Level Domain Name (Eg: google.com)
            String topLevelDomainName = domainExtractor(allowedDomainRequest.getAllowedDomainName());

            AllowedDomains allowedDomainCheck = allowedDomainsRepository.findByAllowedDomainName(topLevelDomainName);

            //-- Check Domain already exists
            if (Objects.isNull(allowedDomainCheck)) {

                AllowedDomains allowedDomain = new AllowedDomains();

                //-- Save only TLD
                allowedDomain.setAllowedDomainName(topLevelDomainName);
                allowedDomain.setEnabled(allowedDomainRequest.isEnabled());
                return new AllowedDomainsMapper().AllowedDomainsToPostDto(allowedDomainsRepository.save(allowedDomain));
            } else {
                throw new ResourceAlreadyExistsException("Domain", topLevelDomainName);
            }

        } else {
            throw new IllegalArgumentException("Domain URL is invalid. Please check");
        }
    }

    @Override
    public List<AllowedDomainRequest> getAllowedDomains() {
        List<AllowedDomains> allowedDomains = allowedDomainsRepository.findAll();
        return allowedDomains.stream().map((url) -> new AllowedDomainsMapper().AllowedDomainsToPostDto(url)).toList();
    }

    //-- URL VALIDITY CHECKER
    public static boolean isURLValid(String url) {
        UrlValidator urlValidator = new UrlValidator();
        return urlValidator.isValid(url);
    }

    //-- DOMAIN EXTRACTOR
    public String domainExtractor(String domain) {

        //-- Check for URL validity
        if (isURLValid(domain)) {
            try {
                URL url = new URL(domain);
                String host = url.getHost();

                //-- Split the host to extract Top Level Domain
                String[] domainParts = host.split("\\.");
                int length = domainParts.length;

                //-- If the domain has at least 2 parts (Eg: google.com)
                if (length >= 2) {
                    return domainParts[length - 2] + "." + domainParts[length - 1];
                } else {
                    return host;
                }
            } catch (MalformedURLException exception) {
                exception.printStackTrace();
                return null;
            }
        }
        throw new IllegalArgumentException("Domain URL is invalid. Please check");
    }


    //-- CHECK WHETHER TLD IS ALREADY DEFINED IN DATABASE
    @Override
    public boolean isDomainNameAllowed(String domainName) {
        AllowedDomains allowedDomains = allowedDomainsRepository.findByAllowedDomainName(domainName);
        return !Objects.isNull(allowedDomains);

    }
}
