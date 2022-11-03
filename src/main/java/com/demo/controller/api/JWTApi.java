package com.demo.controller.api;

import com.demo.dto.CMRespDto;
import com.demo.util.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.Duration;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class JWTApi {

    private final JwtProvider jwtProvider;

    @PostMapping("/jwt/{username}")  //로그인 시 토큰 발급
    public ResponseEntity<?> createJwt(@PathVariable String username){

        String token = jwtProvider.createToken(username);
        Claims claims = jwtProvider.parseJwtToken(token);
//        Date now = new Date();
//
//        String jwt = Jwts.builder()
//                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)//header의 type을 jwt로 잡아라
//                .setIssuer("mungi")//발급자
//                .setIssuedAt(now)//발급 시간
//                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis()))//30분동안 유효
//                .claim("username", "mungi")
//                .claim("email", "whansrldid@naver.com")
//                .signWith(SignatureAlgorithm.HS256, "1234")
//                .compact();
//
//        jwt = "Bearer " + jwt;


        return ResponseEntity.ok(new CMRespDto<>(1, "jwt created",token));
    }

    @GetMapping("/jwt")
    public ResponseEntity<?> checkToken(@RequestHeader(value = HttpHeaders.AUTHORIZATION)String token){
        Claims claims = jwtProvider.parseJwtToken(token);
        return ResponseEntity.ok(new CMRespDto<>(1, "검증 완료", token));
    }

}
