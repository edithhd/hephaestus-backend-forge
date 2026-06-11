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
       try{

           if(fullName == null || fullName.trim().isEmpty()){
                throw new IllegalArgumentException("Nama harus ada isi");
            }
            Customer customer = new Customer(sequence, fullName, email, phoneNumber);
            customerStorage.put(sequence,customer);
            sequence += 1;
            return customer;
       } catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
           return null;
       }
    }

    public Customer getCustomerById(Long id) {
        Customer customer = customerStorage.get(id);
        return customer;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> listCustomer = new ArrayList<>(customerStorage.values());
        return listCustomer;
    }

    public void updateCustomerEmail(Long id, String email) {
        customerStorage.get(id).setEmail(email);
    }

    public void deleteCustomer(Long id){
        customerStorage.remove(id);
    }
}

