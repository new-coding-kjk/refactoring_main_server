package com.example.refactoring_main.jwt;

import com.example.refactoring_main.type.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JWTUtil {

    private SecretKey secretKey;


    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    // 토큰에서 "username" 클레임을 추출하는 메서드. JWT 토큰을 파싱하여 username 값을 반환.
    public String getUsername(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    // 토큰에서 "role" 클레임을 추출하는 메서드. JWT 토큰을 파싱하여 role 값을 반환.
    public String getRole(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    // 토큰의 유효 기간이 만료되었는지 확인하는 메서드. 토큰의 만료 날짜와 현재 날짜를 비교하여 만료 여부 반환.
    public Boolean isExpired(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    // 새로운 JWT 토큰을 생성하는 메서드. username, role, 만료 시간(ms)을 받아 JWT 토큰을 생성하고 반환.
    public String createJwt(String username, Role role, Long expiredMs){
        log.info(expiredMs.toString());

        return Jwts.builder()
                .claim("username", username)  // 사용자 이름을 클레임에 추가
                .claim("role", role)  // 역할을 클레임에 추가
                .issuedAt(new Date(System.currentTimeMillis()))  // 토큰 발행 시간 설정
                .expiration(new Date(System.currentTimeMillis() + expiredMs))  // 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256,secretKey)  // SecretKey로 서명하여 토큰 보호
                .compact();  // 최종적으로 JWT를 압축하여 문자열로 반환
    }



}
