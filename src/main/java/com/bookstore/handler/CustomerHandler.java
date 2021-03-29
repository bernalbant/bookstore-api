package com.bookstore.handler;

import com.bookstore.model.entity.Customer;
import com.bookstore.model.request.CustomerRequest;
import com.bookstore.model.response.CustomerListResponse;
import com.bookstore.model.response.CustomerResponse;
import com.bookstore.service.CustomerService;
import org.springframework.stereotype.Component;

@Component
public class CustomerHandler {

  private CustomerService customerService;

  public CustomerHandler(CustomerService customerService) {
    this.customerService = customerService;
  }

  public CustomerListResponse findAllCustomers() {
    return null;
  }

  public CustomerResponse findCustomerById(int id) {
    return null;
  }

  public CustomerResponse createCustomer(CustomerRequest customerRequest) {
    return null;
  }

  public void deleteCustomer(int id) {
    Customer customer = customerService.findById(id);
    customerService.deleteById(customer.getId());
  }
}
