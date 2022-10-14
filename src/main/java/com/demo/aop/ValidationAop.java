package com.demo.aop;

import com.demo.Exception.CustomValidationException;
import com.demo.dto.CMRespDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Component
@Aspect
public class ValidationAop {

    @Pointcut("@annotation(com.demo.aop.annotation.ValidAspect)")
    private void pointCut() {}

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        BeanPropertyBindingResult bindingResult = null;

        for (Object arg : args) {
            //args[i].getClass == BeanPropertyBindingResult.class -> instanceOf 랑 동일
            if (arg.getClass() == BeanPropertyBindingResult.class) { //-> joinPoint 로 가져온 args 가 BindingResult(오류들 가져오는)
                bindingResult = (BeanPropertyBindingResult) arg;
                break;
            }
        }
            //가져온 bindingResult 에서 에러가 있으면
        if(bindingResult.hasErrors()) {
            //에러맵을 만들어서
            Map<String, String> errorMap = new HashMap<String, String>();
            //에러를 반복(뽀이치)으로 하나씩 빼서 key: 오류난 필드 (error.getField()), value: 오류 메세지(error.getDefaultMessage())로 오류 하나씩 put 해줌
            bindingResult.getFieldErrors().forEach(error -> {
                errorMap.put(error.getField(), error.getDefaultMessage());

            });
            //에러맵 수집 다했으면  CustomValidationException(메시지, errorMap) throw 해줌
            throw new CustomValidationException("유효성 검사 실패: ", errorMap);

        }

    }
    @AfterReturning(value = "pointCut()", returning = "returnObj")
    public void afterReturn(JoinPoint joinPoint, Object returnObj) {
        log.info("Validation success: {}", returnObj);
    }//배낀거

}
