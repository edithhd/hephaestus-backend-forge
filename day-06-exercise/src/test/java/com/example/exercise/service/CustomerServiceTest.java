package com.example.exercise.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.exercise.dto.CreateCustomerRequest;
import com.example.exercise.dto.CustomerResponse;
import com.example.exercise.dto.LoanApplicationResponse;
import com.example.exercise.dto.UpdateCustomerRequest;
import com.example.exercise.entity.CustomerEntity;
import com.example.exercise.entity.LoanApplicationEntity;
import com.example.exercise.exception.CustomerNotFoundException;
import com.example.exercise.exception.DuplicateException;
import com.example.exercise.repository.CustomerRepository;
import com.example.exercise.repository.LoanApplicationRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @InjectMocks
    private CustomerService customerService;
    private LoanApplicationService loanApplicationService;

    // berhasil create customer
    @Test
    void should_create_customer_successfully() {

        // Given
        CreateCustomerRequest request = new CreateCustomerRequest("test", "112230493893", "test@gmail.com", "081122334455");
        CustomerEntity testEntity = new CustomerEntity();
        testEntity.setId(1L);
        testEntity.setFullName(request.getFullName());
        testEntity.setNik(request.getNik());
        testEntity.setEmail(request.getEmail());
        testEntity.setPhoneNumber(request.getPhoneNumber());

        // When
        when(customerRepository.existsByNik(request.getNik())).thenReturn(false);
        when(customerRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(testEntity);

        // Then
        CustomerResponse response = customerService.createCustomer(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("test", response.getFullName());
        assertEquals("112230493893", response.getNik());
        assertEquals("test@gmail.com", response.getEmail());
        assertEquals("081122334455", response.getPhoneNumber());
        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
    }

    // fail create customer karena nik duplicate
    @Test
    void should_throw_exception_when_nik_is_duplicate() {

        // Given
        CreateCustomerRequest request = new CreateCustomerRequest("test", "112230493893", "test@gmail.com", "081122334455");

        // When --> mau nik sudah ada atau tidak, akan ke-detect sebagai sudah ada (true)
        when(customerRepository.existsByNik(request.getNik())).thenReturn(true);

        // Then

        DuplicateException exception = assertThrows(DuplicateException.class, () -> customerService.createCustomer(request));
        assertEquals("NIK already exists", exception.getMessage());
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

     // fail create customer karena email duplicate
    @Test
    void should_throw_exception_when_email_is_duplicate() {

        // Given
        CreateCustomerRequest request = new CreateCustomerRequest("test", "112230493893", "test@gmail.com", "081122334455");

        // When --> mau nik sudah ada atau tidak, akan ke-detect sebagai sudah ada (true)
        when(customerRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // Then
        DuplicateException exception = assertThrows(DuplicateException.class, () -> customerService.createCustomer(request));
        assertEquals("Email already exists", exception.getMessage());
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    // berhasil get all customers
    @Test
    void should_get_all_customers() {

        // Given
        CustomerEntity cust1 = new CustomerEntity();
        cust1.setId(1L);
        cust1.setFullName("Customer 1");

        CustomerEntity cust2 = new CustomerEntity();
        cust2.setId(2L);
        cust2.setFullName("Customer 2");

        // When 
        when(customerRepository.findAll()).thenReturn(Arrays.asList(cust1,cust2));

        // Then
        List<CustomerResponse> response = customerService.getCustomers();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(1L, response.get(0).getId());
        assertEquals("Customer 1", response.get(0).getFullName());
        assertEquals(2L, response.get(1).getId());
        assertEquals("Customer 2", response.get(1).getFullName());
        verify(customerRepository, times(1)).findAll();
    }

    // berhasil get customer by id
    @Test
    void should_get_customer_by_id() {

        // Given
        Long customerId = 1L;
        CustomerEntity testEntity = new CustomerEntity();
        testEntity.setId(customerId);
        testEntity.setFullName("test");
        testEntity.setNik("1090290303298");
        testEntity.setEmail("test@gmail.com");
        testEntity.setPhoneNumber("0811223344567");

        // When 
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(testEntity));

        // Then
        CustomerResponse response = customerService.getCustomerById(customerId);

        assertNotNull(response);
        assertEquals(customerId, response.getId());
        assertEquals("test", response.getFullName());
        assertEquals("1090290303298", response.getNik());
        assertEquals("test@gmail.com", response.getEmail());
        assertEquals("0811223344567", response.getPhoneNumber());

        verify(customerRepository, times(1)).findById(customerId);
    }

    // berhasil search cust by name
    @Test
    void should_search_customer_by_name() {

        // Given
        String keyword = "test";

        CustomerEntity testEntity = new CustomerEntity();
        testEntity.setId(1L);
        testEntity.setFullName("test");
        testEntity.setNik("1090290303298");
        testEntity.setEmail("test@gmail.com");
        testEntity.setPhoneNumber("0811223344567");

        // When
        when(customerRepository.findByFullNameContainingIgnoreCase(keyword))
                .thenReturn(List.of(testEntity));

        List<CustomerResponse> responses =
                customerService.searchCustomerByName(keyword);

        // Then
        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
        assertEquals("test", responses.get(0).getFullName());

        verify(customerRepository, times(2))
                .findByFullNameContainingIgnoreCase(keyword);
    }

    // get cust by id tapi not found
     @Test
    void should_throw_exception_when_customer_id_not_found() {

        // Given
        Long id = 99L;

        // When
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        // Then
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getCustomerById(id);
        });

        assertTrue(exception.getMessage().contains("Customer not found"));
        verify(customerRepository, times(1))
                .findById(id);
    }
    
    // search customer by name tapi not found
    @Test
    void should_throw_exception_when_customer_not_found() {

        // Given
        String keyword = "unknown";

        // When
        when(customerRepository.findByFullNameContainingIgnoreCase(keyword)).thenReturn(List.of());

        // Then
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.searchCustomerByName(keyword);
        });

        assertTrue(exception.getMessage().contains("Customer not found"));

        verify(customerRepository, times(1))
                .findByFullNameContainingIgnoreCase(keyword);
    }

    // get loan app by cust id
    @Test
    void should_get_customer_loan_applications_successfully() {

        // Given
        Long customerId = 1L;

        CustomerEntity customer = new CustomerEntity();
        customer.setId(customerId);

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(10L);
        loan.setCustomer(customer);
        loan.setTenorMonth(12); 

        List<LoanApplicationEntity> loans = List.of(loan);

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(customer));

        when(loanApplicationRepository.findByCustomerId(customerId))
                .thenReturn(loans);

        // When
        List<LoanApplicationResponse> responses =
                customerService.getCustomersLoanApplication(customerId);

        // Then
        assertNotNull(responses);
    }

    // get loan app by cust id but cust not found
    @Test
    void should_throw_exception_when_loan_app_customer_not_found() {

        // Given
        Long customerId = 1L;

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.empty());

        // Then
        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getCustomersLoanApplication(customerId);
        });

        verify(customerRepository, times(1))
                .findById(customerId);

        // Repository loan should NEVER be called
        verify(loanApplicationRepository, never())
                .findByCustomerId(anyLong());
    }

    // delete customer
    @Test
    void should_delete_customer() {

        Long customerId = 1L;

        CustomerEntity customer = new CustomerEntity();
        customer.setId(customerId);
        customer.setFullName("Test");
        customer.setEmail("test@gmail.com");
        customer.setPhoneNumber("081234567890");

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(customer));

        CustomerResponse response =
                customerService.deleteCustomerById(customerId);

        assertNotNull(response);
        assertEquals(customerId, response.getId());
        assertEquals("Test", response.getFullName());
        assertEquals("test@gmail.com", response.getEmail());
        assertEquals("081234567890", response.getPhoneNumber());

        verify(customerRepository).findById(customerId);
        verify(customerRepository).delete(customer);
    }

    // delete cust but cust not found
    @Test
    void should_throw_exception_when_delete_customer_not_found() {

        Long customerId = 999L;
        when(customerRepository.findById(customerId))
                .thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class,() -> customerService.deleteCustomerById(customerId));
        verify(customerRepository).findById(customerId);
        verify(customerRepository, never()).delete(any());
    }

    // update cust
    @Test
    void should_update_customer_data() {

        Long customerId = 1L;

        CustomerEntity customer = new CustomerEntity();
        customer.setId(customerId);
        customer.setNik("1234567890");
        customer.setFullName("John Doe");
        customer.setEmail("john@email.com");
        customer.setPhoneNumber("081111111111");

        UpdateCustomerRequest request = new UpdateCustomerRequest();
        request.setNik("9876543210");
        request.setFullName("John Updated");
        request.setEmail("john.updated@email.com");
        request.setPhoneNumber("082222222222");

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(customer));

        when(customerRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);

        when(customerRepository.existsByNik(request.getNik()))
                .thenReturn(false);

        when(customerRepository.save(any(CustomerEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CustomerResponse response =
                customerService.updateCustomerById(customerId, request);

        assertNotNull(response);
        assertEquals(customerId, response.getId());
        assertEquals("John Updated", response.getFullName());
        assertEquals("john.updated@email.com", response.getEmail());
        assertEquals("082222222222", response.getPhoneNumber());

        verify(customerRepository).findById(customerId);
        verify(customerRepository).save(any(CustomerEntity.class));
    }

    // update cust but not found
    @Test
    void should_throw_exception_when_updated_customer_not_found() {

        Long customerId = 999L;
        UpdateCustomerRequest request = new UpdateCustomerRequest();

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class,
                () -> customerService.updateCustomerById(customerId, request));

        verify(customerRepository).findById(customerId);
        verify(customerRepository, never()).save(any());
    }

    // update cust but updated email is duplicate
    @Test
    void should_throw_exception_when_email_already_exists() {
        Long customerId = 1L;

        CustomerEntity customer = new CustomerEntity();
        customer.setId(customerId);
        customer.setEmail("old@email.com");
        customer.setNik("1234567890");

        UpdateCustomerRequest request = new UpdateCustomerRequest();
        request.setEmail("new@email.com");
        request.setNik("1234567890");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.existsByEmail("new@email.com")).thenReturn(true);

        DuplicateException exception = assertThrows(DuplicateException.class,
                () -> customerService.updateCustomerById(customerId, request));

        assertEquals("Email already exists", exception.getMessage());
        verify(customerRepository, never()).save(any());
        }

    // update cust but updated nik is duplicate
    @Test
    void should_throw_exception_when_nik_already_exists() {
        Long customerId = 1L;

        CustomerEntity customer = new CustomerEntity();
        customer.setId(customerId);
        customer.setEmail("john@email.com");
        customer.setNik("1234567890");

        UpdateCustomerRequest request = new UpdateCustomerRequest();
        request.setEmail("john@email.com");
        request.setNik("9999999999");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.existsByNik("9999999999")).thenReturn(true);

        DuplicateException exception = assertThrows(DuplicateException.class,
                () -> customerService.updateCustomerById(customerId, request));

        assertEquals("NIK already exists", exception.getMessage());
        verify(customerRepository, never()).save(any());
    }

}