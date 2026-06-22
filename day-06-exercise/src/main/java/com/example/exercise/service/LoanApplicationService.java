package com.example.exercise.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.exercise.dto.CreateLoanApplicationRequest;
import com.example.exercise.dto.LoanApplicationResponse;
import com.example.exercise.dto.RepaymentScheduleResponse;
import com.example.exercise.entity.CustomerEntity;
import com.example.exercise.entity.LoanApplicationEntity;
import com.example.exercise.entity.RepaymentScheduleEntity;
import com.example.exercise.enums.LoanStatus;
import com.example.exercise.exception.CustomerNotFoundException;
import com.example.exercise.exception.LoanApplicationNotFoundException;
import com.example.exercise.repository.CustomerRepository;
import com.example.exercise.repository.LoanApplicationRepository;
import com.example.exercise.repository.PaymentTransactionRepository;
import com.example.exercise.repository.RepaymentScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
public class LoanApplicationService {

    private final RepaymentScheduleService repaymentScheduleService;
    private final RepaymentScheduleRepository repaymentScheduleRespository;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final LoanApplicationRepository loanApplicationRepository;
    private final CustomerRepository customerRepository;

    public LoanApplicationService(LoanApplicationRepository loanApplicationRepository, CustomerRepository customerRepository,
        RepaymentScheduleService repaymentScheduleService, PaymentTransactionRepository paymentTransactionRepository,
        RepaymentScheduleRepository repaymentScheduleRespository) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.customerRepository = customerRepository;
        this.repaymentScheduleService = repaymentScheduleService;
        this.paymentTransactionRepository = paymentTransactionRepository;
        this.repaymentScheduleRespository = repaymentScheduleRespository;
    }

    // CREATE LOAN APP
    public LoanApplicationResponse createLoanApplication(CreateLoanApplicationRequest request) {
        CustomerEntity customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException());

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setCustomer(customer);
        loan.setLoanAmount(request.getLoanAmount());
        loan.setTenorMonth(request.getTenorMonth());
        loan.setPurpose(request.getPurpose());
        loan.setStatus(LoanStatus.SUBMITTED);
        loan.setCreatedAt(ZonedDateTime.now());
        loan.setUpdatedAt(ZonedDateTime.now());

        LoanApplicationEntity saved = loanApplicationRepository.save(loan);

        return toResponse(saved);
    }
    
    // GET ALL LOAN APP
    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getLoanApplication() {
        return loanApplicationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // GET BY ID
    @Transactional(readOnly = true)
    public LoanApplicationResponse getLoanApplicationById(Long id) {
        LoanApplicationEntity loan = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException(id));

        return toResponse(loan);
    }

    // SEARCH BY CUST ID
    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> searchByCustomerId(Long customerId) { 
        return loanApplicationRepository.findLoansByCustomerId(customerId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // SEARCH BY STATUS
    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> searchByStatus(LoanStatus status) {
        return loanApplicationRepository.findByStatus(status)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // APPROVE LOAN APP
    public LoanApplicationResponse approveLoanApplication(Long id) {
        LoanApplicationEntity loan = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException(id));

        loan.setStatus(LoanStatus.APPROVED);
        loan.setUpdatedAt(ZonedDateTime.now());
        LoanApplicationEntity updatedLoan = loanApplicationRepository.save(loan);

        repaymentScheduleService.createRepaymentSchedule(updatedLoan);

        return toResponse(updatedLoan);
    }

    // REJECT LOAN APP
    public LoanApplicationResponse rejectLoanApplication(Long id) {
        LoanApplicationEntity loan = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException(id));

        loan.setStatus(LoanStatus.REJECTED);
        loan.setUpdatedAt(ZonedDateTime.now());

        return toResponse(loanApplicationRepository.save(loan));
    }

    // SEARCH REPAYMENT BY LOAN ID
    // GET /api/v1/loan-applications/{loan_application_id}/repayment-schedules
    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> getRepaymentByLoanId(Long loanApplicationId){
        RepaymentScheduleService repaymentScheduleService = new RepaymentScheduleService(repaymentScheduleRespository, paymentTransactionRepository, loanApplicationRepository);
        return repaymentScheduleService.getRepaymentScheduleByLoanId(loanApplicationId);
    }

    // GET   /api/v1/customers/{customer_id}/loan-applications
    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getLoanApplicationsByCustomerId(Long customerId) {
        if(!customerRepository.existsById(customerId)) {
            throw new LoanApplicationNotFoundException(customerId);
        }

        List<LoanApplicationEntity> loanApplication = loanApplicationRepository.findLoansByCustomerId(customerId);
        return loanApplication
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private LoanApplicationResponse toResponse(LoanApplicationEntity loan) {
        LoanApplicationResponse response = new LoanApplicationResponse();
        response.setId(loan.getId());
        response.setLoanAmount(loan.getLoanAmount());
        response.setTenorMonth(loan.getTenorMonth());
        response.setPurpose(loan.getPurpose());
        response.setStatus(loan.getStatus());
        response.setCustomerId(loan.getCustomer().getId());
        response.setCreatedAt(loan.getCreatedAt());
        response.setUpdatedAt(loan.getUpdatedAt());

        return response;
    }
}
