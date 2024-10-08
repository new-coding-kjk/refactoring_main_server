package com.example.refactoring_main.config.auth;

import com.example.refactoring_main.dto.MemberDto;
import com.example.refactoring_main.entity.Member;
import com.example.refactoring_main.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자 조회 로직에서 재귀 호출이 발생하지 않도록 확인
        Member member = memberRepository.findByUsername(username);
        if (member == null) {
            throw new UsernameNotFoundException("User not found");
        }

        MemberDto memberDto = new MemberDto(member.getUsername(),member.getRole());

        log.info("#################로그인 성공 입니다#######################");
        return new CustomerDetails(member);
    }


}
