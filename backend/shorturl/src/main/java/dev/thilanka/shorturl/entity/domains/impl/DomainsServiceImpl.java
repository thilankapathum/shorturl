package dev.thilanka.shorturl.entity.domains.impl;

import dev.thilanka.shorturl.entity.domains.DomainRequest;
import dev.thilanka.shorturl.entity.domains.Domains;
import dev.thilanka.shorturl.exception.ResourceAlreadyExistsException;
import dev.thilanka.shorturl.mapper.DomainsMapper;
import dev.thilanka.shorturl.entity.domains.DomainsRepository;
import dev.thilanka.shorturl.entity.domains.DomainsService;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

@Service
public class DomainsServiceImpl implements DomainsService {

    @Autowired
    private DomainsRepository domainsRepository;

    @Override
    public DomainRequest createAllowedDomain(DomainRequest domainRequest) {

        //-- Check validity of URL
        if (isURLValid(domainRequest.getDomainName())) {

            //-- Extract Top Level Domain Name (Eg: google.com)
            String topLevelDomainName = domainExtractor(domainRequest.getDomainName());

            Domains allowedDomainCheck = domainsRepository.findByDomainName(topLevelDomainName);

            //-- Check Domain already exists
            if (Objects.isNull(allowedDomainCheck)) {

                Domains allowedDomain = new Domains();

                //-- Save only TLD
                allowedDomain.setDomainName(topLevelDomainName);
                allowedDomain.setEnabled(domainRequest.isEnabled());
                allowedDomain.setUrlAllowed(domainRequest.isUrlAllowed());
                allowedDomain.setEmailAllowed(domainRequest.isEmailAllowed());
                return new DomainsMapper().domainsToPostDto(domainsRepository.save(allowedDomain));
            } else {
                throw new ResourceAlreadyExistsException("Domain", topLevelDomainName);
            }

        } else {
            throw new IllegalArgumentException("Domain URL is invalid. Please check");
        }
    }

    @Override
    public List<DomainRequest> getAllowedDomains() {
        List<Domains> domains = domainsRepository.findAll();
        return domains.stream().map((url) -> new DomainsMapper().domainsToPostDto(url)).toList();
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

    //-- EXTRACT DOMAIN (TLD) FROM AN EMAIL ADDRESS
    public String emailDomainExtractor(String email) {
        return email.substring(email.indexOf("@") + 1);
    }


    //-- CHECK WHETHER TLD IS ALREADY DEFINED IN DATABASE
    @Override
    public boolean isUrlDomainAllowed(String domainName) {
        Domains domains = domainsRepository.findByDomainName(domainName);
        if (Objects.isNull(domains)) {
            return false;
        } else {
            return domains.isUrlAllowed();
        }
    }

    //-- CHECK WHETHER EMAIL'S DOMAIN IS ALLOWED FOR ACCOUNT CREATION
    @Override
    public boolean isEmailDomainAllowed(String domainName) {
        Domains domains = domainsRepository.findByDomainName(domainName);
        if (Objects.isNull(domains)) {
            return false;
        } else {
            return domains.isEmailAllowed();
        }
    }


}
