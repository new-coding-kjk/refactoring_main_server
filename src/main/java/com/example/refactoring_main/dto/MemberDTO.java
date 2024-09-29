package com.example.refactoring_main.dto;

import com.example.refactoring_main.type.Gender;
import com.example.refactoring_main.type.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDTO {
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private Role role;
    private Gender gender;

    public MemberDTO(String username, String password, String name, String phone, String email, Role role, Gender gender) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.gender = gender;
    }
}
