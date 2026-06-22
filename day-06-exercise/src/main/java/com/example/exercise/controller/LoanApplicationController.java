package com.example.exercise.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.exercise.dto.CreateLoanApplicationRequest;
import com.example.exercise.dto.LoanApplicationResponse;
import com.example.exercise.dto.RepaymentScheduleResponse;
import com.example.exercise.enums.LoanStatus;
import com.example.exercise.enums.Role;
import com.example.exercise.helper.ValidateTokenRoleHelper;
import com.example.exercise.service.AuthService;
import com.example.exercise.service.LoanApplicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/loan-applications")
public class LoanApplicationController {
    private final LoanApplicationService loanApplicationService;
    private final AuthService authService; 
    
    public LoanApplicationController(LoanApplicationService loanAppService, AuthService authService) {
        this.loanApplicationService = loanAppService;
        this.authService = authService;
    }

    @PostMapping
    @Operation(summary = "Creates loan application.", description = "Creates new loan application.")
    @ApiResponse(responseCode = "200", description = "Loan application created successfully.")
    @ApiResponse(responseCode = "400", description = "Bad Request.")
    @ApiResponse(responseCode = "401", description = "Unauthorized.")
    @ApiResponse(responseCode = "403", description = "Forbidden.")
    public ResponseEntity<LoanApplicationResponse> createLoanApplication(@Valid @RequestBody CreateLoanApplicationRequest request, HttpServletRequest requestContext) { 
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.STAFF);

        LoanApplicationResponse response = loanApplicationService.createLoanApplication(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Shows loan applications.", description = "Displays all loan applications data.")
    @ApiResponse(responseCode = "200", description = "Loan applications data retrieved successfully.")
    @ApiResponse(responseCode = "500", description = "Internal server error.")
    public ResponseEntity<List<LoanApplicationResponse>> getLoanApplication(@RequestParam(required = false, name = "status") LoanStatus status,
    @RequestParam(required = false) Long id, HttpServletRequest requestContext) {
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.STAFF, Role.APPROVER);
        
        List<LoanApplicationResponse> responses;
        if(status != null){
            responses = loanApplicationService.searchByStatus(status);
            return ResponseEntity.ok().body(responses);
        } else if(id != null) {
            responses = loanApplicationService.searchByCustomerId(id);
            return ResponseEntity.ok().body(responses);
        }
        return ResponseEntity.ok(loanApplicationService.getLoanApplication());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Shows loan application by ID.", description = "Displays loan application data based on ID.")
    @ApiResponse(responseCode = "200", description = "Loan application data retrieved successfully.")
    @ApiResponse(responseCode = "404", description = "Loan application not found.")
    @ApiResponse(responseCode = "500", description = "Internal server error.")
    public ResponseEntity<LoanApplicationResponse> getLoanApplicationById(@PathVariable Long id, HttpServletRequest requestContext) {
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.STAFF, Role.APPROVER);
        
        return ResponseEntity.ok(loanApplicationService.getLoanApplicationById(id));
    }

    @PatchMapping("/{id}/approve")
    @Operation(summary = "Approves loan application.", description = "Updates loan applications status to APPROVED.")
    @ApiResponse(responseCode = "200", description = "Loan application data updated to APPROVED successfully.")
    @ApiResponse(responseCode = "403", description = "Forbidden.")
    @ApiResponse(responseCode = "404", description = "Loan application not found.")
    @ApiResponse(responseCode = "500", description = "Internal server error.")
    public ResponseEntity<LoanApplicationResponse> approveLoanApplication(@PathVariable Long id, HttpServletRequest requestContext) {
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.APPROVER);

        return ResponseEntity.ok(loanApplicationService.approveLoanApplication(id));
    }

    @PatchMapping("/{id}/reject")
    @Operation(summary = "Rejects loan application.", description = "Updates loan applications status to REJECTED.")
    @ApiResponse(responseCode = "200", description = "Loan application data updated to REJECTED successfully.")
    @ApiResponse(responseCode = "403", description = "Forbidden.")
    @ApiResponse(responseCode = "404", description = "Loan application not found.")
    @ApiResponse(responseCode = "500", description = "Internal server error.")
    public ResponseEntity<LoanApplicationResponse> rejectLoanApplication(@PathVariable Long id, HttpServletRequest requestContext) {
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.APPROVER);

        return ResponseEntity.ok(loanApplicationService.rejectLoanApplication(id));
    }

    // GET REPAYMENT BY LOAN ID
    @GetMapping("/{id}/repayment-schedules")
    @Operation(summary = "Shows repayment schedule by loan application ID.", description = "Displays repayment schedule data based on loan application ID.")
    @ApiResponse(responseCode = "200", description = "Loan application data retrieved successfully.")
    @ApiResponse(responseCode = "404", description = "Loan application not found.")
    @ApiResponse(responseCode = "500", description = "Internal server error.")
    public ResponseEntity<List<RepaymentScheduleResponse>> getRepaymentByLoanId(@PathVariable Long id, HttpServletRequest requestContext) {
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.STAFF, Role.APPROVER);
        
        return ResponseEntity.ok(loanApplicationService.getRepaymentByLoanId(id));
    }

}
