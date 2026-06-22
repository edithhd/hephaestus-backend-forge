package com.example.exercise.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.example.exercise.enums.ScheduleStatus;
import com.example.exercise.enums.LoanStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentScheduleResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("loan_application_id")
    private Long loanApplicationId;

    @JsonProperty("installment_number")
    private int installmentNumber;

    @JsonProperty("due_date")
    private ZonedDateTime dueDate;

    @JsonProperty("principal_amount")
    private BigDecimal principalAmount;

    @JsonProperty("interest_amount")
    private BigDecimal interestAmount;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    @JsonProperty("status")
    private ScheduleStatus status; 

    @JsonProperty("created_at")
    @NotBlank
    private ZonedDateTime createdAt;

    @JsonProperty("updated_at")
    @NotBlank
    private ZonedDateTime updatedAt;
}
