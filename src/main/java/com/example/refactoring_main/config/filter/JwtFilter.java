package com.example.refactoring_main.config.filter;

import com.example.refactoring_main.dto.MemberDto;
import com.example.refactoring_main.jwt.JWTUtil;
import com.example.refactoring_main.type.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.refactoring_main.entity.Member;
import com.example.refactoring_main.config.auth.CustomerDetails;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = null;
        String authHeader = request.getHeader("Authorization");
        Cookie[] cookies = request.getCookies();

        String username;
        Role role;

        log.info("$$$$$$$$$$$$$$$$$$$JWT 필터 도착$$$$$$$$$$$$$$$$$$$");


//      쿠키가 null이 아닌지 먼저 확인
        if (cookies != null && cookies.length > 1) {  // 쿠키가 존재하는지 확인
            for (Cookie cookie : cookies) {
                log.info(cookie.getName());
                if (cookie.getName().equals("Authorization")) {
                    authorization = cookie.getValue();
                }
            }
        } else if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authorization = authHeader.substring(7);  // "Bearer " 이후의 토큰 부분만 추출
        } else {
            // Authorization 헤더도 없고 쿠키도 없는 경우 필터 체인을 통과시킴
            filterChain.doFilter(request, response);
            return;
        }

        //Authorization 헤더 검증
        if (authorization == null) {
            log.info("token null");
            filterChain.doFilter(request, response);
            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        //토큰
        String token = authorization;
//        String token = authorization.split(" ")[1];

        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            log.info("token expired");
//            filterChain.doFilter(request, response);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        //토큰에서 username과 role 획득
        username = jwtUtil.getUsername(token);
        role = Role.valueOf(jwtUtil.getRole(token));

        //userDTO를 생성하여 값 set
        log.info("#$$#$#$#$#$$#$#$아이디랑 권한 :"+username + " " + role);
        Member member = new Member();
        member.setUsername(username);
        member.setRole(role);


        //UserDetails에 회원 정보 객체 담기
        CustomerDetails customOAuth2User = new CustomerDetails(member);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("#####authentication : " + authentication);
        System.out.println("######principal : " + authentication.getPrincipal());

        filterChain.doFilter(request, response);



    }
}
