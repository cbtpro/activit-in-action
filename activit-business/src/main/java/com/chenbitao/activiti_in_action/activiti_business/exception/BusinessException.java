package com.chenbitao.activiti_in_action.activiti_business.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
