package com.signup.fnc_bank.service;

import com.signup.fnc_bank.model.ChatMessage;
import com.signup.fnc_bank.model.ChatSession;
import com.signup.fnc_bank.repository.ChatMessageRepository;
import com.signup.fnc_bank.repository.ChatSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatSessionRepository chatSessionRepository;

    @Transactional
    public ChatMessage sendMessage(Integer userId, String sender, String messageText,
                                   String userName, String userEmail) {
        // Save message
        ChatMessage message = new ChatMessage();
        message.setUserId(userId);
        message.setSender(sender);
        message.setMessageText(messageText);
        message.setTimestamp(LocalDateTime.now());
        message.setIsRead(false);

        ChatMessage savedMessage = chatMessageRepository.save(message);

        // Update or create chat session
        Optional<ChatSession> existingSession = chatSessionRepository.findByUserId(userId);
        ChatSession session;

        if (existingSession.isPresent()) {
            session = existingSession.get();
            session.setLastMessage(messageText);
            session.setLastMessageTime(LocalDateTime.now());
            session.setUserName(userName); // Update name in case it changed
            if (sender.equals("user")) {
                session.setUnreadCount(session.getUnreadCount() + 1);
            }
        } else {
            session = new ChatSession();
            session.setUserId(userId);
            session.setUserEmail(userEmail);
            session.setUserName(userName);
            session.setLastMessage(messageText);
            session.setLastMessageTime(LocalDateTime.now());
            session.setUnreadCount(sender.equals("user") ? 1 : 0);
            session.setCreatedAt(LocalDateTime.now());
        }

        chatSessionRepository.save(session);

        return savedMessage;
    }

    public List<ChatMessage> getMessages(Integer userId) {
        return chatMessageRepository.findByUserIdOrderByTimestampAsc(userId);
    }

    public List<ChatSession> getAllChatSessions() {
        return chatSessionRepository.findAllByOrderByLastMessageTimeDesc();
    }

    @Transactional
    public void markAsRead(Integer userId) {
        Optional<ChatSession> session = chatSessionRepository.findByUserId(userId);
        if (session.isPresent()) {
            ChatSession chatSession = session.get();
            chatSession.setUnreadCount(0);
            chatSessionRepository.save(chatSession);
        }

        // Mark all admin messages as read
        List<ChatMessage> unreadMessages = chatMessageRepository
                .findByUserIdAndIsReadFalseAndSender(userId, "admin");
        for (ChatMessage msg : unreadMessages) {
            msg.setIsRead(true);
        }
        chatMessageRepository.saveAll(unreadMessages);
    }

    public void deleteChat(Integer userId) {
        // Delete all messages
        List<ChatMessage> messages = chatMessageRepository.findByUserIdOrderByTimestampAsc(userId);
        chatMessageRepository.deleteAll(messages);

        // Delete session
        Optional<ChatSession> session = chatSessionRepository.findByUserId(userId);
        session.ifPresent(chatSession -> chatSessionRepository.delete(chatSession));
    }
}