package com.example.refactoring_main.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "Group_Entity")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;

    // 그룹의 리더 (1:1 관계)
    @OneToOne
    @JoinColumn(name = "leader_id")
    private Member leader;

    // 그룹의 멤버들 (1:N 관계)
    @OneToMany(mappedBy = "group")
    @JsonManagedReference  // 순환 참조 방지를 위해 사용
    private Set<Member> members;



}
