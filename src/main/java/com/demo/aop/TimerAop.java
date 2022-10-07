package com.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/*
메소드 실행 시간을 계산해주는 로직
호출되고 리턴될때까지 얼마나 걸리는지
 */
@Slf4j
@Aspect
@Component
public class TimerAop {

    @Pointcut("execution(* com.demo.controller..*.*(..))")
    public void executionPointCut(){
        
    }
    
    @Around("executionPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();

        Object result = joinPoint.proceed();

        stopwatch.stop();
        log.info("class: {}, method: {} >>>> {}" ,
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                stopwatch.getTotalTimeSeconds());

        return result;
    }
}
