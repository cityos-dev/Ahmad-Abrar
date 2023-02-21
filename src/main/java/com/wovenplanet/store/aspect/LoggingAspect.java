package com.wovenplanet.store.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
        " || within(@org.springframework.stereotype.Service *)" +
        " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
    }
    
    /**
     * Pointcut that matches all Spring web packages.
     */
    @Pointcut("within(org.springframework.web..*)")
    public void springWebPointcut() {
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(com.wovenplanet.store.repository..*)" +
        " || within(com.wovenplanet.store.service..*)" +
        " || within(com.wovenplanet.store.controller..*)")
    public void applicationPackagePointcut() {
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut() && springWebPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    	
    	long startTime = System.currentTimeMillis();
    	if (log.isDebugEnabled()) {
	    	log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
	                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    	}
        long totalTime = System.currentTimeMillis() - startTime;
        try {
        	Object result = joinPoint.proceed();
        	if (log.isDebugEnabled()) {
	            log.debug("Exit: {}.{}() with result = {}.total time taken: {} ms", joinPoint.getSignature().getDeclaringTypeName(),
	                    joinPoint.getSignature().getName(), result, totalTime);
        	}
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }
}
