package com.example.refactoring_main.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration  // 이 클래스는 Spring 에서 설정 클래스임을 나타내는 어노테이션으로, 빈(Bean) 정의를 위한 클래스임을 나타냅니다.
@Slf4j
public class CorsConfig {

    // CorsFilter 를 빈으로 등록하는 메서드
    @Bean  // 이 메서드를 통해 CorsFilter 객체를 Spring 컨테이너에 빈(Bean)으로 등록
    public CorsFilter corsFilter() {

        log.info("###############필터 작동####################");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);  // 쿠키나 인증 정보를 허용

        // 패턴 기반으로 IP를 허용
//        config.addAllowedOrigin("http://localhost:5501");

        List<String> allowedOrigins = new ArrayList<>();
        allowedOrigins.add("http://localhost:5501");
        allowedOrigins.add("http://localhost:5500");
        allowedOrigins.add("http://example.com");

//        config.addAllowedOrigins(allowedOrigins);
        config.setAllowedOrigins(allowedOrigins);

        config.setAllowedHeaders(Arrays.asList("*"));  // 모든 헤더 허용
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));  // 허용할 HTTP 메서드 설정

        config.setExposedHeaders(Arrays.asList("Authorization","Set-Cookie"));

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}

