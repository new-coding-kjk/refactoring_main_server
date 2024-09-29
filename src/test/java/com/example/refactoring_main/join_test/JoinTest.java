package com.example.refactoring_main.join_test;

import com.example.refactoring_main.dto.MemberDTO;
import com.example.refactoring_main.entity.Member;
import com.example.refactoring_main.repository.MemberRepository;
import com.example.refactoring_main.service.MemberService;
import com.example.refactoring_main.type.Gender;
import com.example.refactoring_main.type.Role;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback(false)
public class JoinTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testJoin() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setGender(Gender.male);
        memberDTO.setRole(Role.ROLE_USER);
        memberDTO.setUsername("user");
        memberDTO.setPassword("1234");
        memberDTO.setEmail("user@gmail.com");
        memberDTO.setPhone("123456789");

        Member member = memberService.save(memberDTO);

        Member member1 = memberRepository.findById(member.getId()).get();

        System.out.println("##########Find Member is ###### : "+member1.toString());



    }
}
