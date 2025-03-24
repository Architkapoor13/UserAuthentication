package com.auth.userAuth.services;

import com.auth.userAuth.dto.LoginResponseDto;
import com.auth.userAuth.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecretKey;


    public String getAccessToken(UserEntity user){
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("username", user.getUsername())
                .claim("name", user.getName())
                .claim("roles", user.getAuthorities())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*5))
                .signWith(getSecretKey())
                .compact();

    }

    public String GetRefreshToken(UserEntity user){
        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L *60*60*24*30*6))
                .signWith(getSecretKey())
                .compact();
    }

    public Long getUserFromToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Long.valueOf(claims.getSubject());

    }



    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }
}
