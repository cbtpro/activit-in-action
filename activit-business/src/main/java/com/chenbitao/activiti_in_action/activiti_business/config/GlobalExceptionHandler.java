package com.chenbitao.activiti_in_action.activiti_business.config;

import com.chenbitao.activiti_in_action.activiti_business.exception.BusinessException;
import com.chenbitao.activiti_in_action.activiti_business.vo.ApiResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Value("${spring.profiles.active:prod}")
    private String activeProfile;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponseBody<Object>> handleOrderException(BusinessException ex) {
        String trace = null;
        if ("dev".equalsIgnoreCase(activeProfile)) {
            trace = Arrays.toString(ex.getStackTrace());
        }

        ApiResponseBody<Object> response = ApiResponseBody.fail(
                400,
                "Order Calculation Failed",
                ex.getMessage(),
                trace);

        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseBody<Object>> handleException(Exception ex) {
        String trace = null;
        // 如果是dev,就可以返回堆栈信息，方便定位，其余情况都
        if ("dev".equalsIgnoreCase(activeProfile)) {
            trace = Arrays.toString(ex.getStackTrace());
        }

        ApiResponseBody<Object> response = ApiResponseBody.fail(
                500,
                "Internal Server Error",
                ex.getMessage(),
                trace);

        return ResponseEntity.status(500).body(response);
    }
}
