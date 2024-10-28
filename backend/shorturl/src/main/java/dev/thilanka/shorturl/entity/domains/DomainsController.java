package dev.thilanka.shorturl.entity.domains;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/domains")
public class DomainsController {

    @Autowired
    private DomainsService domainsService;

    @PreAuthorize("hasAuthority('ADMIN')")  //-- Only ADMINs can create allowed domains
    @PostMapping
    ResponseEntity<DomainRequest> createAllowedDomain(@Valid @RequestBody DomainRequest domainRequest){
        DomainRequest savedAllowedDomain = domainsService.createAllowedDomain(domainRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAllowedDomain);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','USER')")
    @GetMapping
    ResponseEntity<List<DomainRequest>> getAllAllowedDomains(){
        List<DomainRequest> allowedDomainsRequests = domainsService.getAllowedDomains();
        return ResponseEntity.ok(allowedDomainsRequests);
    }
}
