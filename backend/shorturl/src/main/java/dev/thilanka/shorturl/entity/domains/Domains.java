package dev.thilanka.shorturl.entity.domains;

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
@Document(collection = "domains")
public class Domains {
    @Id
    private String id;
    private String domainName;
    private boolean enabled;
    private boolean urlAllowed;
    private boolean emailAllowed;
}
