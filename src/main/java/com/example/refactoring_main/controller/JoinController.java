package com.example.refactoring_main.controller;

import com.example.refactoring_main.entity.Member;
import com.example.refactoring_main.repository.MemberRepository;
import com.example.refactoring_main.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class JoinController {

    private final MemberService memberService;

    @PostMapping("/api/user/sign_up")
    public ResponseEntity<String> saveJoin(@RequestBody Member member){

        log.info("####################### 회원 가입 컨트롤러 도착 ########################");

        if(memberService.chcDuplicatedByUsername(member.getUsername())){
            return ResponseEntity.ok("이미 존재하는 아이디 입니다.");
        }

        Member result = memberService.save(member);

        if(result == null){
            return ResponseEntity.ok("회원가입 실패");
        }
        log.info("####################### 회원 가입 성공 ########################");
        return ResponseEntity.ok("회원 가입이 완료 되었습니다.");
    }

    @GetMapping("/api/user/duplicated/{username}")
    public ResponseEntity<Boolean> chcDuplicatedByUsername(@PathVariable String username) {
        return ResponseEntity.ok(memberService.chcDuplicatedByUsername(username));
    }


    @GetMapping("/join")
    public String join() {
        log.info("서버와 연결 되었는지 확인하는 요청");
        return "서버와 연결이 되어 있습니다.";
    }





}
