package com.demo.config;

import com.demo.handler.auth.AuthFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
Configuration은 설정 객체 controller, repository, service등 과 같이 ioc에 등록
 */
@Configuration
//기존의 WebSecurityConfigurerAdapter 클래스를 해당 SecurityConfig로 대체하겠다.
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    //BCtyptPasswordEncoder는 원래 라이브러리에 있는 객체여서 우리가 ioc에 등록할 수 없다
    //Configuration의 객체들은 bean에 등록할수 있어서 BCryptPasswordEncoder를 생성해서 리턴하는
    //메소드에 @Bean을 달아서 ioc에 등록해준것
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //원래 super.configure(http) 였는데 그게 부모속성 따르는 거니까 우리는 오버라이드 해줄거라서 지우고 작성
        http.csrf().disable();
        http.httpBasic().disable(); //우리는 베이직 로그인(alert 창) 비활성화하고 폼로그인하겠다.

        http.authorizeRequests()    //모든 요청에 대해

                /*<<<<<<<<<<<<<<<<<<<PAGE>>>>>>>>>>>>>>>>>>>>>>*/
//                .antMatchers("/admin/**")//이런 요청들은
//                //.hasRole("ADMIN")//ROLE_ADMIN 부여 ROLE_USER, ROLE_ADMIN, ROLE_MANAGER 중에서
//                .access("hasRole('ADMIN') or hasRole('MANAGER')")//둘다 권한을 주고싶을때
                .antMatchers("/account")//해당 주소 요청은
                .access("hasRole('USER') or hasRole('ADMIN') or hasRole('MANAGER')")//USER나 ADMIN이나 MANAGER면 다 들어올수 있다.
                .antMatchers("/", "/index", "/collections/**")//해당 주소 요청은
                .permitAll()//모두 접근 가능 (회원 아니여도)
                .antMatchers("account/login", "account/register")
                .permitAll()

                /*<<<<<<<<<<<<<<<<<<<Resource>>>>>>>>>>>>>>>>>>>>>>*/
                .antMatchers("/static/**", "image/**")
                .permitAll()        //static 이랑 image 으로 들어오면 전부 승인해라

                /*<<<<<<<<<<<<<<<<<<<API>>>>>>>>>>>>>>>>>>>>>>*/
                .antMatchers("/api/account/register")
                .permitAll()

                .anyRequest()       //antMatchers 외에 다른 모든 요청들은
                .permitAll()        //수업때만 permitAll한다. 귀찮으니까
//                .denyAll()        //접근을 차단해라

                .and()              //그리고

                .formLogin()//폼로그인 방식으로 인증하겠돠
                .usernameParameter("email")// principalDetailsService의 email로 간다.
                .loginPage("/account/login")//해당 페이지에서 폼로그인 -> GET 요청
                .loginProcessingUrl("account/login")//로그인 로직(PrincipalDetailsService 의 loadUserByUsername을 호출하는 POST 요청
                                                    //security 에 자동으로 컨트롤러가 만들어지고 그 포스트 매핑이 account/login
                                                    //그래서 이 url의 post 요청이 들어오면 필터 걸어라 -> principalDetailsService로 전달
                .failureHandler(new AuthFailureHandler())//에러가 발생하면 이 핸들러로
                .defaultSuccessUrl("/index");//이전 페이지가 없을 때 돌아갈 곳 없으니까 로그인 하면 index 로 보내라
    }
}
