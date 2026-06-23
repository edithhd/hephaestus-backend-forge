package com.example.exercise.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.exercise.dto.PaymentTransactionResponse;
import com.example.exercise.dto.RepaymentScheduleResponse;
import com.example.exercise.entity.LoanApplicationEntity;
import com.example.exercise.entity.PaymentTransactionEntity;
import com.example.exercise.entity.RepaymentScheduleEntity;
import com.example.exercise.enums.ScheduleStatus;
import com.example.exercise.exception.RepaymentScheduleNotFoundException;
import com.example.exercise.repository.PaymentTransactionRepository;
import com.example.exercise.repository.RepaymentScheduleRepository;

@ExtendWith(MockitoExtension.class)
public class RepaymentScheduleServiceTest {
    @Mock
    private RepaymentScheduleRepository repaymentScheduleRepository;
    @Mock
    private PaymentTransactionRepository paymentTransactionRepository;

    @InjectMocks
    private RepaymentScheduleService repaymentScheduleService;
    @InjectMocks
    private PaymentTransactionService paymentTransactionService;

    // create repayment schedule
    @Test
    void should_create_repayment_schedule_successfully() {

        // given
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(1L);
        loan.setTenorMonth(3);
        loan.setLoanAmount(BigDecimal.valueOf(3_000_000));

        when(repaymentScheduleRepository.save(any(RepaymentScheduleEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        repaymentScheduleService.createRepaymentSchedule(loan);

        // then - verify number of schedules created = tenor
        verify(repaymentScheduleRepository, times(3))
                .save(any(RepaymentScheduleEntity.class));
    }

    // get repayment schedule by id
    @Test
    void should_return_repayment_schedule_by_id_successfully() {

        Long scheduleId = 1L;
        Long loanId = 100L;

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(loanId);

        RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();
        schedule.setId(scheduleId);
        schedule.setLoanApplication(loan);
        schedule.setInstallmentNumber(1);
        schedule.setDueDate(ZonedDateTime.now().plusMonths(1));
        schedule.setPrincipalAmount(BigDecimal.valueOf(1_000_000));
        schedule.setInterestAmount(BigDecimal.valueOf(100_000));
        schedule.setTotalAmount(BigDecimal.valueOf(1_100_000));
        schedule.setStatus(ScheduleStatus.UNPAID);
        schedule.setCreatedAt(ZonedDateTime.now());
        schedule.setUpdatedAt(ZonedDateTime.now());

        when(repaymentScheduleRepository.findByIdWithLoanApplication(scheduleId))
                .thenReturn(Optional.of(schedule));

        RepaymentScheduleResponse response = repaymentScheduleService.getRepaymentScheduleById(scheduleId);

        assertNotNull(response);
        assertEquals(scheduleId, response.getId());
        assertEquals(loanId, response.getLoanApplicationId());
        assertEquals(1, response.getInstallmentNumber());
        assertEquals(BigDecimal.valueOf(1_000_000), response.getPrincipalAmount());

        verify(repaymentScheduleRepository)
                .findByIdWithLoanApplication(scheduleId);
    }

    // get repayment schedule but id not found
    @Test
    void should_throw_exception_when_repayment_schedule_not_found() {

        Long scheduleId = 999L;

        when(repaymentScheduleRepository.findByIdWithLoanApplication(scheduleId))
                .thenReturn(Optional.empty());

        assertThrows(RepaymentScheduleNotFoundException.class,
                () -> repaymentScheduleService.getRepaymentScheduleById(scheduleId));

        verify(repaymentScheduleRepository).findByIdWithLoanApplication(scheduleId);
    }

    // get payment transactions by repayment schedule id
    @Test
    void should_return_payment_transactions_by_repayment_schedule_id() {

        Long repaymentId = 1L;

        PaymentTransactionEntity tx1 = new PaymentTransactionEntity();
        tx1.setId(10L);
        tx1.setPaymentReference("PAY-001");
        tx1.setPaidAmount(BigDecimal.valueOf(1_000_000));
        tx1.setPaidAt(ZonedDateTime.now());
        tx1.setStatus("SUCCESS");

        PaymentTransactionEntity tx2 = new PaymentTransactionEntity();
        tx2.setId(11L);
        tx2.setPaymentReference("PAY-002");
        tx2.setPaidAmount(BigDecimal.valueOf(2_000_000));
        tx2.setPaidAt(ZonedDateTime.now());
        tx2.setStatus("SUCCESS");

        when(repaymentScheduleRepository.existsById(repaymentId)).thenReturn(true);
        when(paymentTransactionRepository.findByRepaymentScheduleId(repaymentId)).thenReturn(List.of(tx1, tx2));

        List<PaymentTransactionResponse> responses = paymentTransactionService.getPaymentTransactionByRepaymentId(repaymentId);

        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals("PAY-001", responses.get(0).getPaymentReference());
        assertEquals("PAY-002", responses.get(1).getPaymentReference());

        verify(repaymentScheduleRepository).existsById(repaymentId);
        verify(paymentTransactionRepository).findByRepaymentScheduleId(repaymentId);
    }
}
