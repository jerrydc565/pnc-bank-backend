package com.signup.fnc_bank.repository;

import com.signup.fnc_bank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.signup.fnc_bank.model.Transaction;
import com.signup.fnc_bank.model.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserIdOrderByCreatedAtDesc(Integer userId);
    List<Transaction> findByUserId(Long userId);
    List<Transaction> findByStatus(TransactionStatus status);
    List<Transaction> findTop50ByOrderByCreatedAtDesc();

    // ✅ ADD THIS - Only get APPROVED transactions for user history
    List<Transaction> findByUserIdAndStatusOrderByCreatedAtDesc(Integer userId, TransactionStatus status);
}


