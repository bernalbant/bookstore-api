package com.bookstore.handler;

import com.bookstore.converter.CustomerConverter;
import com.bookstore.converter.CustomerListConverter;
import com.bookstore.model.entity.Customer;
import com.bookstore.model.request.CustomerRequest;
import com.bookstore.model.response.CustomerListResponse;
import com.bookstore.model.response.CustomerResponse;
import com.bookstore.service.CustomerService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CustomerHandler {

  private CustomerService customerService;
  public CustomerConverter customerConverter;
  public CustomerListConverter customerListConverter;

  public CustomerHandler(CustomerService customerService,
                         CustomerConverter customerConverter,
                         CustomerListConverter customerListConverter) {
    this.customerService = customerService;
    this.customerConverter = customerConverter;
    this.customerListConverter = customerListConverter;
  }

  public CustomerListResponse findAllCustomers() {
    List<Customer> customers = customerService.findAll();
    return customerListConverter.convert(customers);
  }

  public CustomerResponse findCustomerById(int id) {
    Customer customer = customerService.findById(id);

    return customerConverter.convert(customer);
  }

  public CustomerResponse createCustomer(CustomerRequest customerRequest) {
    Customer savedCustomer = customerService.save(customerConverter.convert(customerRequest));
    return customerConverter.convert(savedCustomer);
  }

  public void deleteCustomer(int id) {
    Customer customer = customerService.findById(id);
    customerService.deleteById(customer.getId());
  }
}
