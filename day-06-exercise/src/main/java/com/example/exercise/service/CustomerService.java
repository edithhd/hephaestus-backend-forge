package com.example.exercise.service;

import com.example.exercise.repository.LoanApplicationRepository;
import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
// import org.springdoc.core.converters.models.SortAsQueryParam;
import org.springframework.stereotype.Service;

import com.example.exercise.dto.UpdateCustomerRequest;
import com.example.exercise.dto.CreateCustomerRequest;
import com.example.exercise.dto.CustomerResponse;
import com.example.exercise.dto.LoanApplicationResponse;
import com.example.exercise.dto.PatchCustomerRequest;
// import com.example.exercise.dto.PatchCustomerRequest;
// import com.example.exercise.dto.UpdateCustomerRequest;
import com.example.exercise.exception.CustomerNotFoundException;
import com.example.exercise.exception.DuplicateException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import com.example.exercise.entity.CustomerEntity;
import com.example.exercise.entity.LoanApplicationEntity;
import com.example.exercise.repository.CustomerRepository;

@Service
@Validated
public class CustomerService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final CustomerRepository customerRepository;
    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    CustomerService(LoanApplicationRepository loanApplicationRepository, CustomerRepository customerRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest request) {

        if (customerRepository.existsByNik(request.getNik())) {
            log.warn("event=validation_error, field_error=nik, error=duplicate");
            throw new DuplicateException("NIK already exists");
        }

        if (customerRepository.existsByEmail(request.getEmail())) {
            log.warn("event=validation_error, field_error=email, error=duplicate");
            throw new DuplicateException("Email already exists");
        }

        CustomerEntity customer = new CustomerEntity();
        customer.setNik(request.getNik());
        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setCreatedAt(ZonedDateTime.now());
        customer.setUpdatedAt(ZonedDateTime.now());

        CustomerEntity savedCustomer = customerRepository.save(customer);

        log.info( "event=customer_created, customerId={}, fullName={}",
        savedCustomer.getId(),
        savedCustomer.getFullName());

        return toResponse(savedCustomer);
    }

    @Transactional
    public List<CustomerResponse> getCustomers() {
        List<CustomerEntity> customers = null;
        try {
            customers = customerRepository.findAll();  
        } catch (Exception e) {
            throw new CustomerNotFoundException();
        }

        // Jika ada datanya, baru lakukan mapping ke Response DTO
        return customers.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public CustomerResponse getCustomerById(Long id) {

        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException());

        return toResponse(customer);
    }

    @Transactional
    public List<CustomerResponse> searchCustomerByName(String fullName) {
        List<CustomerEntity> customers = customerRepository.findByFullNameContainingIgnoreCase(fullName);
        if(customers.isEmpty())
            throw new CustomerNotFoundException();
        
        return customerRepository
                .findByFullNameContainingIgnoreCase(fullName)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public CustomerResponse deleteCustomerById(Long id) {

        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException());
                log.warn("event=validation_error, field_error=email, error=not_found");
        customerRepository.delete(customer);

        log.info( "Customer deleted. customerId={}, fullName={}, email={}, phoneNumber={}",
        customer.getId(),
        customer.getFullName(),
        customer.getEmail(),
        customer.getPhoneNumber());

        return toResponse(customer);
    }

    @Transactional
    public CustomerResponse updateCustomerById(
            Long id,
            UpdateCustomerRequest request) {

        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException());

        if (!customer.getEmail().equals(request.getEmail())
                && customerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateException("Email already exists");
        }

        if (!customer.getNik().equals(request.getNik())
                && customerRepository.existsByNik(request.getNik())) {
            throw new DuplicateException("NIK already exists");
        }

        customer.setNik(request.getNik());
        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());

        CustomerEntity updatedCustomer = customerRepository.save(customer);

        log.info( "Customer deleted. customerId={}, fullName={}",
        customer.getId(),
        customer.getFullName());

        return toResponse(updatedCustomer);
    }

    // // Optional
    // @Transactional
    // public CustomerResponse patchCustomer(
    //         Long id,
    //         @Valid PatchCustomerRequest request) {

    //     CustomerEntity customer = customerRepository.findById(id)
    //             .orElseThrow(() -> new CustomerNotFoundException());

    //     if (request.getNik() != null) {
    //         if (!customer.getNik().equals(request.getNik())
    //                 && customerRepository.existsByNik(request.getNik())) {
    //             throw new DuplicateException("NIK already exists");
    //         }
    //         customer.setNik(request.getNik());
    //     }

    //     if (request.getFullName() != null) {
    //         customer.setFullName(request.getFullName());
    //     }

    //     if (request.getEmail() != null) {
    //         if (!customer.getEmail().equals(request.getEmail())
    //                 && customerRepository.existsByEmail(request.getEmail())) {
    //             throw new IllegalArgumentException("Email already exists");
    //         }
    //         customer.setEmail(request.getEmail());
    //     }
    //     if (request.getPhoneNumber() != null) {
    //         customer.setPhoneNumber(request.getPhoneNumber());
    //     }
    //     CustomerEntity updatedCustomer = customerRepository.save(customer);

    //     log.info( "Customer data updated. customerId={}, email={}, phoneNumber={}",
    //     customer.getId(),
    //     customer.getEmail(),
    //     customer.getPhoneNumber());

    //     return toResponse(updatedCustomer);
    // }

    // GET LOAN APP BY CUST ID
    @Transactional
    public List<LoanApplicationResponse> getCustomersLoanApplication(Long id) {
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException());
                
        return loanApplicationRepository.findByCustomerId(id)
                .stream()
                .map(this::toLoanApplicationResponse)
                .toList();
    }

    private CustomerResponse toResponse(CustomerEntity customer) {
        CustomerResponse response = new CustomerResponse();

        response.setId(customer.getId());
        response.setNik(customer.getNik());
        response.setFullName(customer.getFullName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setCreatedAt(customer.getCreatedAt());
        response.setUpdatedAt(customer.getUpdatedAt());

        return response;
    }

    private LoanApplicationResponse toLoanApplicationResponse(LoanApplicationEntity loanApplication) {
        LoanApplicationResponse response = new LoanApplicationResponse();

        response.setId(loanApplication.getId());
        response.setCustomerId(loanApplication.getCustomer().getId());
        response.setLoanAmount(loanApplication.getLoanAmount());
        response.setTenorMonth(loanApplication.getTenorMonth());
        response.setPurpose(loanApplication.getPurpose());
        response.setStatus(loanApplication.getStatus());
        response.setCreatedAt(loanApplication.getCreatedAt());
        response.setUpdatedAt(loanApplication.getUpdatedAt());

        return response;
    }
}