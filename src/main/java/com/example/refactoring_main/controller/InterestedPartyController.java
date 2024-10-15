package com.example.refactoring_main.controller;

import com.example.refactoring_main.entity.InterestedParty;
import com.example.refactoring_main.service.InterestedPartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class InterestedPartyController {

    private final InterestedPartyService interestedPartyService;


    @PostMapping("/api/interested")
    public ResponseEntity<String> interestedParty(@RequestBody InterestedParty interestedParty) {

        log.info("###################참여하기 컨트롤러 도착############################");
        log.info("$$$$$$$$$$$$$$$$$$$$$$$$$GROUP : {} ",interestedParty.getGroup().toString());
        log.info("$$$$$$$$$$$$$$$$$$$$$$$$$MEMBER : {} ",interestedParty.getMember().toString());

        InterestedParty result = interestedPartyService.save(interestedParty);

        log.info("$$$$$$$$$$$$$$$$$$$$$$$$$GROUP : {} ",result.getGroup().toString());
        log.info("$$$$$$$$$$$$$$$$$$$$$$$$$MEMBER : {} ",result.getMember().toString());
        return ResponseEntity.ok("참여 신청 되었습니다.");
    }
}
