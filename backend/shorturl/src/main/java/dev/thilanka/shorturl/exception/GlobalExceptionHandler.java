package dev.thilanka.shorturl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice   //-- HANDLE REST EXCEPTIONS (@ControllerAdvice + @ResponseBody)
public class GlobalExceptionHandler {

    //-- HANDLE ALL EXCEPTIONS OF Exception.class. This will be the default for Exceptions that are not defined below
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exception) {
        exception.printStackTrace();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse
                        .builder()
                        .customErrorCode(CustomErrorCodes.INTERNAL_SERVER_ERROR.getErrorCode())
                        .customErrorDescription("Internal Server Error. Contact System admin")
                        .error(exception.getMessage())
                        .build()
                );
    }

    //-- HANDLE RESOURCENOTFOUNDEXCEPTION (It's a Custom Exception)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(ResourceNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ExceptionResponse
                                .builder()
                                .customErrorCode(CustomErrorCodes.RESOURCE_NOT_FOUND.getErrorCode())
                                .customErrorDescription(CustomErrorCodes.RESOURCE_NOT_FOUND.getDescription())
                                .error(exception.getMessage())
                                .build()
                );
    }

    //-- HANDLE ResourceAlreadyExistsException
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleException(ResourceAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse
                                .builder()
                                .customErrorCode(CustomErrorCodes.RESOURCE_ALREADY_EXISTS.getErrorCode())
                                .customErrorDescription(CustomErrorCodes.RESOURCE_ALREADY_EXISTS.getDescription())
                                .error(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse
                                .builder()
                                .customErrorCode(CustomErrorCodes.INVALID_DETAILS.getErrorCode())
                                .customErrorDescription(CustomErrorCodes.INVALID_DETAILS.getDescription())
                                .error(exception.getMessage())
                                .build()
                );
    }

    //-- MethodArgumentNotValidException is used to handlle Validation Errors at @Valid annotation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exception) {

        //-- Assign multiple errors, for each validation error (Eg: username invalid, password invalid,...)
        Set<String> errors = new HashSet<>();

        //-- Assign all validation errors to a HashSet
        exception.getBindingResult()
                .getAllErrors()
                .forEach(e -> {
                    var errorMessage = e.getDefaultMessage();
                    errors.add(errorMessage);
                });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse
                        .builder()
                        .customErrorCode(CustomErrorCodes.INVALID_DETAILS.getErrorCode())
                        .customErrorDescription(CustomErrorCodes.INVALID_DETAILS.getDescription())
                        .validationErrors(errors)
                        .build()
                );
    }
}
