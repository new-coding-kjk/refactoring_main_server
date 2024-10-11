package com.example.refactoring_main.controller;

import com.example.refactoring_main.entity.Member;
import com.example.refactoring_main.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;
    //
    @GetMapping("/main/{id}")
    public ResponseEntity<Map<String,String>> main(@PathVariable Long id) {
        Map<String, String> data = new HashMap<>();

        Member member = memberService.findById(id);
        data.put("name", member.getUsername());
        data.put("email", member.getRole().toString());

        log.info("##################MAIN####################");

        return ResponseEntity.ok(data);
    }

}
