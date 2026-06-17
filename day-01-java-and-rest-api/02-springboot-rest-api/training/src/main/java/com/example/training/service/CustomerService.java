package com.example.training.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springdoc.core.converters.models.SortAsQueryParam;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.training.dto.CreateCustomerRequest;
import com.example.training.dto.CustomerResponse;
import com.example.training.dto.PatchCustomerRequest;
import com.example.training.dto.UpdateCustomerRequest;
import com.example.training.exception.CustomerNotFoundException;
import com.example.training.model.Customer;

import jakarta.validation.Valid;

@Service
// @Validated
public class CustomerService {
    private Map<Long, Customer> customerStorage = new HashMap<>();
    private Long sequence = 1L;

    public List<CustomerResponse> getCustomers() {
        List<CustomerResponse> responses = new ArrayList<>();
        for(Customer customer : customerStorage.values()) {
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.setId(customer.getId());
            customerResponse.setFullName(customer.getFullName());
            customerResponse.setEmail(customer.getEmail());
            customerResponse.setPhoneNumber(customer.getPhoneNumber());
            customerResponse.setCreatedAt(customer.getCreatedAt());
            customerResponse.setUpdatedAt(customer.getUpdatedAt());

            responses.add(customerResponse);
        }
        return responses;
    }

    public List<CustomerResponse> searchCustomerByEmail(String email) {
        List<CustomerResponse> responses = new ArrayList<>();
        for(Customer customer : customerStorage.values()) {
            if(customer.getEmail().toLowerCase().contains(email.toLowerCase())) {
                CustomerResponse customerResponse = new CustomerResponse();
                customerResponse.setId(customer.getId());
                customerResponse.setFullName(customer.getFullName());
                customerResponse.setEmail(customer.getEmail());
                customerResponse.setPhoneNumber(customer.getPhoneNumber());
                customerResponse.setCreatedAt(customer.getCreatedAt());
                customerResponse.setUpdatedAt(customer.getUpdatedAt());

                responses.add(customerResponse);
            }
        }
        return responses;
    }

	// @PostMapping
	public CustomerResponse createCustomer(@RequestBody CreateCustomerRequest request) {
        Customer newCust = new Customer(sequence, request.getFullName(), request.getEmail(), request.getPhoneNumber());
        customerStorage.put(sequence, newCust);
        sequence++;

        CustomerResponse response = new CustomerResponse();
		response.setId(newCust.getId());
		response.setFullName(newCust.getFullName());
		response.setEmail(newCust.getEmail());
		response.setPhoneNumber(newCust.getPhoneNumber());
        response.setCreatedAt(newCust.getCreatedAt());
        response.setUpdatedAt(newCust.getUpdatedAt());

        return response;
	}

	// @GetMapping("/{id}")
	public CustomerResponse getCustomerById(@PathVariable Long id) {
        Customer customer = customerStorage.get(id);
        if(customer == null) {
            throw new CustomerNotFoundException(id);
        }
        
		CustomerResponse response = new CustomerResponse();
		response.setId(id);
		response.setFullName(customer.getFullName());
		response.setEmail(customer.getEmail());
		response.setPhoneNumber(customer.getPhoneNumber());
        response.setCreatedAt(customer.getCreatedAt());
        response.setUpdatedAt(customer.getUpdatedAt());
        
        return response;
    } 

    public CustomerResponse deleteCustomerById(@PathVariable Long id) {
        Customer customer = customerStorage.get(id);
        if(customer == null) {
            throw new CustomerNotFoundException(id);
        }
        customerStorage.remove(id);

        CustomerResponse response = new CustomerResponse();
		response.setId(id);
		response.setFullName(customer.getFullName());
		response.setEmail(customer.getEmail());
		response.setPhoneNumber(customer.getPhoneNumber());
        response.setCreatedAt(customer.getCreatedAt());
        response.setUpdatedAt(customer.getUpdatedAt());
        
        return response;
    }

    // @PutMapping
    public CustomerResponse updateCustomerById(@PathVariable Long id, @RequestBody UpdateCustomerRequest entity) {
        Customer customer = customerStorage.get(id);
        if(customer == null) {
            throw new CustomerNotFoundException(id);
        }
        customer.setEmail(entity.getEmail());
        customer.setFullName(entity.getFullName());
        customer.setPhoneNumber(entity.getPhoneNumber());
        customerStorage.put(id, customer);

        CustomerResponse response = new CustomerResponse();
		response.setId(id);
		response.setFullName(customer.getFullName());
		response.setEmail(customer.getEmail());
		response.setPhoneNumber(customer.getPhoneNumber());
        response.setCreatedAt(customer.getCreatedAt());
        response.setUpdatedAt(customer.getUpdatedAt());
        
        return response;
    }

    // @PatchMapping
    public CustomerResponse patchCustomer(@PathVariable Long id, @Valid @RequestBody PatchCustomerRequest entity) {
        Customer customer = customerStorage.get(id);

        if(customer == null) {
            throw new CustomerNotFoundException(id);
        }

        if(entity.getEmail() != null) {
            customer.setEmail(entity.getEmail());
        } 
        if (entity.getFullName() != null) {
            customer.setFullName(entity.getFullName());
        }
        if (entity.getPhoneNumber() != null) {
            customer.setPhoneNumber(entity.getPhoneNumber());
        }

        customerStorage.put(id, customer);

        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setFullName(customer.getFullName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setCreatedAt(customer.getCreatedAt());
        response.setUpdatedAt(customer.getUpdatedAt());

        return response;
    }
}
