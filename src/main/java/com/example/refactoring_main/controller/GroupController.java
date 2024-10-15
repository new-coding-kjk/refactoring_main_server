package com.example.refactoring_main.controller;


import com.example.refactoring_main.config.auth.CustomerDetails;
import com.example.refactoring_main.entity.Group;
import com.example.refactoring_main.entity.Member;
import com.example.refactoring_main.jwt.JWTUtil;
import com.example.refactoring_main.method.AuthoritiesMethod;
import com.example.refactoring_main.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GroupController {
    private final GroupService groupService;
    private final JWTUtil jwtUtil;

    // 그룹 만들기
    @PostMapping("/api/group")
    public ResponseEntity<Map<String, Object>> createGroup(@RequestBody Group group) {
        log.info("##### 파티 만들기 도착 #######");
        log.info(group.toString());
        Map<String, Object> result = new HashMap<>();


        Group findGroup = groupService.createGroup(group);

        Member member = findGroup.getMembers().stream().findFirst().get();
        log.info(member.toString());
        String token = jwtUtil.createJwt(member.getUsername(),member.getRole(),600 * 600 * 10L, member.getId(),member.getGender());
        jwtUtil.updateSecurityContext(member);

        result.put("message", "성공적으로 만들었습니다.");
        result.put("token", "Bearer "+token);


        return ResponseEntity.ok(result);
    }

    // 그룹 리스트 갖고 오기
    @GetMapping("/api/group/list/{page}")
    public Page<Group> getGroups(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable Integer page) {
        AuthoritiesMethod.checkAuthorities();

       return groupService.findAllGroups(pageable);
    }





}
