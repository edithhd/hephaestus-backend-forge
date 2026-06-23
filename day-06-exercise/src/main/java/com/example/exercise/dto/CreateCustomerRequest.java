package com.example.exercise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateCustomerRequest {
    // @NotBlank
    @Size(min=3,max=100)
    @JsonProperty("full_name")
    private String fullName;

    // @NotBlank
    @Size(max=30)
    private String nik;

    // @NotBlank
    @Email
    private String email;

    // @NotBlank
    @Size(min = 10, max = 30)
    @JsonProperty("phone_number")
    private String phoneNumber;
}