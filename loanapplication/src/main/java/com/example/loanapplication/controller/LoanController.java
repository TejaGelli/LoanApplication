package com.example.loanapplication.controller;

import com.example.loanapplication.entity.LoanEntity;
import com.example.loanapplication.exception.LoanApplicationNotFoundException;
import com.example.loanapplication.repository.LoanRepository;
import com.example.loanapplication.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/loan/application")
public class LoanController {

    @Autowired
    private LoanService loanService;
    @Autowired
    private LoanRepository loanRepository;

    @PostMapping("/apply")
    public ResponseEntity<LoanEntity> applyLoan(@RequestBody LoanEntity loanEntity) {
        LoanEntity savedLoanEntity = loanService.applyLoan(loanEntity);
        return new ResponseEntity<>(savedLoanEntity, HttpStatus.CREATED);
    }

    @GetMapping("/view")
    public ResponseEntity<List<LoanEntity>> viewAllLoanApplications() {
        List<LoanEntity> loanEntityList = loanService.viewAllLoanApplications();
        return new ResponseEntity<>(loanEntityList, HttpStatus.OK);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<LoanEntity> viewLoanApplicationById(@PathVariable int id) {
        try {
            LoanEntity loanEntity = loanService.viewLoanApplicationById(id);
            return new ResponseEntity<>(loanEntity, HttpStatus.OK);
        } catch (LoanApplicationNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/modify/income/{id}")
    public ResponseEntity<LoanEntity> updateIncome(@PathVariable int id, @RequestBody Map<String, Double> requestBody) {
        double annualIncome = requestBody.get("annualIncome");
        try {
            LoanEntity updatedLoanEntity = loanService.updateIncome(id, annualIncome);
            Optional<LoanEntity> optionalLoanEntity = loanRepository.findById(id);
            if (optionalLoanEntity.isPresent()) {
                LoanEntity retrievedLoanEntity = optionalLoanEntity.get();

                // Print or log the retrieved entity
                System.out.println("Retrieved Loan Entity: " + retrievedLoanEntity);
            }
            return new ResponseEntity<>(updatedLoanEntity, HttpStatus.OK);
        } catch (LoanApplicationNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}