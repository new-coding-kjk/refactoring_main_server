package com.example.refactoring_main.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "InterestedParty_Entity")
@Data
public class InterestedParty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // Group과의 관계 설정
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;


}
