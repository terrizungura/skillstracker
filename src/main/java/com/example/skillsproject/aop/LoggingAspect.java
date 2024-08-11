package com.example.skillsproject.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.example.skillsproject..*(..))")
    public void servicePointCut() {

    }

    @Around("servicePointCut()")
    public Object logMethodExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

        log.info("Execution time of {}.{}() :: {} ms",
                proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                proceedingJoinPoint.getSignature().getName(), stopWatch.getTotalTimeMillis());

        return result;
    }

    @AfterThrowing(
            pointcut = "servicePointCut()",
            throwing = "exception")
    public void handleException(JoinPoint joinPoint, Exception exception) {

        log.error("Exception in {}.{}() with cause = {}",
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                exception.getMessage() != null ? exception.getMessage() : "NULL");
    }

}
