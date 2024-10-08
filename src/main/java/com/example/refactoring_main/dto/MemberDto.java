package com.example.refactoring_main.dto;

import com.example.refactoring_main.entity.Member;
import com.example.refactoring_main.type.Gender;
import com.example.refactoring_main.type.Role;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Member}
 */
@Value
@Data
public class MemberDto implements Serializable {
//    Long id;
    String username;
//    String name;
    Role role;
//    Gender gender;
}