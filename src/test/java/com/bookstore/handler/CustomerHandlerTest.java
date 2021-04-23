package com.bookstore.handler;

import com.bookstore.converter.CustomerConverter;
import com.bookstore.converter.CustomerListConverter;
import com.bookstore.model.entity.Customer;
import com.bookstore.model.request.CustomerRequest;
import com.bookstore.model.response.CustomerListResponse;
import com.bookstore.model.response.CustomerResponse;
import com.bookstore.service.CustomerService;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerHandlerTest {

  @Mock
  private CustomerService customerService;

  @Mock
  private CustomerConverter customerConverter;

  @Mock
  private CustomerListConverter customerListConverter;

  @InjectMocks
  private CustomerHandler customerHandler;

  @Test
  public void it_should_find_all_customers() {
    ArrayList<Customer> customerList = new ArrayList<>();
    Mockito.when(customerService.findAll()).thenReturn(customerList);

    CustomerListResponse customerResponse = new CustomerListResponse();
    Mockito.when(customerListConverter.convert(Mockito.any())).thenReturn(customerResponse);

    customerHandler.findAllCustomers();

    Mockito.verify(customerService).findAll();
    Mockito.verify(customerListConverter).convert(new ArrayList<>());
  }

  @ParameterizedTest
  @MethodSource("provideCustomer")
  public void it_should_find_customer_by_id(Customer customer, CustomerRequest customerRequest, CustomerResponse customerResponse) {
    Mockito.when(customerService.findById(Mockito.anyInt())).thenReturn(customer);

    Mockito.when(customerConverter.convert(Mockito.any(Customer.class))).thenReturn(customerResponse);

    customerHandler.findCustomerById(1);

    Mockito.verify(customerService).findById(1);
    Mockito.verify(customerConverter).convert(customer);
  }

  @ParameterizedTest
  @MethodSource("provideCustomer")
  public void it_should_create_customer(Customer customer, CustomerRequest customerRequest, CustomerResponse customerResponse) {
    Mockito.when(customerConverter.convert(Mockito.any(CustomerRequest.class))).thenReturn(customer);
    Mockito.when(customerConverter.convert(Mockito.any(Customer.class))).thenReturn(customerResponse);
    Mockito.when(customerService.save(Mockito.any())).thenReturn(customer);

    customerHandler.createCustomer(customerRequest);

    Mockito.verify(customerService).save(customer);
    Mockito.verify(customerConverter).convert(customer);
    Mockito.verify(customerConverter).convert(customerRequest);
  }

  @Test
  public void it_should_delete_customer() {
    Customer customer = new Customer();
    customer.setId(1);
    Mockito.when(customerService.findById(Mockito.anyInt())).thenReturn(customer);

    customerHandler.deleteCustomer(customer.getId());

    Mockito.verify(customerService).findById(1);
  }

  private static Stream<Arguments> provideCustomer() {
    CustomerRequest customerRequest = new CustomerRequest();
    customerRequest.setName("Name of customer");
    customerRequest.setSurname("Surname of customer");
    customerRequest.setAddress("Address of customer");
    customerRequest.setEmail("e@mail.com");
    customerRequest.setPhone("5555555555");

    Customer customer = new Customer();
    customer.setId(1);
    customer.setName("Name of customer");
    customer.setSurname("Surname of customer");
    customer.setAddress("Address of customer");
    customer.setEmail("e@mail.com");
    customer.setPhone("5555555555");

    CustomerResponse customerResponse = new CustomerResponse();
    customerResponse.setId(1);
    customer.setName("Name of customer");
    customer.setSurname("Surname of customer");
    customer.setAddress("Address of customer");
    customer.setEmail("e@mail.com");
    customer.setPhone("5555555555");

    return Stream.of(
        Arguments.of(customer, customerRequest, customerResponse)
    );
  }
}
