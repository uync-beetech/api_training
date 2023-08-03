package com.beetech.api_intern.common.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.beetech.api_intern..*Controller.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Received request: {}", joinPoint.getSignature());
        }
    }

    @AfterThrowing(pointcut = "execution(* com.beetech.api_intern..*Controller.*(..))", throwing = "ex")
    public void logError(Exception ex) {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("An error occurred: " + ex.getMessage(), ex);
        }
    }

    @AfterReturning(pointcut = "execution(* com.beetech.api_intern..*Controller.*(..))", returning = "result")
    public void logSuccess(Object result) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Method executed successfully with result: {}", result);
        }
    }
}
