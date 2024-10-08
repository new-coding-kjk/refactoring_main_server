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
        String username = group.getLeader().getUsername();
        groupService.createGroup(group);

        return ResponseEntity.ok("Group created successfully");
    }

    // 방 리스트 갖고 오기
    @GetMapping("/api/group/list/{page}")
    public Page<Group> getGroups(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable Integer page) {
        log.info("##### 컨트롤러 도착 #######");

       return groupService.findAllGroups(pageable);





    }


    // 내가 만든 방 체크
    @GetMapping("/api/group/leader/{username}")
    public ResponseEntity<Boolean> chcDuplicatedByUsername(@PathVariable String username) {
        return ResponseEntity.ok(groupService.existsGroupByUsername(username));
    }

    // 내가 만든 방 삭제
    @DeleteMapping("/api/group/{username}")
    public ResponseEntity<String> deleteResource(
            @PathVariable String username) {

        if(groupService.deleteGroupByUsername(username) > 0) return ResponseEntity.ok("삭제에 실패 했습니다.");

        return ResponseEntity.ok("성공적으로 삭제 되었습니다.");
    }
}
