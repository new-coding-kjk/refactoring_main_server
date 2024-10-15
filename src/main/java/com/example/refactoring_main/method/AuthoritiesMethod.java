package com.example.refactoring_main.method;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthoritiesMethod {
    public static void checkAuthorities(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            System.out.println("Name: " + authentication.getName()); // 사용자 이름
            System.out.println("Authorities: " + authentication.getAuthorities()); // 권한 목록
            System.out.println("Is Authenticated: " + authentication.isAuthenticated()); // 인증 여부
        } else {
            System.out.println("No authentication information found.!!");
        }
    }
}
