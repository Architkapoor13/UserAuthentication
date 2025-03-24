package com.auth.userAuth.Controllers;

import com.auth.userAuth.dto.LoginDto;
import com.auth.userAuth.dto.LoginResponseDto;
import com.auth.userAuth.dto.SignUpDto;
import com.auth.userAuth.dto.UserDto;
import com.auth.userAuth.services.AuthService;
import com.auth.userAuth.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse response){

        LoginResponseDto loginResponseDto = authService.loginUser(loginDto);
        Cookie cookie = new Cookie("refreshToken", loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDto);
    }

    @GetMapping("/refreshToken")
    public ResponseEntity<LoginResponseDto> refreshAccessToken(HttpServletRequest request) throws AuthenticationException {
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(cookie -> cookie.getValue())
                .orElseThrow(()->new AuthenticationException("refresh token not found"));

        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }


}
