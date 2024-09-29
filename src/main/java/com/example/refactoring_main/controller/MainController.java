package com.example.refactoring_main.controller;

import com.example.refactoring_main.dto.MemberDTO;
import com.example.refactoring_main.entity.Member;
import com.example.refactoring_main.repository.MemberRepository;
import com.example.refactoring_main.service.MemberService;
import com.example.refactoring_main.type.Gender;
import com.example.refactoring_main.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
//@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/join")
    public String saveJoin(@RequestBody MemberDTO memberDTO ){
        MemberDTO data = memberDTO;
        data.setRole(Role.ROLE_USER);
        System.out.println(data.toString());

        Member member = memberService.save(memberDTO);
        System.out.println(member.toString());

        if(member == null){
            return "회원 가입 실패";
        }
        return member.getId().toString();
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<Member> findMember(@PathVariable Long id) {
        // 요청으로 받은 ID 값을 출력하거나 로직에 사용할 수 있음
        Member memeber = memberRepository.findById(id).get();

        // 요청 처리 로직
        return ResponseEntity.ok(memeber);
    }


    @GetMapping("/join")
    public String join() {
        System.out.println("con join");
        return "hello join";
    }





}
