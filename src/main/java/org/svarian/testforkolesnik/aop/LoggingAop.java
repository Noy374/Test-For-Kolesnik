package org.svarian.testforkolesnik.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class LoggingAop {


    @Before("execution(* org.svarian.testforkolesnik.web.rest.controller.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Method {} is about to be called with arguments: {}" , joinPoint.getSignature().getName() , joinPoint.getArgs());
    }

    @After("execution(* org.svarian.testforkolesnik.web.rest.controller.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        log.info("Method {} was called." , joinPoint.getSignature().getName());
    }

    @AfterReturning(value = "execution(* org.svarian.testforkolesnik.web.rest.controller.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Method {} completed successfully with result: {}" , joinPoint.getSignature().getName() , result);
    }

    @AfterThrowing(value = "execution(* org.svarian.testforkolesnik.web.rest.controller.*.*(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("Method {} threw exception: {}" , joinPoint.getSignature().getName() , exception.getMessage());
    }
}
