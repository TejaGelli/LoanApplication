package com.example.loanapplication.service;

import com.example.loanapplication.entity.LoanEntity;
import com.example.loanapplication.exception.LoanApplicationNotFoundException;
import com.example.loanapplication.repository.LoanRepository;
import com.example.loanapplication.serviceImpl.LoanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class LoanServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testApplyLoan_ValidInput() {
        // Arrange
        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setFirstName("John");
        loanEntity.setLastName("Doe");
        loanEntity.setSex("Male");
        loanEntity.setDob(LocalDate.of(1990, 1, 1));
        loanEntity.setAnnualIncome(200000);
        loanEntity.setAddress("123 Main St");
        loanEntity.setPin(12345);
        loanEntity.setCity("New York");
        loanEntity.setCountry("USA");

        when(loanRepository.save(any(LoanEntity.class))).thenReturn(loanEntity);

        // Act
        LoanEntity savedLoanEntity = loanService.applyLoan(loanEntity);

        // Assert
        assertNotNull(savedLoanEntity);
        assertNotNull(savedLoanEntity.getApplicationDate());
        assertNotNull(savedLoanEntity.getId());
    }

    @Test
    void testApplyLoan_InvalidAge() {
        // Arrange
        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setDob(LocalDate.now());
        loanEntity.setAnnualIncome(200000);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> loanService.applyLoan(loanEntity));
    }

    @Test
    void testApplyLoan_InvalidIncome() {
        // Arrange
        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setDob(LocalDate.of(1990, 1, 1));
        loanEntity.setAnnualIncome(100000);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> loanService.applyLoan(loanEntity));
    }

    @Test
    void testUpdateIncome_ValidInput() {
        // Arrange
        int loanId = 1;
        double newIncome = 250000;
        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setId(loanId);
        loanEntity.setAnnualIncome(200000);

        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loanEntity));
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(loanEntity);

        // Act
        LoanEntity updatedLoanEntity = loanService.updateIncome(loanId, newIncome);

        // Assert
        assertNotNull(updatedLoanEntity);
        assertEquals(newIncome, updatedLoanEntity.getAnnualIncome());
    }

    @Test
    void testUpdateIncome_InvalidId() {
        // Arrange
        int loanId = 1;
        double newIncome = 250000;

        when(loanRepository.findById(loanId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(LoanApplicationNotFoundException.class, () -> loanService.updateIncome(loanId, newIncome));
    }

    @Test
    void testApplyLoan_ValidInput_DisplaysWelcomeMessage() {
        // Arrange
        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setFirstName("John");
        loanEntity.setLastName("Doe");
        loanEntity.setSex("Male");
        loanEntity.setDob(LocalDate.of(1990, 1, 1));
        loanEntity.setAnnualIncome(200000);
        loanEntity.setAddress("123 Main St");
        loanEntity.setPin(12345);
        loanEntity.setCity("New York");
        loanEntity.setCountry("USA");

        LoanEntity savedLoanEntity = new LoanEntity();
        savedLoanEntity.setId(1);
        savedLoanEntity.setFirstName("John");
        savedLoanEntity.setLastName("Doe");
        savedLoanEntity.setSex("Male");
        savedLoanEntity.setDob(LocalDate.of(1990, 1, 1));
        savedLoanEntity.setAnnualIncome(200000);
        savedLoanEntity.setAddress("123 Main St");
        savedLoanEntity.setPin(12345);
        savedLoanEntity.setCity("New York");
        savedLoanEntity.setCountry("USA");
        savedLoanEntity.setApplicationDate(LocalDate.now());

        when(loanRepository.save(any(LoanEntity.class))).thenReturn(savedLoanEntity);

        // Act
        LoanEntity result = loanService.applyLoan(loanEntity);

        // Assert
        assertNotNull(result);
        assertEquals(savedLoanEntity, result);
        // Add additional assertions to verify the welcome message is displayed
    }

    @Test
    void testApplyLoan_InvalidInput_DoesNotPersistData() {
        // Arrange
        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setDob(LocalDate.now());
        loanEntity.setAnnualIncome(100000);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> loanService.applyLoan(loanEntity));
        // Add additional assertions to verify that no data is persisted in the database
    }
}