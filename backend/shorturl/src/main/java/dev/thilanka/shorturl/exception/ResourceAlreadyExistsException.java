package dev.thilanka.shorturl.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)     //-- RETURNS BAD_REQUEST
public class ResourceAlreadyExistsException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    public ResourceAlreadyExistsException(String fieldName, String fieldValue) {
        //-- 'Short Url' already exists with 'abcdef'
        super(String.format("%s already exists with %s", fieldName, fieldValue));
    }

    public ResourceAlreadyExistsException(String resourceName, String fieldName, String fieldValue) {
        //-- 'Long URL' already exists with 'Short URL': 'abcdef'
        super(String.format("%s already exists with %s: %s", resourceName, fieldName, fieldValue));
    }
}
