package com.example.training.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class PatchCustomerRequest {
    @Getter
    @Setter
    @Size(min=3,max=100)
    @JsonProperty("full_name")
    private String fullName;

    @Getter
    @Setter
    @Email
    private String email;

    @Getter
    @Setter
    @Size(min = 10, max = 13)
    @JsonProperty("phone_number")
    private String phoneNumber;
}
