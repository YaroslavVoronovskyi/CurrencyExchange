package com.gmail.voronovskyi.yaroslav.currencyexchange.controller.exception;

import org.hibernate.exception.ConstraintViolationException;

public class RestControllerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RestControllerException(String message) {
        super(message);
    }

    public RestControllerException(String message, ConstraintViolationException cause) {
        super(message, cause);
    }
}
