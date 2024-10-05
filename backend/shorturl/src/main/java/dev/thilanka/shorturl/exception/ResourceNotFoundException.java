package dev.thilanka.shorturl.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)   //-- RETURNS HTTP NOT FOUND EXCEPTION
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public ResourceNotFoundException(String message){
        super(message);
    }

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue){

        //-- 'LongURL' is not found with 'shortUrl': 'abc123'
        super(String.format("%s is not found with %s: %s", resourceName,fieldName,fieldValue));

        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
