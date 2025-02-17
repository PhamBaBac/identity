package com.bacpham.identity_service.exception;

import com.bacpham.identity_service.dto.request.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final String MIN_ATTRIBUTE = "min";
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error("Exception: ", e);
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ErrorCode.UNCATEGORIZED.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<Void>> handleAppException(AppException appException) {
        log.error("AppException: ", appException);
        ErrorCode errorCode = appException.getErrorCode();
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse<Void>> handlingAccessDeniedException(AccessDeniedException exception) {
        log.error("AccessDeniedException: ", exception);
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Void>> handlingValidation(MethodArgumentNotValidException exception) {
        String enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        Map<String, Object> attributes = null;
        try {
            errorCode = ErrorCode.valueOf(enumKey);

            var constraintViolation =
                    exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);

            @SuppressWarnings("unchecked")
            Map<String, Object> rawAttributes = constraintViolation.getConstraintDescriptor().getAttributes();
            attributes = rawAttributes;

            log.info(attributes.toString());

        } catch (IllegalArgumentException ignored) {

        }

        ApiResponse<Void> apiResponse = new ApiResponse<>();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(
                Objects.nonNull(attributes)
                        ? mapAttribute(errorCode.getMessage(), attributes)
                        : errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }
    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
    }
