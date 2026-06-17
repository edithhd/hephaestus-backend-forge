package com.example.training.model;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
 
    public Customer(Long id, String fullName, String email, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createdAt = ZonedDateTime.now();
        this.updatedAt = ZonedDateTime.now();
    }

    public void getDisplayName() {
        System.out.println("Customer: " + this.fullName);
    }
}