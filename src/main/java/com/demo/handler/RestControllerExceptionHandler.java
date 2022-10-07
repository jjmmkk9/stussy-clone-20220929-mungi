package com.demo.handler;

import com.demo.Exception.CustomValidationException;
import com.demo.dto.CMRespDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class) //ExceptionHandler 가 매개변수의 예외가 일어나면 예외를 낚아챔
    public ResponseEntity<?> validationErrorException(CustomValidationException e){
        //예외처리 응답
        return ResponseEntity
                .badRequest()
                .body(new CMRespDto<>(-1, e.getMessage(),e.getErrorMap()));
    }

}
