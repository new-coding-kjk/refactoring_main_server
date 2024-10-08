package com.example.refactoring_main.config.filter;

import com.example.refactoring_main.config.auth.CustomerDetails;
import com.example.refactoring_main.config.auth.CustomerDetailsService;
import com.example.refactoring_main.entity.Member;
import com.example.refactoring_main.jwt.JWTUtil;
import com.example.refactoring_main.type.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/login");  // 로그인 경로를 /api/login 으로 설정
    }

    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {


        try{
            log.info("################## 유저 페스워드 확인중!!!###################");
            ObjectMapper objectMapper = new ObjectMapper();
            Member member = objectMapper.readValue(req.getReader(), Member.class);
            log.info(member.toString());

            String username = member.getUsername();  // 요청에서 사용자 이름을 추출
            String password = member.getPassword();  // 요청에서 비밀번호를 추출


//            // 사용자 이름과 비밀번호로 UsernamePasswordAuthenticationToken 객체를 생성하여 인증 요청을 생성.
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
            SecurityContextHolder.getContext().setAuthentication(authToken);
            // AuthenticationManager 가 인증을 처리하고 결과를 반환.
            return authenticationManager.authenticate(authToken);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 인증된 사용자 정보(CustomUserDetails)에서 사용자 이름을 추출.
        CustomerDetails customerDetails = (CustomerDetails) authResult.getPrincipal();
        String username = customerDetails.getUsername();

        log.info(authResult.getAuthorities().toString());
        log.info("Authentication object: " + authResult.toString());
        log.info("Granted Authorities: " + authResult.getAuthorities());


        // 사용자 권한(roles)들을 가져와 첫 번째 권한을 role 변수에 저장.
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();


        GrantedAuthority auth = iterator.next();  // 권한 목록 중 첫 번째 권한을 가져옴
        Role role = Role.valueOf(auth.getAuthority());  // 해당 권한의 이름을 가져옴

        // JWTUtil을 사용해 JWT 토큰을 생성. 10시간 동안 유효한 토큰을 생성.
        String token = jwtUtil.createJwt(username, role, 60 * 60 * 10L);

        log.info("#######################token####################"+token);

        Cookie jwtCookie = new Cookie("token", token);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(60 * 60 * 1000);

        log.info("jwtCookie: " + jwtCookie.getValue());


        // 응답 헤더에 생성된 토큰을 추가하여 클라이언트에 전달.
        response.addHeader("Authorization", "Bearer " + token);


    }



}
