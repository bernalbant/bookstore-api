package com.bookstore.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.bookstore.exception.NotFoundException;
import com.bookstore.model.entity.Customer;
import com.bookstore.repository.CustomerRepository;
import com.bookstore.service.CustomerServiceImpl;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

  @Mock
  private CustomerRepository customerRepository;

  @InjectMocks
  private CustomerServiceImpl customerService;

  @ParameterizedTest
  @MethodSource("provideCustomers")
  public void it_should_find_all_customers(Customer customer) {
    List<Customer> customerList = List.of(customer);

    Mockito.when(customerRepository.findAll()).thenReturn(customerList);

    List<Customer> allCustomers = customerService.findAll();

    Mockito.verify(customerRepository).findAll();
    assertThat(allCustomers).isNotNull();
    assertThat(allCustomers.get(0).getId()).isEqualTo(customer.getId());
    assertThat(allCustomers.get(0).getAddress()).isEqualTo(customer.getAddress());
    assertThat(allCustomers.get(0).getEmail()).isEqualTo(customer.getEmail());
    assertThat(allCustomers.get(0).getName()).isEqualTo(customer.getName());
    assertThat(allCustomers.get(0).getPhone()).isEqualTo(customer.getPhone());
    assertThat(allCustomers.get(0).getSurname()).isEqualTo(customer.getSurname());
  }

  @ParameterizedTest
  @MethodSource("provideCustomers")
  public void it_should_find_customer_by_id(Customer customer) {
    Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(customer));

    Customer foundCustomer = customerService.findById(customer.getId());

    Mockito.verify(customerRepository).findById(customer.getId());
    assertThat(foundCustomer).isNotNull();
    assertThat(foundCustomer.getId()).isEqualTo(customer.getId());
    assertThat(foundCustomer.getAddress()).isEqualTo(customer.getAddress());
    assertThat(foundCustomer.getEmail()).isEqualTo(customer.getEmail());
    assertThat(foundCustomer.getName()).isEqualTo(customer.getName());
    assertThat(foundCustomer.getPhone()).isEqualTo(customer.getPhone());
    assertThat(foundCustomer.getSurname()).isEqualTo(customer.getSurname());
  }

  @ParameterizedTest
  @MethodSource("provideExceptionCustomers")
  public void it_should_throw_not_found_exception_when_there_are_no_any_customer(Customer customer) {
    Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

    NotFoundException notFoundException = Assertions.catchThrowableOfType(() ->
            customerService.findById(customer.getId()),
        NotFoundException.class);

    Mockito.verify(customerRepository).findById(customer.getId());
    assertThat(notFoundException).isNotNull();
    assertThat(notFoundException.getMessage()).isEqualTo("Customer not found with the given id " + customer.getId());
  }

  @ParameterizedTest
  @MethodSource("provideCustomers")
  public void it_should_save_customer(Customer customer) {
    Mockito.when(customerRepository.save(Mockito.any())).thenReturn(customer);

    Customer savedCustomer = customerService.save(customer);

    Mockito.verify(customerRepository).save(customer);
    assertThat(savedCustomer).isNotNull();
    assertThat(savedCustomer.getId()).isEqualTo(customer.getId());
    assertThat(savedCustomer.getAddress()).isEqualTo(customer.getAddress());
    assertThat(savedCustomer.getEmail()).isEqualTo(customer.getEmail());
    assertThat(savedCustomer.getName()).isEqualTo(customer.getName());
    assertThat(savedCustomer.getPhone()).isEqualTo(customer.getPhone());
    assertThat(savedCustomer.getSurname()).isEqualTo(customer.getSurname());
  }

  private static Stream<Arguments> provideExceptionCustomers() {
    Customer customer = new Customer();
    customer.setId(1);
    customer.setName("Name of the customer");
    customer.setAddress("Address of the customer");
    customer.setEmail("Email of the customer");
    customer.setPhone("Phone of the customer");
    customer.setSurname("Surname of the customer");

    return Stream.of(
        Arguments.of(customer)
    );
  }

  private static Stream<Arguments> provideCustomers() {
    Customer customer = new Customer();
    customer.setId(1);
    customer.setName("Name of the customer");
    customer.setAddress("Address of the customer");
    customer.setEmail("Email of the customer");
    customer.setPhone("Phone of the customer");
    customer.setSurname("Surname of the customer");

    Customer customer1 = new Customer();
    customer1.setId(2);
    customer1.setName("Name of the second customer");
    customer1.setAddress("Address of the second customer");
    customer1.setEmail("Email of the second customer");
    customer1.setPhone("Phone of the second customer");
    customer1.setSurname("Surname of the second customer");

    Customer customer2 = new Customer();
    customer2.setId(3);
    customer2.setName("Name of the third customer");
    customer2.setAddress("Address of the third customer");
    customer2.setEmail("Email of the third customer");
    customer2.setPhone("Phone of the third customer");
    customer2.setSurname("Surname of the third customer");

    return Stream.of(
        Arguments.of(customer),
        Arguments.of(customer1),
        Arguments.of(customer2)
    );
  }
}
