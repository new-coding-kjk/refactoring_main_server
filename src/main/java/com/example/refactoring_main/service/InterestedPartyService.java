package com.example.refactoring_main.service;

import com.example.refactoring_main.entity.InterestedParty;
import com.example.refactoring_main.repository.InterestedPartyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InterestedPartyService {

    private final InterestedPartyRepository interestedPartyRepository;

    // 그룹 참여 신청하기
    public InterestedParty save(InterestedParty interestedParty) {
        return interestedPartyRepository.save(interestedParty);
    }

    // 내 그룹 신청자 리스트 확인
    public List<InterestedParty> findAllByGroupId(Long groupId) {
        return interestedPartyRepository.findAllByGroupId(groupId);
    }
}
