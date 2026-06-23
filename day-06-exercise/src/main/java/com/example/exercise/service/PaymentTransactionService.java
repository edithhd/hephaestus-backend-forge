package com.example.exercise.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.exercise.dto.CreatePaymentTransactionRequest;
import com.example.exercise.dto.PaymentTransactionResponse;
import com.example.exercise.entity.PaymentTransactionEntity;
import com.example.exercise.entity.RepaymentScheduleEntity;
import com.example.exercise.enums.ScheduleStatus;
import com.example.exercise.exception.PaymentTransactionNotFoundException;
import com.example.exercise.repository.PaymentTransactionRepository;
import com.example.exercise.repository.RepaymentScheduleRepository;

@Service
public class PaymentTransactionService {

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private static final Logger log = LoggerFactory.getLogger(LoanApplicationService.class);

    public PaymentTransactionService(PaymentTransactionRepository paymentTransactionRepository, RepaymentScheduleRepository repaymentScheduleRepository) {
        this.paymentTransactionRepository = paymentTransactionRepository;
        this.repaymentScheduleRepository = repaymentScheduleRepository;
    }

    @Transactional
    public PaymentTransactionResponse createPaymentTransaction(CreatePaymentTransactionRequest request) {
        
        RepaymentScheduleEntity repaymentSchedule =
            repaymentScheduleRepository.findById(request.getRepaymentScheduleId())
                .orElseThrow(() -> new RuntimeException("Repayment schedule not found"));

        PaymentTransactionEntity paymentTransaction = new PaymentTransactionEntity();
        paymentTransaction.setPaymentReference(request.getPaymentReference());
        paymentTransaction.setRepaymentSchedule(repaymentSchedule);
        paymentTransaction.setPaidAmount(request.getPaidAmount());
        paymentTransaction.setPaidAt(ZonedDateTime.now());
        paymentTransaction.setStatus("SUCCESS");
        paymentTransaction.setCreatedAt(ZonedDateTime.now());
        paymentTransaction.setUpdatedAt(ZonedDateTime.now());

        PaymentTransactionEntity savedPaymentTransaction = paymentTransactionRepository.save(paymentTransaction);

        repaymentSchedule.setStatus(ScheduleStatus.PAID);
        repaymentScheduleRepository.save(repaymentSchedule);

        log.info( "event=payment_transaction_created, paymentTransaction={}",
        savedPaymentTransaction.getId());

        return toResponse(savedPaymentTransaction);
    }

    @Transactional(readOnly = true)
    public List<PaymentTransactionResponse> getPaymentTransactionByRepaymentId(Long repaymentScheduleId) {
        if(!repaymentScheduleRepository.existsById(repaymentScheduleId)) {
            throw new PaymentTransactionNotFoundException(repaymentScheduleId);
        }

        List<PaymentTransactionEntity> paymentTransaction = paymentTransactionRepository.findByRepaymentScheduleId(repaymentScheduleId);
            return paymentTransaction
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private PaymentTransactionResponse toResponse(PaymentTransactionEntity paymentTransaction) {
        PaymentTransactionResponse response = new PaymentTransactionResponse();

        response.setId(paymentTransaction.getId());
        response.setPaymentReference(paymentTransaction.getPaymentReference());
        response.setPaidAmount(paymentTransaction.getPaidAmount());
        response.setPaidAt(paymentTransaction.getPaidAt());
        response.setStatus(paymentTransaction.getStatus());
        response.setCreatedAt(paymentTransaction.getCreatedAt());
        response.setUpdatedAt(paymentTransaction.getUpdatedAt());

        return response;
    }

}
