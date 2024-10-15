package com.example.refactoring_main.repository;

import com.example.refactoring_main.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    public Optional<Group> findByMembersId(Long memberId);
}
