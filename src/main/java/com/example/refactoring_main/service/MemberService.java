package com.example.refactoring_main.service;

import com.example.refactoring_main.entity.Member;
import com.example.refactoring_main.repository.MemberRepository;
import com.example.refactoring_main.type.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    // 회원 가입 서비스
    public Member save(Member member) {
        if(member == null) {
            return null;
        }

        log.info("##########MEMBER SAVED ###########");
        member.setRole(Role.ROLE_USER);
        member.setPassword(passwordEncoder.encode(member.getPassword()));


        return memberRepository.save(member);
    }

    // 아이디 중복 확인
    public boolean chcDuplicatedByUsername (String username){
        return memberRepository.existsByUsername(username);
    }

}
