package com.example.refactoring_main.handler;

import com.example.refactoring_main.config.auth.CustomerDetails;
import com.example.refactoring_main.controller.MainController;
import com.example.refactoring_main.jwt.JWTUtil;
import com.example.refactoring_main.type.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2Handler implements AuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    private String tokenStr;



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 로그인 성공!");

        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        String username = customerDetails.getUsername();

        log.info(authentication.getAuthorities().toString());
        log.info("Authentication object: " + authentication.toString());
        log.info("Granted Authorities: " + authentication.getAuthorities());


        // 사용자 권한(roles)들을 가져와 첫 번째 권한을 role 변수에 저장.
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();


        GrantedAuthority auth = iterator.next();  // 권한 목록 중 첫 번째 권한을 가져옴
        Role role = Role.valueOf(auth.getAuthority());  // 해당 권한의 이름을 가져옴

        // JWTUtil을 사용해 JWT 토큰을 생성. 10시간 동안 유효한 토큰을 생성.
        String token = jwtUtil.createJwt(username, role, 60 * 60 * 10L);

        // 응답 헤더에 생성된 토큰을 추가하여 클라이언트에 전달.
//        response.addHeader("Authorization", "Bearer " + token);
//        tokenStr = token;

        Cookie jwtCookie = new Cookie("token", token);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(60 * 60 * 1000);
        response.addCookie(createCookie("Authorization", token));

        // 집
//        response.sendRedirect("http://192.168.35.239:5501/main.html");

        // 카페
        response.sendRedirect("http://localhost:5501/main.html");
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(false);

        return cookie;
    }
}
