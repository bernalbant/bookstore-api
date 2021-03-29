package com.bookstore.converter;

import com.bookstore.model.entity.Customer;
import com.bookstore.model.response.CustomerListResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CustomerListConverter {

  private CustomerConverter customerConverter;

  public CustomerListConverter(CustomerConverter customerConverter) {
    this.customerConverter = customerConverter;
  }

  public CustomerListResponse convert(List<Customer> customers) {
    CustomerListResponse customerListResponse = new CustomerListResponse();

    customerListResponse.setCustomerResponseList(customers
        .stream()
        .map(customerConverter::convert).collect(Collectors.toList()));

    return customerListResponse;
  }
}
