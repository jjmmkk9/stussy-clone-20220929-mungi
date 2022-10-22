package com.demo.handler.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
인증 실패 핸들러
AuthenticationFailureHandler 구현


 */
public class AuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                                                    //error 를 auth 라는 걸 보내줌

        response.sendRedirect("/account/login?error=auth"); //AccountPageController의 error 여기서 받는것.

    }
}
