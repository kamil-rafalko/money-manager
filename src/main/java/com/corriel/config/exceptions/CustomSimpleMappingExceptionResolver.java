package com.corriel.config.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;

public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {

    private static final String EXCEPTION_RESOLVER = "ExceptionResolver";

    private final Logger logger = LogManager.getLogger(EXCEPTION_RESOLVER);

    @Override
    protected void logException(Exception ex, HttpServletRequest request) {
        this.logger.error(ex.getMessage(), ex);
    }
}
