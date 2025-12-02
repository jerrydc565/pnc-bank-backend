package com.signup.fnc_bank.repository;

import com.signup.fnc_bank.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    List<ChatMessage> findByUserIdOrderByTimestampAsc(Integer userId);
    List<ChatMessage> findByUserIdAndIsReadFalseAndSender(Integer userId, String sender);
}