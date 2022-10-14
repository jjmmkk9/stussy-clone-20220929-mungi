package com.demo.service.auth;

import com.demo.domain.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/*
UserDetails를 implements 해서 클라이언트에 반환할 UserDetails 객체 만들기
 */
@Getter
public class PrincipalDetails implements UserDetails {

    private User user; //데이터 베이스에서 가져온 user entity 객체가 여기로 들어온다.
                        //principalDetails 를 service에서 return으로 생성했을 때

    public PrincipalDetails(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {//getAuthorities = 권한
        //GrantedAuthority를 타고 들어가보면 interface인데 얘가 String을 반환하는 getAuthority 메소드를 가지고있다
        //interface가 메소드를 하나만 가지고있다는 것은 람다를 쓸수 있다는 것이다. 그래서 여기서 리턴되는 객체가
        //Collection 에 담긴 GrantedAuthority 이니까 우리가 컬렉션을 만들거다
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(); //ArrayList를 Collection으로 업캐스팅
        authorities.add(()->user.getRole().getRole());//getRole이 두번인 이유는 user 안에 Role 클래스 안에 String role을 가져온다 .
        return authorities;
    }

    @Override
    public String getPassword() { //이 메소드는 security 가 가져갈거래
        return user.getPassword(); //로그인 한 비밀번호(post요청)와 해당 email 검사를 한 user의
                                    //password 를 비교해서 동일한지 체크!
    }

    @Override
    public String getUsername() {//이 메소드는 security 가 가져갈거래
        return user.getUsername();//로그인 한 이메일
    }

    /*
    이 밑에 네개는 일단 true로 잡아주삼
    회사 정책에 따라 로직을 짜야된다.
     */
    @Override
    public boolean isAccountNonExpired() {//계정의 만료여부(계정 사용기간 만료)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {//계정의 잠김여부(관리자가 강제로 사용을 금함)
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {//비밀번호 만료여부(비밀번호 5회 이상 틀렸음 = fale로 잠김)
        return true;
    }

    @Override
    public boolean isEnabled() {//계정의 활성화 여부(휴면계정, 이메일 또는 전화번호 인증 필요)
        return true;
    }
}
