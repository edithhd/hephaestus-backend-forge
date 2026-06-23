package com.example.exercise.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.exercise.dto.CreateCustomerRequest;
import com.example.exercise.dto.CustomerResponse;
import com.example.exercise.dto.LoanApplicationResponse;
import com.example.exercise.dto.PatchCustomerRequest;
import com.example.exercise.dto.UpdateCustomerRequest;
import com.example.exercise.entity.LoanApplicationEntity;
import com.example.exercise.enums.Role;
import com.example.exercise.helper.ValidateTokenRoleHelper;
import com.example.exercise.service.AuthService;
import com.example.exercise.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "CustomerController", description = "API for customer transactions.")
public class CustomerController {
    private final CustomerService customerService;
    private final AuthService authService; 
    
    public CustomerController(CustomerService customerService, AuthService authService) {
        this.customerService = customerService;
        this.authService = authService;
    }

    @PostMapping
    @Operation(summary = "Create a new customer.", description = "Creates a new customers with details as shown.")
    @ApiResponse(responseCode = "201", description = "New customer created.")
    @ApiResponse(responseCode = "400", description = "Invalid user input.")
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CreateCustomerRequest request, 
            HttpServletRequest requestContext) {
        
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.STAFF);

        CustomerResponse response = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    @Operation(summary = "Get all customers.", description = "Shows all customer data.")
    @ApiResponse(responseCode = "200", description = "All customers retrieved successfully.")
    @ApiResponse(responseCode = "401", description = "Customer not found.")
    public ResponseEntity<List<CustomerResponse>> getCustomers(HttpServletRequest requestContext) {
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.APPROVER, Role.STAFF);

        return ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping("/search")
    @Operation(summary = "Get all customers.", description = "Shows all customer data.")
    @ApiResponse(responseCode = "200", description = "All customers retrieved successfully.")
    @ApiResponse(responseCode = "401", description = "Customer not found.")
    public ResponseEntity<List<CustomerResponse>> searchCustomer(@RequestParam(name = "name") String fullName, HttpServletRequest requestContext) {
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.APPROVER, Role.STAFF);
        
        List<CustomerResponse> responses;
        if(fullName != null){
            responses = customerService.searchCustomerByName(fullName);
            return ResponseEntity.ok().body(responses);
        }
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer data by id.", description = "Shows customer data based on id.")
    @ApiResponse(responseCode = "200", description = "Customer data retrieved successfully.")
    @ApiResponse(responseCode = "401", description = "Customer not found.")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id, HttpServletRequest requestContext) {
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.APPROVER, Role.STAFF);

        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    // @DeleteMapping("/{id}")
    // @Operation(summary = "Delete customer data.", description = "Deletes customer data based on id.")
    // @ApiResponse(responseCode = "200", description = "Customer data deleted successfully.")
    // @ApiResponse(responseCode = "401", description = "Customer not found.")
    // public ResponseEntity<CustomerResponse> deleteCustomerById(@PathVariable Long id, HttpServletRequest requestContext) {
    //     ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
    //     tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN);

    //     return ResponseEntity.ok(customerService.deleteCustomerById(id));
    // }

    // @PutMapping("/{id}")
    // @Operation(summary = "Update customer data.", description = "Updates all fields in customer data based on id.")
    // @ApiResponse(responseCode = "200", description = "All fields in customer data updated successfully.")
    // @ApiResponse(responseCode = "401", description = "Customer not found.")
    // public ResponseEntity<CustomerResponse> updateCustomerById(@PathVariable Long id, @Valid @RequestBody UpdateCustomerRequest request, HttpServletRequest requestContext) {
    //     ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
    //     tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN);

    //     return ResponseEntity.ok(customerService.updateCustomerById(id, request));
    // }

    // @PatchMapping("/{id}")
    // @Operation(summary = "Update customer data.", description = "Updates selected fields in customer data based on id.")
    // @ApiResponse(responseCode = "200", description = "Customer data updated successfully.")
    // @ApiResponse(responseCode = "401", description = "Customer not found.")
    // public ResponseEntity<CustomerResponse> patchCustomer(@PathVariable Long id, @Valid @RequestBody PatchCustomerRequest request, HttpServletRequest requestContext) {
    //     ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
    //     tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN);

    //     return ResponseEntity.ok(customerService.patchCustomer(id, request));
    // }

    // GET LOAN APP BY CUST ID
    // GET   /api/v1/customers/{customer_id}/loan-applications
    @GetMapping("/{id}/loan-applications")
    @Operation(summary = "Get customer data by id.", description = "Shows customer data based on id.")
    @ApiResponse(responseCode = "200", description = "Customer data retrieved successfully.")
    @ApiResponse(responseCode = "401", description = "Customer not found.")
    public ResponseEntity<List<LoanApplicationResponse>> getLoanByCustomerId(@PathVariable(name = "id") Long customerId, HttpServletRequest requestContext) {
        ValidateTokenRoleHelper tokenHelper = new ValidateTokenRoleHelper();
        tokenHelper.validateTokenRole(requestContext, authService.getUsers(), Role.ADMIN, Role.APPROVER, Role.STAFF);

        return ResponseEntity.ok(customerService.getCustomersLoanApplication(customerId));
    }
}