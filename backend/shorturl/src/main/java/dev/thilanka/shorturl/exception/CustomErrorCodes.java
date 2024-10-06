package dev.thilanka.shorturl.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum CustomErrorCodes {

    NO_CODE(0, HttpStatus.NOT_IMPLEMENTED, "No Code"),
    RESOURCE_NOT_FOUND(404, HttpStatus.NOT_FOUND, "Resource Not found"),
    RESOURCE_ALREADY_EXISTS(400, HttpStatus.BAD_REQUEST, "Resource Already exists"),
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    INVALID_DETAILS(400, HttpStatus.BAD_REQUEST, "Invalid Details");

    @Getter
    private final int errorCode;
    @Getter
    private final HttpStatus httpStatus;
    @Getter
    private final String description;

    CustomErrorCodes(int errorCode, HttpStatus httpStatus, String description) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
