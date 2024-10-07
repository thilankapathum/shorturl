package dev.thilanka.shorturl.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "allowed_domains")
public class AllowedDomains {
    @Id
    private String id;
    private String allowedDomainName;
    private boolean enabled;
}
