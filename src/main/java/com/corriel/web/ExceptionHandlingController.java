package com.corriel.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlingController {

    private final Logger logger = LogManager.getLogger(getClass().getName());

    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason="Internal server error")
    @ExceptionHandler(Exception.class)
    public void handleError(Exception ex) {
        logger.error(ex.getMessage(), ex);
    }
}
