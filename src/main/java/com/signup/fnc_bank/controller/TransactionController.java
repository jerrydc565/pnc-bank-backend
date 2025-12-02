package com.signup.fnc_bank.controller;

import com.signup.fnc_bank.dto.BalanceResponse;
import com.signup.fnc_bank.dto.TransactionRequest;
import com.signup.fnc_bank.dto.TransactionResponse;
import com.signup.fnc_bank.model.Transaction;
import com.signup.fnc_bank.model.TransactionStatus;
import com.signup.fnc_bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.signup.fnc_bank.model.TransactionStatus;
import com.signup.fnc_bank.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import com.signup.fnc_bank.model.User;
import com.signup.fnc_bank.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionRepository transactionRepository;  // ADD THIS

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/transactions")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequest request) {
        try {
            Transaction transaction = transactionService.createTransaction(
                    request.getUserId(),
                    request.getTransactionType(),
                    request.getAmount(),
                    request.getDescription()
            );

            // Convert to response DTO
            TransactionResponse response = new TransactionResponse(
                    transaction.getId(),
                    transaction.getUserId(),
                    transaction.getTransactionType(),
                    transaction.getAmount(),
                    transaction.getDescription(),
                    transaction.getCreatedAt(),
                    transaction.getBalanceAfter()
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/transactions/pending")
    public ResponseEntity<?> createPendingTransaction(@RequestBody Transaction transaction) {
        try {
            transaction.setStatus(TransactionStatus.PENDING);
            transaction.setCreatedAt(LocalDateTime.now());

            // For PENDING transactions, DON'T change the balance yet
            // Just record what the balance is NOW (before approval)
            User user = userRepository.findById(transaction.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Set balanceAfter to CURRENT balance (no deduction yet)
            transaction.setBalanceAfter(user.getBalance());

            Transaction saved = transactionRepository.save(transaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/transactions/{userId}")
    public ResponseEntity<List<TransactionResponse>> getUserTransactions(@PathVariable Integer userId) {
        List<Transaction> transactions = transactionService.getUserTransactions(userId);

        // Convert to response DTOs
        List<TransactionResponse> responses = transactions.stream()
                .map(t -> new TransactionResponse(
                        t.getId(),
                        t.getUserId(),
                        t.getTransactionType(),
                        t.getAmount(),
                        t.getDescription(),
                        t.getCreatedAt(),
                        t.getBalanceAfter()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }
    @GetMapping("/transactions/single/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable Long id) {
        try {
            Transaction transaction = transactionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Transaction not found"));
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}/balance")
    public ResponseEntity<BalanceResponse> getUserBalance(@PathVariable Integer userId) {
        Double balance = transactionService.getUserBalance(userId);
        return ResponseEntity.ok(new BalanceResponse(balance));
    }
}