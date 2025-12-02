package com.signup.fnc_bank.repository;

import com.signup.fnc_bank.model.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSession, Integer> {
    Optional<ChatSession> findByUserId(Integer userId);
    List<ChatSession> findAllByOrderByLastMessageTimeDesc();
}