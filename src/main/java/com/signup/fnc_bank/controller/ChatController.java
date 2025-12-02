package com.signup.fnc_bank.controller;

import com.signup.fnc_bank.dto.ChatMessageRequest;
import com.signup.fnc_bank.model.ChatMessage;
import com.signup.fnc_bank.model.ChatSession;
import com.signup.fnc_bank.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:5173")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody ChatMessageRequest request) {
        try {
            ChatMessage message = chatService.sendMessage(
                    request.getUserId(),
                    request.getSender(),
                    request.getMessage(),
                    request.getUserName(),
                    request.getUserEmail()
            );
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/messages/{userId}")
    public ResponseEntity<List<ChatMessage>> getMessages(@PathVariable Integer userId) {
        List<ChatMessage> messages = chatService.getMessages(userId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<ChatSession>> getAllChatSessions() {
        List<ChatSession> sessions = chatService.getAllChatSessions();
        return ResponseEntity.ok(sessions);
    }

    @PostMapping("/markRead/{userId}")
    public ResponseEntity<?> markAsRead(@PathVariable Integer userId) {
        chatService.markAsRead(userId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Marked as read");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/session/{userId}")
    public ResponseEntity<?> deleteChat(@PathVariable Integer userId) {
        chatService.deleteChat(userId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Chat deleted");
        return ResponseEntity.ok(response);
    }
}