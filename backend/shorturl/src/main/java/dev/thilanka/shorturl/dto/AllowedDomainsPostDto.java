package dev.thilanka.shorturl.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllowedDomainsPostDto {
    @NotNull(message = "Domain should not be empty")
    private String allowedDomainName;
    @NotNull(message = "Enabled should not be empty")
    private boolean enabled;
}
