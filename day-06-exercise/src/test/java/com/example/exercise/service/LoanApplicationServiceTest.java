package com.example.exercise.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.exercise.dto.CreateLoanApplicationRequest;
import com.example.exercise.dto.LoanApplicationResponse;
import com.example.exercise.entity.CustomerEntity;
import com.example.exercise.entity.LoanApplicationEntity;
import com.example.exercise.enums.LoanStatus;
import com.example.exercise.exception.LoanApplicationNotFoundException;
import com.example.exercise.repository.CustomerRepository;
import com.example.exercise.repository.LoanApplicationRepository;

@ExtendWith(MockitoExtension.class)
public class LoanApplicationServiceTest {
    @Mock
    private LoanApplicationRepository loanApplicationRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private RepaymentScheduleService repaymentScheduleService;

    @InjectMocks
    private LoanApplicationService loanApplicationService;
    @InjectMocks
    private CustomerService customerService;    

    @Test
    void should_create_loan_application_successfully() {

        // Given
        Long customerId = 1L;

        CreateLoanApplicationRequest request =
                new CreateLoanApplicationRequest(customerId, BigDecimal.valueOf(10000000), 12, "Business");

        CustomerEntity customer = new CustomerEntity();
        customer.setId(customerId);

        LoanApplicationEntity savedLoan = new LoanApplicationEntity();
        savedLoan.setId(1L);
        savedLoan.setCustomer(customer);
        savedLoan.setLoanAmount(request.getLoanAmount());
        savedLoan.setTenorMonth(request.getTenorMonth());
        savedLoan.setPurpose(request.getPurpose());
        savedLoan.setStatus(LoanStatus.SUBMITTED);

        // When
        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(customer));

        when(loanApplicationRepository.save(any(LoanApplicationEntity.class)))
                .thenReturn(savedLoan);

        // Then
        LoanApplicationResponse response =
                loanApplicationService.createLoanApplication(request);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(BigDecimal.valueOf(10000000), response.getLoanAmount());
        assertEquals(12, response.getTenorMonth());
        assertEquals("Business", response.getPurpose());

        verify(customerRepository, times(1))
                .findById(customerId);

