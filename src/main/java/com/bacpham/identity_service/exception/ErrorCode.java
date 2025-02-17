package com.bacpham.identity_service.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    UNCATEGORIZED(9999, "Uncategorized", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_FOUND(1001, "User not found", HttpStatus.NOT_FOUND),
    USERNAME_ALREADY_EXISTS(1002, "Username already exists", HttpStatus.BAD_REQUEST),
    INVALID_DATE_OF_BIRTH(1003, "Invalid date of birth", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(1005, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1006, "Invalid key", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS(1007, "Invalid credentials", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED(1008, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1009, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1010, "Invalid date of birth {min}", HttpStatus.BAD_REQUEST),

    ;
    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
