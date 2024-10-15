package com.example.refactoring_main.repository;

import com.example.refactoring_main.entity.InterestedParty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestedPartyRepository extends JpaRepository<InterestedParty, Long> {

    public List<InterestedParty> findAllByMemberId(Long memberId);

    public List<InterestedParty> findAllByGroupId(Long groupId);
}
