import java.util.List;

import model.Customer;
import service.CustomerService;

public class Main {
    public static void main(String[] args) throws Exception {
        CustomerService customerService = new CustomerService();

        customerService.createCustomer("Edith", "edith@gmail.com", "081122334455");  
        customerService.createCustomer("Kayla", "kayla@gmail.com", "082233445566");  
        customerService.createCustomer("Fadka", "fadka@gmail.com", "083344556677");       
        
        List<Customer> listCustomer = customerService.getAllCustomers();
        
        customerService.updateCustomerEmail(1L, "kayy@gmail.com");
        customerService.deleteCustomer(1L);

        System.out.println("All Customers: ");
        int i = 1;

        for (Customer customer : listCustomer) {
            System.out.println(i + " - " + customer.getFullName() + " - " + customer.getEmail() + " - " + customer.getPhoneNumber());
            i++;
        }

        System.out.println("Customer Detail: ");
        
        Customer selectedCust = customerService.getCustomerById(2L);
        System.out.println(selectedCust.getDisplayName());
    }
}
