package com.auth.userAuth.services;

import com.auth.userAuth.dto.LoginDto;
import com.auth.userAuth.dto.LoginResponseDto;
import com.auth.userAuth.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public LoginResponseDto loginUser(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );
        UserEntity user = (UserEntity) authentication.getPrincipal();
        String token = jwtService.getAccessToken(user);
        String refreshToken = jwtService.GetRefreshToken(user);
        return new LoginResponseDto(user.getId(), token, refreshToken);
    }

    public LoginResponseDto refreshToken(String refreshToken){
        Long userId = jwtService.getUserFromToken(refreshToken);
        UserEntity user = userService.getUserById(userId);
        String newAccessToken = jwtService.getAccessToken(user);
        return new LoginResponseDto(userId, newAccessToken, refreshToken);
    }
}
