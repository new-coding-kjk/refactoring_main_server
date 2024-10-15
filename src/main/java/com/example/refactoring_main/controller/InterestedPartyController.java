package com.example.refactoring_main.controller;

import com.example.refactoring_main.entity.Group;
import com.example.refactoring_main.entity.InterestedParty;
import com.example.refactoring_main.service.GroupService;
import com.example.refactoring_main.service.InterestedPartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class InterestedPartyController {

    private final InterestedPartyService interestedPartyService;
    private final GroupService groupService;


    @PostMapping("/api/interested")
    public ResponseEntity<String> createInterestedParty(@RequestBody InterestedParty interestedParty) {


        InterestedParty result = interestedPartyService.save(interestedParty);

        log.info("$$$$$$$$$$$$$$$$$$$$$$$$$GROUP : {} ",result.getGroup().toString());
        log.info("$$$$$$$$$$$$$$$$$$$$$$$$$MEMBER : {} ",result.getMember().toString());
        return ResponseEntity.ok("참여 신청 되었습니다.");
    }

    @GetMapping("/api/interested/{memberId}")
    public ResponseEntity<List<InterestedParty>> selectInterestedParty(@PathVariable Long memberId) {
        Group group = groupService.findGroupByMembersId(memberId);

        List<InterestedParty> result = interestedPartyService.findAllByGroupId(group.getId());
        return ResponseEntity.ok(result);
    }
}
