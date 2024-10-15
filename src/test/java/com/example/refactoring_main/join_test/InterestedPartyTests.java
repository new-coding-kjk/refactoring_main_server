package com.example.refactoring_main.join_test;

import com.example.refactoring_main.entity.InterestedParty;
import com.example.refactoring_main.repository.InterestedPartyRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(false)
public class InterestedPartyTests {
    @Autowired
    private InterestedPartyRepository interestedPartyRepository;

    @Test
    public void findInterestedParty() {
        List<InterestedParty> interestedParty = interestedPartyRepository.findAllByMemberId(3L);
        for (InterestedParty interestedParty1 : interestedParty) {
            System.out.println(interestedParty1.getGroup().toString());
            System.out.println(interestedParty1.getMember().toString());
        }
    }
}
