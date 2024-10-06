package dev.thilanka.shorturl.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)     //-- INCLUDE ONLY NON-EMPTY ATTRIBUTES
public class ExceptionResponse {
    //-- From CustomErrorCodes enum
    private int customErrorCode;

    //-- From CustomErrorCodes enum
    private String customErrorDescription;

    //-- Can include in Error Message at the throwing time
    private String error;

    private Set<String> validationErrors;
    private Map<String, String> errors;
}
