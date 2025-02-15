package com.bacpham.identity_service.exception;

public class AppException extends RuntimeException {
    private final ErrorCode errorCode;
    public AppException(ErrorCode errorCode) {
        super(errorCode != null ? errorCode.getMessage() : "Unknown error");
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