        verify(loanApplicationRepository, times(1))
                .save(any(LoanApplicationEntity.class));
    }

    // get all loan app
    @Test
    void should_get_all_loan_applications_successfully() {

        // Given
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);

        LoanApplicationEntity loan1 = new LoanApplicationEntity();
        loan1.setId(1L);
        loan1.setCustomer(customer); // ✅ IMPORTANT
        loan1.setLoanAmount(BigDecimal.valueOf(10000000));
        loan1.setTenorMonth(12);
        loan1.setPurpose("Business");

        LoanApplicationEntity loan2 = new LoanApplicationEntity();
        loan2.setId(2L);
        loan2.setCustomer(customer); // ✅ IMPORTANT
        loan2.setLoanAmount(BigDecimal.valueOf(20000000));
        loan2.setTenorMonth(24);
        loan2.setPurpose("Education");

        when(loanApplicationRepository.findAll())
                .thenReturn(List.of(loan1, loan2));

        // When
        List<LoanApplicationResponse> responses =
                loanApplicationService.getLoanApplication();

        // Then
        assertNotNull(responses);
        assertEquals(2, responses.size());

        verify(loanApplicationRepository, times(1)).findAll();
    }

    // get loan app by id
    @Test
    void should_get_loan_application_by_id_successfully() {

        // Given
        Long loanId = 1L;

        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(loanId);
        loan.setCustomer(customer); // ✅ IMPORTANT (avoid NPE)
        loan.setLoanAmount(BigDecimal.valueOf(20000000));
        loan.setTenorMonth(12);
        loan.setPurpose("Business");

        when(loanApplicationRepository.findById(loanId))
                .thenReturn(Optional.of(loan));

        // When
        LoanApplicationResponse response =
                loanApplicationService.getLoanApplicationById(loanId);

        // Then
        assertNotNull(response);
        assertEquals(loanId, response.getId());
        assertEquals(BigDecimal.valueOf(20000000), response.getLoanAmount());
        assertEquals(12, response.getTenorMonth());
        assertEquals("Business", response.getPurpose());

        verify(loanApplicationRepository, times(1))
                .findById(loanId);
    }

    // get loan app by id but id not found
    @Test
    void should_throw_exception_when_loan_application_not_found() {

        // Given
        Long loanId = 99L;

        // When
        when(loanApplicationRepository.findById(loanId))
                .thenReturn(Optional.empty());

        // Then
        assertThrows(LoanApplicationNotFoundException.class, () -> {
            loanApplicationService.getLoanApplicationById(loanId);
        });

        verify(loanApplicationRepository, times(1))
                .findById(loanId);
    }

    // get loan app by cust id
    @Test
    void search_by_customer_id_should_return_loans() {

        Long customerId = 1L;

        CustomerEntity customer = new CustomerEntity();
        customer.setId(customerId);

        LoanApplicationEntity loan1 = new LoanApplicationEntity();
        loan1.setId(100L);
        loan1.setCustomer(customer);
        loan1.setLoanAmount(BigDecimal.valueOf(10_000_000));
        loan1.setTenorMonth(12);
        loan1.setPurpose("Home Renovation");
        loan1.setStatus(LoanStatus.SUBMITTED);
        loan1.setCreatedAt(ZonedDateTime.now());
        loan1.setUpdatedAt(ZonedDateTime.now());

        LoanApplicationEntity loan2 = new LoanApplicationEntity();
        loan2.setId(101L);
        loan2.setCustomer(customer);
        loan2.setLoanAmount(BigDecimal.valueOf(5_000_000));
        loan2.setTenorMonth(6);
        loan2.setPurpose("Car Repair");
        loan2.setStatus(LoanStatus.APPROVED);
        loan2.setCreatedAt(ZonedDateTime.now());
        loan2.setUpdatedAt(ZonedDateTime.now());

        when(loanApplicationRepository.findLoansByCustomerId(customerId))
                .thenReturn(List.of(loan1, loan2));

        List<LoanApplicationResponse> responses =
                loanApplicationService.searchByCustomerId(customerId);

        assertNotNull(responses);
        assertEquals(2, responses.size());

        assertEquals(100L, responses.get(0).getId());
        assertEquals(101L, responses.get(1).getId());

        verify(loanApplicationRepository)
                .findLoansByCustomerId(customerId);
    }

    // get loan app by status
    @Test
    void search_loans_by_status() {

        CustomerEntity customer1 = new CustomerEntity();
        customer1.setId(1L);

        LoanApplicationEntity loan1 = new LoanApplicationEntity();
        loan1.setId(100L);
        loan1.setCustomer(customer1);
        loan1.setLoanAmount(BigDecimal.valueOf(10000000));
        loan1.setTenorMonth(12);
        loan1.setPurpose("Home Renovation");
        loan1.setStatus(LoanStatus.APPROVED);
        loan1.setCreatedAt(ZonedDateTime.now());
        loan1.setUpdatedAt(ZonedDateTime.now());

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setId(2L);

        LoanApplicationEntity loan2 = new LoanApplicationEntity();
        loan2.setId(101L);
        loan2.setCustomer(customer2);
        loan2.setLoanAmount(BigDecimal.valueOf(5000000));
        loan2.setTenorMonth(6);
        loan2.setPurpose("Car Repair");
        loan2.setStatus(LoanStatus.APPROVED);
        loan2.setCreatedAt(ZonedDateTime.now());
        loan2.setUpdatedAt(ZonedDateTime.now());

        when(loanApplicationRepository.findByStatus(LoanStatus.APPROVED))
                .thenReturn(List.of(loan1, loan2));

        List<LoanApplicationResponse> responses =
                loanApplicationService.searchByStatus(LoanStatus.APPROVED);

        assertEquals(2, responses.size());

        verify(loanApplicationRepository)
                .findByStatus(LoanStatus.APPROVED);
    }

    // approve loan app
    @Test
    void approve_loan_application() {

        Long loanId = 100L;
        Long customerId = 1L;

        CustomerEntity customer = new CustomerEntity();
        customer.setId(customerId);

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(loanId);
        loan.setCustomer(customer);
        loan.setLoanAmount(BigDecimal.valueOf(10_000_000));
        loan.setTenorMonth(12);
        loan.setPurpose("Home Renovation");
        loan.setStatus(LoanStatus.SUBMITTED);
        loan.setCreatedAt(ZonedDateTime.now());
        loan.setUpdatedAt(ZonedDateTime.now());

        when(loanApplicationRepository.findById(loanId))
                .thenReturn(Optional.of(loan));

        when(loanApplicationRepository.save(any(LoanApplicationEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        LoanApplicationResponse response =
                loanApplicationService.approveLoanApplication(loanId);

        assertNotNull(response);
        assertEquals(loanId, response.getId());
        assertEquals(LoanStatus.APPROVED, response.getStatus());

        verify(loanApplicationRepository).findById(loanId);
        verify(loanApplicationRepository).save(any(LoanApplicationEntity.class));
        verify(repaymentScheduleService).createRepaymentSchedule(any(LoanApplicationEntity.class));
    }

    // approve loan app but loan app not found
    @Test
    void approve_loan_application_should_throw_exception_when_loan_not_found() {

        Long loanId = 999L;

        when(loanApplicationRepository.findById(loanId)).thenReturn(Optional.empty());

        assertThrows(
                LoanApplicationNotFoundException.class,
                () -> loanApplicationService.approveLoanApplication(loanId)
        );

        verify(loanApplicationRepository).findById(loanId);
        verify(loanApplicationRepository, never()).save(any());
        verify(repaymentScheduleService, never()).createRepaymentSchedule(any());
    }
    
    // reject loan app
    @Test
    void reject_loan_application() {

        Long loanId = 100L;
        Long customerId = 1L;

        CustomerEntity customer = new CustomerEntity();
        customer.setId(customerId);

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(loanId);
        loan.setCustomer(customer);
        loan.setLoanAmount(BigDecimal.valueOf(10_000_000));
        loan.setTenorMonth(12);
        loan.setPurpose("Home Renovation");
        loan.setStatus(LoanStatus.SUBMITTED);
        loan.setCreatedAt(ZonedDateTime.now());
        loan.setUpdatedAt(ZonedDateTime.now());

        when(loanApplicationRepository.findById(loanId))
                .thenReturn(Optional.of(loan));

        when(loanApplicationRepository.save(any(LoanApplicationEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        LoanApplicationResponse response =
                loanApplicationService.rejectLoanApplication(loanId);

        assertNotNull(response);
        assertEquals(loanId, response.getId());
        assertEquals(LoanStatus.REJECTED, response.getStatus());

        verify(loanApplicationRepository).findById(loanId);
        verify(loanApplicationRepository).save(any(LoanApplicationEntity.class));
    }

    // reject loan app but loan not found
    @Test
    void reject_loan_application_should_throw_exception_when_loan_not_found() {

        Long loanId = 999L;

        when(loanApplicationRepository.findById(loanId)).thenReturn(Optional.empty());

        assertThrows(
                LoanApplicationNotFoundException.class,
                () -> loanApplicationService.approveLoanApplication(loanId)
        );

        verify(loanApplicationRepository).findById(loanId);
        verify(loanApplicationRepository, never()).save(any());
        verify(repaymentScheduleService, never()).createRepaymentSchedule(any());
    }

    // get loan apps based on cust id
    @Test
    void search_by_customer_id_should_map_entity_to_response_correctly() {

        Long customerId = 1L;

        CustomerEntity customer = new CustomerEntity();
        customer.setId(customerId);

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(100L);
        loan.setCustomer(customer);
        loan.setLoanAmount(BigDecimal.valueOf(10_000_000));
        loan.setTenorMonth(12);
        loan.setPurpose("Home Renovation");
        loan.setStatus(LoanStatus.SUBMITTED);
        loan.setCreatedAt(ZonedDateTime.now());
        loan.setUpdatedAt(ZonedDateTime.now());

        when(loanApplicationRepository.findLoansByCustomerId(customerId))
                .thenReturn(List.of(loan));

        List<LoanApplicationResponse> responses =
                loanApplicationService.searchByCustomerId(customerId);

        assertEquals(1, responses.size());

        LoanApplicationResponse response = responses.get(0);

        assertEquals(100L, response.getId());
        assertEquals(customerId, response.getCustomerId());
        assertEquals(BigDecimal.valueOf(10_000_000), response.getLoanAmount());
        assertEquals(12, response.getTenorMonth());
        assertEquals("Home Renovation", response.getPurpose());
        assertEquals(LoanStatus.SUBMITTED, response.getStatus());

        assertNotNull(response.getCreatedAt());
        assertNotNull(response.getUpdatedAt());
    }
}
