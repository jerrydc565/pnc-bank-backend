package com.signup.fnc_bank.service;

import com.signup.fnc_bank.dto.*;
import com.signup.fnc_bank.model.*;
import com.signup.fnc_bank.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
    public class TransactionService {

        @Autowired
        private TransactionRepository transactionRepository;

        @Autowired
        private UserRepository userRepository; // Your existing repository

        @Transactional
        public Transaction createTransaction(Integer userId, TransactionType type,
                                             Double amount, String description) {
            User user = userRepository.findById(Integer.valueOf(userId))
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Validate transaction
            if (type == TransactionType.WITHDRAW && user.getBalance() < amount) {
                throw new RuntimeException("Insufficient balance");
            }

            // Update balance - handle null balance
            Double currentBalance = user.getBalance();
            if (currentBalance == null) {
                currentBalance = 0.0;
            }

            Double newBalance = currentBalance;



            if (type == TransactionType.DEPOSIT) {
                newBalance = currentBalance + amount;
            } else if (type == TransactionType.WITHDRAW || type == TransactionType.TRANSFER) {
                newBalance = currentBalance - amount;
            }

            user.setBalance(newBalance);
            userRepository.save(user);

            // Create transaction record
            Transaction transaction = new Transaction();
            transaction.setUserId(userId);
            transaction.setTransactionType(type);
            transaction.setAmount(amount);
            transaction.setDescription(description);
            transaction.setCreatedAt(LocalDateTime.now());
            transaction.setBalanceAfter(newBalance);

            return transactionRepository.save(transaction);
        }
    public List<Transaction> getUserTransactions(Integer userId) {
        // ✅ CHANGE THIS to only show APPROVED transactions
        return transactionRepository.findByUserIdAndStatusOrderByCreatedAtDesc(userId, TransactionStatus.APPROVED);
    }
// Just return transactions, don't look up user


        public Double getUserBalance(Integer userId) {
            User user = userRepository.findById((userId))
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return user.getBalance();
        }
    }

