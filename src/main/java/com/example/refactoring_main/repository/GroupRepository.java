package com.example.refactoring_main.repository;

import com.example.refactoring_main.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
    public Boolean existsByLeader_Username(String username);

    public int deleteByLeader_Username(String username);
}
