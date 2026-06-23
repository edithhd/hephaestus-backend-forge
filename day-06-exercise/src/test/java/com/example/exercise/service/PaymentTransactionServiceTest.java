package com.example.exercise.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.exercise.dto.CreatePaymentTransactionRequest;
import com.example.exercise.dto.PaymentTransactionResponse;
import com.example.exercise.entity.PaymentTransactionEntity;
import com.example.exercise.entity.RepaymentScheduleEntity;
import com.example.exercise.enums.ScheduleStatus;
import com.example.exercise.exception.PaymentTransactionNotFoundException;
import com.example.exercise.repository.PaymentTransactionRepository;
import com.example.exercise.repository.RepaymentScheduleRepository;

import org.mockito.InjectMocks;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
public class PaymentTransactionServiceTest {
    @Mock
    private PaymentTransactionRepository paymentTransactionRepository;
    @Mock
    private RepaymentScheduleRepository repaymentScheduleRepository;

    @InjectMocks
    private PaymentTransactionService paymentTransactionService;

    // create payment transaction
    @Test
    void should_create_payment_transaction() {
        Long scheduleId = 1L;

        RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();
        schedule.setId(scheduleId);
        schedule.setStatus(ScheduleStatus.UNPAID);

        CreatePaymentTransactionRequest request = new CreatePaymentTransactionRequest();
        request.setRepaymentScheduleId(scheduleId);
        request.setPaymentReference("PAY-001");
        request.setPaidAmount(BigDecimal.valueOf(1_000_000));

        when(repaymentScheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));

        when(paymentTransactionRepository.save(any(PaymentTransactionEntity.class))).thenAnswer(invocation -> {
                    PaymentTransactionEntity entity = invocation.getArgument(0);
                    entity.setId(10L);
                    return entity;});

        when(repaymentScheduleRepository.save(any(RepaymentScheduleEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PaymentTransactionResponse response = paymentTransactionService.createPaymentTransaction(request);

        assertNotNull(response);

        verify(repaymentScheduleRepository).findById(scheduleId);
        verify(paymentTransactionRepository).save(any(PaymentTransactionEntity.class));
        verify(repaymentScheduleRepository).save(schedule);
    }

    // create payment transaction but repayment schedule not found
    @Test
    void should_throw_exception_when_repayment_schedule_not_found() {

        Long scheduleId = 99L;

        CreatePaymentTransactionRequest request = new CreatePaymentTransactionRequest();
        request.setRepaymentScheduleId(scheduleId);
        request.setPaymentReference("PAY-001");
        request.setPaidAmount(BigDecimal.valueOf(1_000_000));

        when(repaymentScheduleRepository.findById(scheduleId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> paymentTransactionService.createPaymentTransaction(request));

        assertEquals("Repayment schedule not found", exception.getMessage());

        verify(repaymentScheduleRepository).findById(scheduleId);
        verify(paymentTransactionRepository, never()).save(any());
    }

    // get payment transaction by repayment id
    @Test
    void should_get_payment_transactions_by_repayment_schedule_id() {

        Long repaymentId = 1L;

        RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();
        schedule.setId(repaymentId);

        PaymentTransactionEntity tx = new PaymentTransactionEntity();
        tx.setId(10L);
        tx.setPaymentReference("PAY-001");
        tx.setPaidAmount(BigDecimal.valueOf(1_000_000));
        tx.setPaidAt(ZonedDateTime.now());
        tx.setStatus("SUCCESS");
        tx.setRepaymentSchedule(schedule);
        tx.setCreatedAt(ZonedDateTime.now());
        tx.setUpdatedAt(ZonedDateTime.now());

        when(repaymentScheduleRepository.existsById(repaymentId)).thenReturn(true);
        when(paymentTransactionRepository.findByRepaymentScheduleId(repaymentId)).thenReturn(List.of(tx));

        List<PaymentTransactionResponse> responses = paymentTransactionService.getPaymentTransactionByRepaymentId(repaymentId);

        assertNotNull(responses);
        assertEquals(1, responses.size());

        verify(repaymentScheduleRepository).existsById(repaymentId);
        verify(paymentTransactionRepository).findByRepaymentScheduleId(repaymentId);
    }

    // get payment transaction by repayment id (repayment not found)
    @Test
    void should_throw_exception_when_repayment_schedule_for_payment_transaction_not_found() {

        Long repaymentId = 99L;

        when(repaymentScheduleRepository.existsById(repaymentId)) .thenReturn(false);

        assertThrows(PaymentTransactionNotFoundException.class,
                () -> paymentTransactionService.getPaymentTransactionByRepaymentId(repaymentId)
        );

        verify(repaymentScheduleRepository).existsById(repaymentId);
        verify(paymentTransactionRepository, never()).findByRepaymentScheduleId(anyLong());
    }
    
}
