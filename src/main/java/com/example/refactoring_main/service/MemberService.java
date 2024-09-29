package com.example.refactoring_main.service;

import com.example.refactoring_main.dto.MemberDTO;
import com.example.refactoring_main.entity.Member;
import com.example.refactoring_main.repository.MemberRepository;
import com.example.refactoring_main.type.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public Member save(MemberDTO memberDTO) {
        if(memberDTO == null) {
            return null;
        }
        Member member = Member.builder()
                .username(memberDTO.getUsername())
                .password(memberDTO.getPassword())
                .name(memberDTO.getName())
                .role(Role.ROLE_USER)
                .email(memberDTO.getEmail())
                .gender(memberDTO.getGender())
                .phone(memberDTO.getPhone())
                .build();

        System.out.println("##########MEMBER SAVED ###########");
        log.info(member.toString());

        memberRepository.save(member);

        return member;
    }


}
