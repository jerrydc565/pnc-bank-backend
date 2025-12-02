package com.signup.fnc_bank.repository;


import com.signup.fnc_bank.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    // Check if email exists
    boolean existsByEmail(String email);

    // Find user by email (for login)
    User findByEmail(String email);


}
