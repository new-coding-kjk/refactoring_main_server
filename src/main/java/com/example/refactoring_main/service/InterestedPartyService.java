package com.example.refactoring_main.service;

import com.example.refactoring_main.entity.InterestedParty;
import com.example.refactoring_main.repository.InterestedPartyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class InterestedPartyService {

    private final InterestedPartyRepository interestedPartyRepository;

    public InterestedParty save(InterestedParty interestedParty) {

        return interestedPartyRepository.save(interestedParty);
    }
}
