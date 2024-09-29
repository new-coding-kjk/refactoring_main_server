package com.example.refactoring_main.repository;

import com.example.refactoring_main.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {
}
