package com.demo.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends HttpFilter {

//    @Override  쌤 오류나서 필터 지우고 요청으로 바꿈
//    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        if(!request.getRequestURI().contains("/api/auth/jwt") && !request.getRequestURI().contains("/static")){
//            String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
//            Claims claims = parseJWTToken(authorization);
//        }
//
//        super.doFilter(request, response, chain);
//
//    }
//                                //생성된 토큰 일로 컴온
//    public Claims parseJWTToken(String authorization){
//        validationAuthorizationHeader(authorization);
//        String jwtToken = extractToken(authorization);
//
//        return Jwts.parser()
//                .setSigningKey("1234")
//                .parseClaimsJwt(jwtToken)
//                .getBody();
//    }
//
//    private void validationAuthorizationHeader(String header) {
//        if(header == null || !header.startsWith("Bearer ")) {
//            throw new IllegalArgumentException(); //type 에러 = argument가 잘못되었다.
//        }
//    }
//
//    private String extractToken(String authorization) {
//        return authorization.substring("Bearer ".length());// = indexOf 토큰 생성할때 Bearer 라고 해줄거라서 그거 띠주는거
//    }
}
