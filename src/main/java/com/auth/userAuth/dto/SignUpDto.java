package com.auth.userAuth.dto;

import com.auth.userAuth.entities.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignUpDto {

    private String name;
    private String username;
    private String password;
    private Set<Roles> roles;
}
