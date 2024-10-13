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
    private final MemberService memberService;

    // 방 만들기
    public Group createGroup(Group group) {
        log.info(group.getMembers().stream().findFirst().toString());

        Member member =  memberService.findById(group.getMembers().stream().findFirst().get().getId());
        member.setLeader(true);
        Group savedGroup = groupRepository.save(group);
        member.addGroup(savedGroup);
        group.addMember(member);
        
        return savedGroup;
    }


    // 방 리스트 갖고 오기
    public Page<Group> findAllGroups(Pageable pageable) {

        return groupRepository.findAll(pageable);
    }



}
