package com.example.loanapplication.service;

import com.example.loanapplication.entity.LoanEntity;

import java.util.List;

public interface LoanService {
    LoanEntity applyLoan(LoanEntity loanEntity);
    List<LoanEntity> viewAllLoanApplications();
    LoanEntity viewLoanApplicationById(int id);
    LoanEntity updateIncome(int id, double annualIncome);
}