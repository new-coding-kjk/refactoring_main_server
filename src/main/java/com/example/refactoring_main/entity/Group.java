package com.example.refactoring_main.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Entity
@Data
@Table(name = "Group_Entity")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;

    // 그룹의 멤버들 (1:N 관계)
    @OneToMany(mappedBy = "group")
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    private Set<Member> members;

    public void addMember(Member member) {
        this.members.add(member);
    }


}
