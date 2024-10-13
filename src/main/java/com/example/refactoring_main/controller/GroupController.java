package com.example.refactoring_main.controller;


import com.example.refactoring_main.entity.Group;
import com.example.refactoring_main.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GroupController {
    private final GroupService groupService;

    // 방 만들기
    @PostMapping("/api/group")
    public ResponseEntity<String> createGroup(@RequestBody Group group) {
        log.info("##### 파티 만들기 도착 #######");
        log.info(group.toString());

        groupService.createGroup(group);


        return ResponseEntity.ok("Group created successfully");
    }

    // 방 리스트 갖고 오기
    @GetMapping("/api/group/list/{page}")
    public Page<Group> getGroups(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable Integer page) {


       return groupService.findAllGroups(pageable);
    }



}
