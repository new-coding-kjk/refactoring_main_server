package com.example.refactoring_main.entity;

import com.example.refactoring_main.type.Gender;
import com.example.refactoring_main.type.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
    private Role role;
    private Gender gender;

    private String provider;
    private String providerId;

    @CreationTimestamp
    private Timestamp createDate;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;


    @Builder
    public Member(String username, String password, String name, Role role, Gender gender, String provider, Group group, String providerId, Timestamp createDate) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.provider = provider;
        this.providerId = providerId;
        this.group = group;
        this.createDate = createDate;
    }
}
