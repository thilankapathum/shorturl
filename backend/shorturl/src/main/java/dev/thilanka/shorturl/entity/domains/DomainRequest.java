package dev.thilanka.shorturl.entity.domains;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DomainRequest {
    @NotNull(message = "Domain should not be empty")
    private String domainName;
    @NotNull(message = "Enabled should not be empty")
    private boolean enabled;
    private boolean urlAllowed;
    private boolean emailAllowed;
}
