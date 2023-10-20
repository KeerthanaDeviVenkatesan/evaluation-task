package com.springboot.evaluationtask.repository;

import com.springboot.evaluationtask.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSession,Long> {


    UserSession findByUserId(Long id);

    void deleteByUserId(Long sessionId);
}
