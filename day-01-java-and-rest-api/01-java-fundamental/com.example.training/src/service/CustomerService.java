package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Customer;

public class CustomerService {
    private Map<Long, Customer> customerStorage = new HashMap<>();
    private Long sequence = 1L;

    public Customer createCustomer(String fullName, String email, String phoneNumber){
        Customer customer = new Customer(sequence, fullName, email, phoneNumber);
        customerStorage.put(sequence,customer);
        sequence += 1;
        return customer;
    }

    public Customer getCustomerById(Long id) {
        Customer customer = customerStorage.get(id);
        return customer;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> listCustomer = new ArrayList<>(customerStorage.values());
        return listCustomer;
    }
}

