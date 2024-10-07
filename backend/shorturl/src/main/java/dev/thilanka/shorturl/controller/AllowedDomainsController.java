package dev.thilanka.shorturl.controller;

import dev.thilanka.shorturl.dto.AllowedDomainsPostDto;
import dev.thilanka.shorturl.service.AllowedDomainsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/domains")
public class AllowedDomainsController {

    @Autowired
    private AllowedDomainsService allowedDomainsService;

    @PostMapping
    ResponseEntity<AllowedDomainsPostDto> createAllowedDomain(@Valid @RequestBody AllowedDomainsPostDto allowedDomainsPostDto){
        AllowedDomainsPostDto savedAllowedDomain = allowedDomainsService.createAllowedDomain(allowedDomainsPostDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAllowedDomain);
    }

    @GetMapping
    ResponseEntity<List<AllowedDomainsPostDto>> getAllAllowedDomains(){
        List<AllowedDomainsPostDto> allowedDomainsPostDtos = allowedDomainsService.getAllowedDomains();
        return ResponseEntity.ok(allowedDomainsPostDtos);
    }
}
