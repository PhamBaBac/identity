package com.bacpham.identity_service.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    UNCATEGORIZED(0, "Uncategorized"),
    USER_NOT_FOUND(1001, "User not found"),
    USERNAME_ALREADY_EXISTS(1002, "Username already exists"),
    INVALID_DATE_OF_BIRTH(1003, "Invalid date of birth"),
    INVALID_PASSWORD(1004, "Invalid password"),
    INVALID_USERNAME(1005, "Invalid username"),
    INVALID_KEY(1006, "Invalid key"),
    INVALID_CREDENTIALS(1007, "Invalid credentials")

    ;
    int code;
    String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
