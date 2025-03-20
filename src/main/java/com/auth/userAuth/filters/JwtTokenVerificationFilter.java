package com.auth.userAuth.filters;

import com.auth.userAuth.entities.UserEntity;
import com.auth.userAuth.services.JwtService;
import com.auth.userAuth.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenVerificationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            final String tokenHeader = request.getHeader("Authorization");

            if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = tokenHeader.split("Bearer ")[1];

            Long userId = jwtService.getUserFromToken(token);

            if (userId != null || SecurityContextHolder.getContext().getAuthentication() == null) {
                UserEntity user = userService.getUserById(userId);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        } catch(Exception e){
            log.info(e.getLocalizedMessage());
        }

    }
}
