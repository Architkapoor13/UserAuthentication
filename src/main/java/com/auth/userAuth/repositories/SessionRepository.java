package com.auth.userAuth.repositories;

import com.auth.userAuth.entities.SessionEntity;
import com.auth.userAuth.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    List<SessionEntity> findByUserOrderByLastUsed(UserEntity user);
    List<SessionEntity> findByToken(String token);
}
