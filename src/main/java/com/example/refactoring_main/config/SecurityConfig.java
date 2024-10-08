package com.example.refactoring_main.config;

import com.example.refactoring_main.config.filter.JwtFilter;
import com.example.refactoring_main.config.filter.LoginFilter;
import com.example.refactoring_main.controller.MainController;
import com.example.refactoring_main.handler.OAuth2Handler;
import com.example.refactoring_main.jwt.JWTUtil;
import com.example.refactoring_main.oauth.CustomerOAuth2MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CorsFilter corsFilter;

    private final CustomerOAuth2MemberService customerOAuth2MemberService;

    private final AuthenticationConfiguration authenticationConfiguration;

    private final OAuth2Handler oAuth2Handler;

    private final JWTUtil jwtUtil;


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(basic -> basic.disable());


        http
                .formLogin(formLogin -> formLogin.disable());

        http
                .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class)
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration),jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilter(corsFilter);

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/main").hasRole("USER")
                        .requestMatchers("/api/group").hasRole("USER")
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/login/oauth2/**").permitAll()
                        .anyRequest().permitAll());


//        http
//                .oauth2Login(oauth2 -> oauth2
//                        .successHandler(oAuth2Handler)
//                        .userInfoEndpoint()
//                        .userService(customerOAuth2MemberService));

        return http.build();
    }


}
