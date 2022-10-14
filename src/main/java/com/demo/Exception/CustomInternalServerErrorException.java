package com.demo.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
500ERROR 잡아주기
 */

public class CustomInternalServerErrorException extends RuntimeException{

    public CustomInternalServerErrorException(String message){
        super(message);
    }
}
