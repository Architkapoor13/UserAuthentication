package com.auth.userAuth.Controllers;

import com.auth.userAuth.dto.LoginDto;
import com.auth.userAuth.dto.LoginResponseDto;
import com.auth.userAuth.dto.SignUpDto;
import com.auth.userAuth.dto.UserDto;
import com.auth.userAuth.services.AuthService;
import com.auth.userAuth.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUpUser(@RequestBody SignUpDto signUpDto){
        return new ResponseEntity<>(userService.signUpUser(signUpDto),HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public LoginResponseDto loginUser(@RequestBody LoginDto loginDto){
        System.out.println(loginDto);
        return authService.loginUser(loginDto);
    }


}
