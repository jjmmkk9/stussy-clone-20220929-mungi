package com.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LogAop {

    /*
    어노테이션으로 설정해주는 이유는 내가 원하는 메소드에만! pointCut 해주고 싶을때
     */
    @Pointcut("@annotation(com.demo.aop.annotation.LogAspect)")
    private void annotationPointCut(){
    }
    @Pointcut("execution(* com.demo.controller.api.*.*(..))")// 맨 앞에 *은 리턴타입

    private void executionPointCut(){}

    @Around("annotationPointCut()")
    //우리 api 메소드에 항상 리턴이 있기 때문에 Object로 리턴 잡아준다.
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs(); //args = 매개변수 하나하나(bindingResult, dto등 다 들고 올 수 o)

        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature(); // 다운캐스팅해서 넣어줌
        String[] argNames = codeSignature.getParameterNames();

        StringBuilder argNameString = new StringBuilder();
        StringBuilder argDataString = new StringBuilder();

        for(int i = 0; i < args.length; i++){
            argNameString.append(argNames[i]);
            argDataString.append(args[i].toString());
           if(i != args.length -1){
                argNameString.append(", ");
                argDataString.append(", ");
            }
            log.info("매개변수 값 >> {}", args[i]);
        }

        log.info("메소드 호출 -- {}/{}({}) >> {}",

                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                argNameString.toString(),
                argDataString.toString()
                );
                                            //전
        Object result = joinPoint.proceed(); //실행 기준
                                            //후
        log.info("메소드 리턴 -- {}.{}({}) >> {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                argNameString.toString(),
                result
        );

        return result;
    }
}
