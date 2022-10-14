package com.demo.service.auth;

import com.demo.domain.User;
import com.demo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
로그인 중
 */
@Slf4j
@Service
@RequiredArgsConstructor                        //security 의 UserDetailService 우리가 override 해서 사용
public class PrincipalDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override //UserDetails 가 로그인 되어진 사용자 정보 이걸 클라이언트 한테 실질적을로 반환
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = accountRepository.findUserByEmail(email); //db에 아이디가 있으면

        if (user == null) {
            log.error("아이디를 찾지 못함");
            throw new UsernameNotFoundException("존재하지 않는 아이디입니다.");
        }

        return new PrincipalDetails(user); //PrincipalDetails 를 생성
        //UserDetails를 반환 해줘야 하는데 얘가 인터페이스라서 구현을 해줘야한다.
        // 그래서 principalDetails 가 UserDetails 를 구현하고 있으니까 바로 프린시펄을 반환    }
    }
}



