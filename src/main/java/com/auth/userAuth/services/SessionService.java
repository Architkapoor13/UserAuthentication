package com.auth.userAuth.services;

import com.auth.userAuth.entities.SessionEntity;
import com.auth.userAuth.entities.UserEntity;
import com.auth.userAuth.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final static Integer sessionsPerUser = 2;

    private final SessionRepository sessionRepository;
    public void createSession(String token, UserEntity user){
        List<SessionEntity> sessions = sessionRepository.findByUserOrderByLastUsed(user);
        if(sessions.size() == sessionsPerUser){
            sessionRepository.delete(sessions.getLast());
        }
        SessionEntity newSession = new SessionEntity();
        newSession.setToken(token);
        newSession.setUser(user);
        sessionRepository.save(newSession);
    }

    public void verifySession(String token){
        List<SessionEntity> sessions = sessionRepository.findByToken(token);
        if(sessions.isEmpty()){
            throw new SessionAuthenticationException("refresh token not valid");
        }
    }
}
