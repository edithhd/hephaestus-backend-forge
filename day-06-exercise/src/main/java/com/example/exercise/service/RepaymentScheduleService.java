package com.example.exercise.service;

import com.example.exercise.repository.LoanApplicationRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.exercise.dto.PaymentTransactionResponse;
import com.example.exercise.dto.RepaymentScheduleResponse;
import com.example.exercise.entity.LoanApplicationEntity;
import com.example.exercise.entity.RepaymentScheduleEntity;
import com.example.exercise.enums.ScheduleStatus;
import com.example.exercise.exception.LoanApplicationNotFoundException;
import com.example.exercise.exception.RepaymentScheduleNotFoundException;
import com.example.exercise.repository.PaymentTransactionRepository;
import com.example.exercise.repository.RepaymentScheduleRepository;

@Service
public class RepaymentScheduleService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private static final Logger log = LoggerFactory.getLogger(LoanApplicationService.class);

    @Value("${loan.interest.annual-rate:0.12}")
    private double annualInterestRate;

    public RepaymentScheduleService(RepaymentScheduleRepository repaymentScheduleRepository, PaymentTransactionRepository paymentTransactionRepository, LoanApplicationRepository loanApplicationRepository) {
        this.repaymentScheduleRepository = repaymentScheduleRepository;
        this.paymentTransactionRepository = paymentTransactionRepository;
        this.loanApplicationRepository = loanApplicationRepository;
    }

    @Transactional(readOnly = true)
    public RepaymentScheduleResponse getRepaymentScheduleById(Long id) {
        RepaymentScheduleEntity repaymentSchedule = repaymentScheduleRepository.findByIdWithLoanApplication(id)
            .orElseThrow(() -> new RepaymentScheduleNotFoundException(id));
        return toScheduleResponse(repaymentSchedule);
    }

    // GET  /api/v1/repayment-schedules/{repayment_schedule_id}/payment-transactions
    @Transactional(readOnly = true)
    public List<PaymentTransactionResponse> getScheduleIdPaymentTransaction(Long repaymentScheduleId) {
        PaymentTransactionService paymentTransactionService = new PaymentTransactionService(paymentTransactionRepository, repaymentScheduleRepository);
        return paymentTransactionService.getPaymentTransactionByRepaymentId(repaymentScheduleId);
    }
    
    public void createRepaymentSchedule (LoanApplicationEntity loanApplication) {
        int tenor = loanApplication.getTenorMonth();

        // untuk get loan amount
        BigDecimal loanAmount = loanApplication.getLoanAmount();

        // formula principal amount
        BigDecimal monthlyPrincipal = loanAmount.divide(BigDecimal.valueOf(tenor), 2, RoundingMode.HALF_UP);

        // formula interest rate
        BigDecimal monthlyInterestRate = BigDecimal.valueOf(annualInterestRate).divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);

        // formula interest amount
        BigDecimal monthlyInterest = monthlyInterestRate.multiply(monthlyPrincipal);

        // formula total amount
        BigDecimal monthlyTotalAmount = monthlyPrincipal.add(monthlyInterest);

        // formula due date
        ZonedDateTime nextDueDate = ZonedDateTime.now().plusMonths(1);

        for(int i = 1; i <= tenor; i++) {
            RepaymentScheduleEntity repaymentScheduleEntity = new RepaymentScheduleEntity();
            repaymentScheduleEntity.setLoanApplication(loanApplication);
            repaymentScheduleEntity.setInstallmentNumber(i);
            repaymentScheduleEntity.setDueDate(nextDueDate);
            repaymentScheduleEntity.setPrincipalAmount(monthlyPrincipal);
            repaymentScheduleEntity.setInterestAmount(monthlyInterestRate);
            repaymentScheduleEntity.setTotalAmount(monthlyTotalAmount);
            repaymentScheduleEntity.setStatus(ScheduleStatus.UNPAID);
            repaymentScheduleEntity.setCreatedAt(ZonedDateTime.now());
            repaymentScheduleEntity.setUpdatedAt(ZonedDateTime.now());

            repaymentScheduleRepository.save(repaymentScheduleEntity);
            log.info("event=repayment_schedule_created, repaymentScheduleId={}",
                repaymentScheduleEntity.getId());
            nextDueDate = nextDueDate.plusMonths(1);
        }
        
    }

    // GET /api/v1/loan-applications/{loan_application_id}/repayment-schedules
    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> getRepaymentScheduleByLoanId(Long loanApplicationId) {
        if(!loanApplicationRepository.existsById(loanApplicationId)) {
            throw new LoanApplicationNotFoundException(loanApplicationId);
        }

        List<RepaymentScheduleEntity> repaymentSchedule = repaymentScheduleRepository.findByLoanApplicationId(loanApplicationId);
        return repaymentSchedule
                .stream()
                .map(this::toScheduleResponse)
                .collect(Collectors.toList());
    }

    private RepaymentScheduleResponse toScheduleResponse(RepaymentScheduleEntity repaymentSchedule) {
        RepaymentScheduleResponse response = new RepaymentScheduleResponse();

        response.setId(repaymentSchedule.getId());
        response.setLoanApplicationId(repaymentSchedule.getLoanApplication().getId());
        response.setInstallmentNumber(repaymentSchedule.getInstallmentNumber());
        response.setDueDate(repaymentSchedule.getDueDate());
        response.setPrincipalAmount(repaymentSchedule.getPrincipalAmount());
        response.setInterestAmount(repaymentSchedule.getInterestAmount());
        response.setTotalAmount(repaymentSchedule.getTotalAmount());
        response.setStatus(repaymentSchedule.getStatus());
        response.setCreatedAt(repaymentSchedule.getCreatedAt());
        response.setUpdatedAt(repaymentSchedule.getUpdatedAt());

        return response;
    }
}
