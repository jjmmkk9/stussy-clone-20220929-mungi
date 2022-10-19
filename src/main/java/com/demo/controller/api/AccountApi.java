package com.demo.controller.api;

import com.demo.aop.annotation.LogAspect;
import com.demo.aop.annotation.ValidAspect;
import com.demo.dto.CMRespDto;
import com.demo.dto.account.RegisterReqDto;
import com.demo.dto.validation.ValidationSequence;
import com.demo.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
<<<<<<< HEAD
스프링 빈에만 AOP를 적용 가능 - component
=======
>>>>>>> af23d26 (AccountRepository, mappers, Service,)
처음 요청이 들어오는 곳  -> aop에서 이걸 낚아챔 -> 에러가 있으면 예외처리 되는데 생성된 예외를 -> handler가 낚아채서 handler에서
그 예외 응답을 해준다
====> RestControllerAdvice
 */
@Slf4j
@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountApi {

    private final AccountService accountService;
    @ValidAspect
    @LogAspect


    @PostMapping("/register")       //@Valid 해줘야 validation 사용하는 dto 가져올때 validation 체크함
    //@Validated 는 10-5 수업에서 sequence 잡아준거랑 관련 있음
    public ResponseEntity<?> register(@Validated(ValidationSequence.class) @RequestBody RegisterReqDto registerReqDto,
                                      BindingResult bindingResult) throws Exception//bindingResult 에 에러가 담김
    {
        accountService.checkDuplicateEmail(registerReqDto.getEmail()); //트루 여기서 false 되면 어떻게 됨? : throw new CustomValidationException("DuplicateEmail Error", errorMap);
        accountService.register(registerReqDto);    //트루 여기서 false 되면 어떻게 됨? 걍 false
        return ResponseEntity.ok().body(new CMRespDto<>(1, "Successfully registered", registerReqDto));

        }

    }

