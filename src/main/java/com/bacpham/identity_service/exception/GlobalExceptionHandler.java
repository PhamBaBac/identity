package com.bacpham.identity_service.exception;

import com.bacpham.identity_service.dto.request.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ErrorCode.UNCATEGORIZED.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<Void>> handleAppException(AppException appException) {
        ErrorCode errorCode = appException.getErrorCode();
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException e) {
       String enumKey = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
       try {
              errorCode = ErrorCode.valueOf(enumKey);
         } catch (IllegalArgumentException ignored) {
       }
          ApiResponse<Void> apiResponse = new ApiResponse<>();
          apiResponse.setCode(errorCode.getCode());
          apiResponse.setMessage(errorCode.getMessage());
          return ResponseEntity.badRequest().body(apiResponse);
    }
}
