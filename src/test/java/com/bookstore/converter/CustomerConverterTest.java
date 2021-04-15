package com.bookstore.converter;

import com.bookstore.model.entity.Customer;
import com.bookstore.model.request.CustomerRequest;
import com.bookstore.model.response.CustomerResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CustomerConverterTest {

  @Autowired
  private CustomerConverter customerConverter;

  @BeforeEach
  void init() {
    customerConverter = new CustomerConverter();
  }

  @Test
  public void itShouldConvertCustomerToCustomerResponse() {
    Customer customer = new Customer();
    customer.setId(1);
    customer.setName("Name of the customer");
    customer.setAddress("Address of the customer");
    customer.setEmail("Email of the customer");
    customer.setPhone("Phone of the customer");
    customer.setSurname("Surname of the customer");

    CustomerResponse customerConversion = customerConverter.convert(customer);

    Assertions.assertThat(customerConversion).isNotNull();
    Assertions.assertThat(customerConversion.getId()).isEqualTo(1);
    Assertions.assertThat(customerConversion.getName()).isEqualTo("Name of the customer");
    Assertions.assertThat(customerConversion.getAddress()).isEqualTo("Address of the customer");
    Assertions.assertThat(customerConversion.getEmail()).isEqualTo("Email of the customer");
    Assertions.assertThat(customerConversion.getPhone()).isEqualTo("Phone of the customer");
    Assertions.assertThat(customerConversion.getSurname()).isEqualTo("Surname of the customer");
  }

  @Test
  public void itShouldConvertCustomerRequestToCustomer() {
    CustomerRequest customerRequest = new CustomerRequest();
    customerRequest.setName("Name of the customer");
    customerRequest.setAddress("Address of the customer");
    customerRequest.setEmail("Email of the customer");
    customerRequest.setPhone("Phone of the customer");
    customerRequest.setSurname("Surname of the customer");

    Customer customer = customerConverter.convert(customerRequest);

    Assertions.assertThat(customer).isNotNull();
    Assertions.assertThat(customer.getName()).isEqualTo("Name of the customer");
    Assertions.assertThat(customer.getAddress()).isEqualTo("Address of the customer");
    Assertions.assertThat(customer.getEmail()).isEqualTo("Email of the customer");
    Assertions.assertThat(customer.getPhone()).isEqualTo("Phone of the customer");
    Assertions.assertThat(customer.getSurname()).isEqualTo("Surname of the customer");
  }
}
