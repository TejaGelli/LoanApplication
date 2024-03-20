package com.example.loanapplication.repository;

import com.example.loanapplication.entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<LoanEntity, Integer> {
}