package com.example.refactoring_main.entity;

import com.example.refactoring_main.type.Gender;
import com.example.refactoring_main.type.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Data
@Table(name = "Member_Entity")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    private String name;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String provider;
    private String providerId;

    @CreationTimestamp
    private Timestamp createDate;


    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonBackReference
    @ToString.Exclude  // 순환 참조 방지
    @EqualsAndHashCode.Exclude
    private Group group;

    @Builder
    public Member(String username, String password, String name, Role role, Gender gender, String provider, String providerId, Timestamp createDate, Group group) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
        this.group = group;
    }

    public void addGroup(Group group) {
        this.group = group;
        if (!group.getMembers().contains(this)) {
            group.getMembers().add(this);
        }
    }


}
