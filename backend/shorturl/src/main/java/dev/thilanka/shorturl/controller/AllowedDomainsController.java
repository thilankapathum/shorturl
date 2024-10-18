package dev.thilanka.shorturl.controller;

import dev.thilanka.shorturl.dto.AllowedDomainsPostDto;
import dev.thilanka.shorturl.service.AllowedDomainsService;
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
    ResponseEntity<AllowedDomainsPostDto> createAllowedDomain(@Valid @RequestBody AllowedDomainsPostDto allowedDomainsPostDto){
        AllowedDomainsPostDto savedAllowedDomain = allowedDomainsService.createAllowedDomain(allowedDomainsPostDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAllowedDomain);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','USER')")
    @GetMapping
    ResponseEntity<List<AllowedDomainsPostDto>> getAllAllowedDomains(){
        List<AllowedDomainsPostDto> allowedDomainsPostDtos = allowedDomainsService.getAllowedDomains();
        return ResponseEntity.ok(allowedDomainsPostDtos);
    }
}
