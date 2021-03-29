package com.bookstore.controller;

import com.bookstore.handler.CustomerHandler;
import com.bookstore.model.request.CustomerRequest;
import com.bookstore.model.response.CustomerListResponse;
import com.bookstore.model.response.CustomerResponse;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

  private CustomerHandler customerHandler;

  public CustomerController(CustomerHandler customerHandler) {
    this.customerHandler = customerHandler;
  }

  @GetMapping
  public CustomerListResponse findAllCustomers() {
    return customerHandler.findAllCustomers();
  }

  @GetMapping("/{id}")
  public CustomerResponse findCustomerById(@PathVariable int id) {
    return customerHandler.findCustomerById(id);
  }

  @PostMapping
  public CustomerResponse createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
    return customerHandler.createCustomer(customerRequest);
  }

  @DeleteMapping("/{id}")
  public void deleteCustomer(@PathVariable int id) {
    customerHandler.deleteCustomer(id);
  }
}