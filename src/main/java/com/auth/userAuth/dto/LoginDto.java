package com.auth.userAuth.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;

@Data
@ToString
public class LoginDto {
    private String username;
    private String password;
}
