package dev.thilanka.shorturl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
