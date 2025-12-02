package com.signup.fnc_bank.service;

import com.signup.fnc_bank.dto.AdminStats;
import com.signup.fnc_bank.model.Admin;
import com.signup.fnc_bank.model.Transaction;
import com.signup.fnc_bank.model.User;
import com.signup.fnc_bank.model.TransactionStatus;
import com.signup.fnc_bank.model.TransactionType;
import com.signup.fnc_bank.repository.AdminRepository;
import com.signup.fnc_bank.repository.TransactionRepository;
import com.signup.fnc_bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<Admin> authenticateAdmin(String username, String password) {
        Optional<Admin> admin = adminRepository.findByUsername(username);
        if (admin.isPresent() && admin.get().getPasswordHash().equals(password)) {
            return admin;
        }
        return Optional.empty();
    }

    public AdminStats getStats() {
        long totalUsers = userRepository.count();
        long totalTransactions = transactionRepository.count();
        long activeAccounts = userRepository.count(); // All users are active for now
        long pendingTransactions = transactionRepository
                .findByStatus(TransactionStatus.PENDING).size();

        return new AdminStats(totalUsers, totalTransactions,
                activeAccounts, pendingTransactions);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Transaction> getPendingTransactions() {
        return transactionRepository.findByStatus(TransactionStatus.PENDING);
    }

    public List<Transaction> getRecentTransactions() {
        return transactionRepository.findTop50ByOrderByCreatedAtDesc();
    }

    @Transactional
    public Transaction approveTransaction(Long transactionId, Long adminId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new RuntimeException("Transaction is not pending");
        }

        // Get user and update balance NOW (when approved)
        User user = userRepository.findById(transaction.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (transaction.getTransactionType() == TransactionType.DEPOSIT) {
            user.setBalance(user.getBalance() + transaction.getAmount());
        } else if (transaction.getTransactionType() == TransactionType.WITHDRAW ||
                transaction.getTransactionType() == TransactionType.TRANSFER) {
            if (user.getBalance() < transaction.getAmount()) {
                throw new RuntimeException("Insufficient funds");
            }
            user.setBalance(user.getBalance() - transaction.getAmount());  // ✅ DEDUCT HERE
        }

        userRepository.save(user);

        // Update transaction status
        transaction.setStatus(TransactionStatus.APPROVED);
        transaction.setApprovedAt(LocalDateTime.now());
        transaction.setApprovedBy(adminId);
        transaction.setBalanceAfter(user.getBalance());  // ✅ UPDATE balance after deduction

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction rejectTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new RuntimeException("Transaction is not pending");
        }

        transaction.setStatus(TransactionStatus.REJECTED);
        transaction.setRejectedAt(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }
}
