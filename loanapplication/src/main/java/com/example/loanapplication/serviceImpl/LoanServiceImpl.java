package com.example.loanapplication.serviceImpl;

import com.example.loanapplication.entity.LoanEntity;
import com.example.loanapplication.exception.LoanApplicationNotFoundException;
import com.example.loanapplication.repository.LoanRepository;
import com.example.loanapplication.service.LoanService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public LoanEntity applyLoan(LoanEntity loanEntity) {
        // Validate age and annual income
        if (!isValidAge(loanEntity.getDob()) || !isValidIncome(loanEntity.getAnnualIncome())) {
            throw new IllegalArgumentException("Invalid loan application. Please check age and annual income.");
        }

        // Set application date
        loanEntity.setApplicationDate(LocalDate.now());

        // Save loan application
        LoanEntity savedLoanEntity = loanRepository.save(loanEntity);

        // Display welcome message
        System.out.println("Welcome, " + savedLoanEntity.getFirstName() + " " + savedLoanEntity.getLastName() +
                "! Your loan application ID is: " + savedLoanEntity.getId());

        return savedLoanEntity;
    }

    @Override
    public List<LoanEntity> viewAllLoanApplications() {
        return loanRepository.findAll();
    }

    @Override
    public LoanEntity viewLoanApplicationById(int id) {
        Optional<LoanEntity> loanEntityOptional = loanRepository.findById(id);
        if (loanEntityOptional.isPresent()) {
            return loanEntityOptional.get();
        } else {
            throw new LoanApplicationNotFoundException("Loan application with ID " + id + " not found.");
        }
    }

    @Override
    public LoanEntity updateIncome(int id, double annualIncome) {
        Optional<LoanEntity> loanEntityOptional = loanRepository.findById(id);
        if (loanEntityOptional.isPresent()) {
            LoanEntity loanEntity = loanEntityOptional.get();
            loanEntity.setAnnualIncome(annualIncome);
            return loanRepository.save(loanEntity);
        } else {
            throw new LoanApplicationNotFoundException("Loan application with ID " + id + " not found.");
        }
    }

    // Helper methods
    private boolean isValidAge(LocalDate dob) {
        LocalDate eighteenYearsAgo = LocalDate.now().minusYears(18);
        return dob.isBefore(eighteenYearsAgo);
    }

    private boolean isValidIncome(double income) {
        return income >= 150000;
    }
}