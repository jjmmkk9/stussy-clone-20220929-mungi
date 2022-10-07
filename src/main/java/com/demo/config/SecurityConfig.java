package com.demo.config;

import com.demo.service.PrincipalDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.RequestBody;

//설정 클래스로 ioc에 등록
@Configuration
//기존의 WebSecurityConfigurerAdapter 클래스를 해당 SecurityConfig로 대체하겠다.
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //원래 super.configure(http) 였는데 그게 부모속성 따르는 거니까 우리는 오버라이드 해줄거라서 지우고 작성
        http.csrf().disable();
        http.httpBasic().disable(); //우리는 베이직 로그인(alert 창) 비활성화하고 폼로그인하겠다.

        http.authorizeRequests()    //모든 요청에 대해
                .antMatchers("/test/**", "/index")//해당 주소 요청들은
                .authenticated()    //인증을 거쳐라
                .anyRequest()       //antMatchers 외에 다른 모든 요청들은
                .permitAll()        //모두 승인해라

                .and()              //그리고

                .formLogin()//폼로그인 방식으로 인증하겠돠
                .usernameParameter("email")// principalDetailsService의 email로 간다.
                .loginPage("/account/login")//해당 페이지에서 폼로그인 -> GET 요청
                .loginProcessingUrl("account/login")//로그인 로직(PrincipalDetailsService 의 loadUserByUsername을 호출하는 POST 요청
                                                    //security 에 자동으로 컨트롤러가 만들어지고 그 포스트 매핑이 account/login
                .defaultSuccessUrl("/index");//이전 페이지가 없을 때 돌아갈 곳 없으니까 로그인 하면 index 로 보내라
    }
}
