package com.example.refactoring_main.service;

import com.example.refactoring_main.entity.Group;
import com.example.refactoring_main.entity.Member;
import com.example.refactoring_main.repository.GroupRepository;
import com.example.refactoring_main.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    // 방 만들기
    public Group createGroup(Group group) {
        Member member = memberRepository.findByUsername(group.getLeader().getUsername());
        if (member == null) {
            throw new RuntimeException("Member is null");
        }

        member.setGroup(group);
        group.setLeader(member);

        memberRepository.save(member);
        return groupRepository.save(group);
    }
    // 내가 만든 방 존재 하는지 확인
    public Boolean existsGroupByUsername(String username) {
        return  groupRepository.existsByLeader_Username(username);
    }

    // 방 리스트 갖고 오기
    public Page<Group> findAllGroups(Pageable pageable) {

        return groupRepository.findAll(pageable);
    }

    // 내가 만든 방 삭제
    public int deleteGroupByUsername(String username) {
        return groupRepository.deleteByLeader_Username(username);
    }

}
