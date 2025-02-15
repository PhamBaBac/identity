package com.bacpham.identity_service.exception;

public enum ErrorCode {
    UNCATEGORIZED(0, "Uncategorized"),
    USER_NOT_FOUND(1001, "User not found"),
    USERNAME_ALREADY_EXISTS(1002, "Username already exists"),
    INVALID_DATE_OF_BIRTH(1003, "Invalid date of birth"),
    INVALID_PASSWORD(1004, "Invalid password"),
    INVALID_USERNAME(1005, "Invalid username"),
    INVALID_KEY(1006, "Invalid key")

    ;
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
