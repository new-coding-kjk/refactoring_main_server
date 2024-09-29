package com.example.refactoring_main.entity;

import com.example.refactoring_main.type.Gender;
import com.example.refactoring_main.type.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@ToString
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    private String name;
    private String phone;
    private String email;
    private Role role;
    private Gender gender;


    @Builder
    public Member(String username, String password, String name , String phone, String email, Role role, Gender gender) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.gender = gender;
    }

}
