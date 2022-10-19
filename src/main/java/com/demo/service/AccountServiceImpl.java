package com.demo.service;

import com.demo.Exception.CustomValidationException;
import com.demo.domain.User;
import com.demo.dto.account.RegisterReqDto;
import com.demo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
<<<<<<< HEAD

    /*
    중복이 아니면 true를
    중복이면 예외 CustomValidationException 를 생성하는 boolean 메소드
     */
    @Override
    public boolean checkDuplicateEmail(String email) {
        User user = accountRepository.findUserByEmail(email);                       //xml 에서 이메일 검사해서

        if(user != null) {                                                          //중복 이메일이면
            Map<String, String> errorMap = new HashMap<String, String>();           //에러 내용을 담아줄 map 생성
            errorMap.put("duplicateFlag", "이미 가입된 이메일입니다");                  // 에러 맵에 duplicateFlag put 하고
            throw new CustomValidationException("DuplicateEmail Error", errorMap);  //예외 발생 CustomValidationException에 메세지랑 에러맵 넣음
        }
        return true;                                                                //user != null 검사에 안 걸리면 중복이 아니라서 true를 반환
    }



    /*
    userEntity를 db에 save = 회원가입
    그리고 result 에 반영 레코드수를 받아 result가 0이 아니면 true를 반환하는 메소드
    근데 throw Exception은 왜 해줌? 오류 발생할수도 있어서????????????????????????????????/
     */
=======
    @Override
    public boolean checkDuplicateEmail(String email) {
        User user = accountRepository.findUserByEmail(email);
        if(user != null) {
            Map<String, String> errorMap = new HashMap<String, String>();
            errorMap.put("duplicateFlag", "이미 가입된 이메일입니다");
            throw new CustomValidationException("DuplicateEmail Error", errorMap);
        }
        return true;
    }

>>>>>>> af23d26 (AccountRepository, mappers, Service,)
    @Override
    public boolean register(RegisterReqDto registerReqDto) throws Exception {
        User userEntity = registerReqDto.toUserEntity();
        int result = accountRepository.save(userEntity);

        return result != 0;
    }
}