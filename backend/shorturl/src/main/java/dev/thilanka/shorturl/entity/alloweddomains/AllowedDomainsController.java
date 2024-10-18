package dev.thilanka.shorturl.entity.alloweddomains;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/domains")
public class AllowedDomainsController {

    @Autowired
    private AllowedDomainsService allowedDomainsService;

    @PreAuthorize("hasAuthority('ADMIN')")  //-- Only ADMINs can create allowed domains
    @PostMapping
    ResponseEntity<AllowedDomainRequest> createAllowedDomain(@Valid @RequestBody AllowedDomainRequest allowedDomainRequest){
        AllowedDomainRequest savedAllowedDomain = allowedDomainsService.createAllowedDomain(allowedDomainRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAllowedDomain);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','USER')")
    @GetMapping
    ResponseEntity<List<AllowedDomainRequest>> getAllAllowedDomains(){
        List<AllowedDomainRequest> allowedDomainsRequests = allowedDomainsService.getAllowedDomains();
        return ResponseEntity.ok(allowedDomainsRequests);
    }
}
