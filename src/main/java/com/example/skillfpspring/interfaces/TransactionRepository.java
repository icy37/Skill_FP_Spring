package com.example.skillfpspring.interfaces;

import com.example.skillfpspring.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}

