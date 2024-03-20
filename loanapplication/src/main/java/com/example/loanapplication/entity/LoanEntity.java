package com.example.loanapplication.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class LoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String sex;
    private LocalDate dob;
    private LocalDate applicationDate;
    private double annualIncome;
    private String address;
    private long pin;
    private String city;
    private String country;

    @Override
    public String toString() {
        return "LoanEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", sex='" + sex + '\'' +
                ", dob=" + dob +
                ", applicationDate=" + applicationDate +
                ", annualIncome=" + annualIncome +
                ", address='" + address + '\'' +
                ", pin=" + pin +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}