package com.signup.fnc_bank.controller;

import com.signup.fnc_bank.dto.AdminLoginRequest;
import com.signup.fnc_bank.dto.AdminLoginResponse;
import com.signup.fnc_bank.dto.AdminStats;
import com.signup.fnc_bank.model.Admin;
import com.signup.fnc_bank.model.Transaction;
import com.signup.fnc_bank.model.User;
import com.signup.fnc_bank.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminLoginRequest request) {
        Optional<Admin> admin = adminService.authenticateAdmin(
                request.getUsername(),
                request.getPassword()
        );

        if (admin.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }

        // Generate a simple token (use JWT in production!)
        String token = "admin-" + UUID.randomUUID().toString();

        AdminLoginResponse response = new AdminLoginResponse(
                token,
                admin.get().getId(),
                admin.get().getUsername()
        );

        return ResponseEntity.ok(response);
    }
    @GetMapping("/stats")
    public ResponseEntity<AdminStats> getStats(
            @RequestHeader(value = "Authorization", required = false) String token) {
        // In production, validate token here
        AdminStats stats = adminService.getStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(
            @RequestHeader(value = "Authorization", required = false) String token) {
        List<User> users = adminService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/transactions/pending")
    public ResponseEntity<List<Transaction>> getPendingTransactions(
            @RequestHeader(value = "Authorization", required = false) String token) {
        List<Transaction> transactions = adminService.getPendingTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/transactions/recent")
    public ResponseEntity<List<Transaction>> getRecentTransactions(
            @RequestHeader(value = "Authorization", required = false) String token) {
        List<Transaction> transactions = adminService.getRecentTransactions();
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/transactions/{id}/approve")
    public ResponseEntity<?> approveTransaction(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            // Extract adminId from token (simplified - use JWT in production)
            Long adminId = 1L; // Default admin

            Transaction transaction = adminService.approveTransaction(id, adminId);
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/transactions/{id}/reject")
    public ResponseEntity<?> rejectTransaction(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            Transaction transaction = adminService.rejectTransaction(id);
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
