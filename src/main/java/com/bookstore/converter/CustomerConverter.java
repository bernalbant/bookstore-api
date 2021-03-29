package com.bookstore.converter;

import com.bookstore.model.entity.Customer;
import com.bookstore.model.request.CustomerRequest;
import com.bookstore.model.response.CustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {

  public CustomerResponse convert(Customer customer) {
    CustomerResponse customerResponse = new CustomerResponse();
    customerResponse.setId(customer.getId());
    customerResponse.setName(customer.getName());
    customerResponse.setSurname(customer.getSurname());
    customerResponse.setPhone(customer.getPhone());
    customerResponse.setEmail(customer.getEmail());
    customerResponse.setAddress(customer.getAddress());

    return customerResponse;
  }

  public Customer convert(CustomerRequest customerRequest) {
    Customer customer = new Customer();
    customer.setName(customerRequest.getName());
    customer.setSurname(customerRequest.getSurname());
    customer.setPhone(customerRequest.getPhone());
    customer.setEmail(customerRequest.getEmail());
    customer.setAddress(customerRequest.getAddress());

    return customer;
  }
}
