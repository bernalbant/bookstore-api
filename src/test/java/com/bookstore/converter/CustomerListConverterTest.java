package com.bookstore.converter;

import com.bookstore.model.entity.Customer;
import com.bookstore.model.response.CustomerListResponse;
import com.bookstore.model.response.CustomerResponse;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerListConverterTest {

  @InjectMocks
  private CustomerListConverter customerListConverter;

  @Mock
  private CustomerConverter customerConverter;

  @Test
  public void itShouldConvertCustomerListToCustomerResponse() {
    Customer customer = new Customer();
    customer.setId(1);
    customer.setName("Name of the customer");
    customer.setAddress("Address of the customer");
    customer.setEmail("Email of the customer");
    customer.setPhone("Phone of the customer");
    customer.setSurname("Surname of the customer");

    CustomerResponse customerResponse = new CustomerResponse();
    customerResponse.setId(1);
    customerResponse.setName("Name of the customer");
    customerResponse.setAddress("Address of the customer");
    customerResponse.setEmail("Email of the customer");
    customerResponse.setPhone("Phone of the customer");
    customerResponse.setSurname("Surname of the customer");

    Mockito.when(customerConverter.convert(Mockito.any(Customer.class))).thenReturn(customerResponse);

    CustomerListResponse conversionOfCustomerList = customerListConverter.convert(List.of(customer));

    Mockito.verify(customerConverter).convert(customer);
    Assertions.assertThat(conversionOfCustomerList).isNotNull();

    CustomerResponse customerResponseList = conversionOfCustomerList.getCustomerResponseList().get(0);
    Assertions.assertThat(customerResponseList.getId()).isEqualTo(customer.getId());
    Assertions.assertThat(customerResponseList.getSurname()).isEqualTo(customer.getSurname());
    Assertions.assertThat(customerResponseList.getPhone()).isEqualTo(customer.getPhone());
    Assertions.assertThat(customerResponseList.getEmail()).isEqualTo(customer.getEmail());
    Assertions.assertThat(customerResponseList.getAddress()).isEqualTo(customer.getAddress());
    Assertions.assertThat(customerResponseList.getName()).isEqualTo(customer.getName());
  }
}
