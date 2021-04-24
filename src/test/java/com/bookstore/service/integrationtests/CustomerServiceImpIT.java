package com.bookstore.service.integrationtests;

import static org.assertj.core.api.Assertions.assertThat;

import com.bookstore.exception.NotFoundException;
import com.bookstore.model.entity.Customer;
import com.bookstore.service.CustomerService;
import com.bookstore.service.integrationtests.base.MongoDbBaseContainer;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerServiceImpIT extends MongoDbBaseContainer {

  @Autowired
  private CustomerService customerService;

  @BeforeEach
  public void setUp() {
    customerService.save(new Customer(1, "Name of the customer", "Surname of the customer", "5555555555", "e@mail.com", "Address of the customer"));
    customerService.save(new Customer(2, "Name of the customer", "Surname of the customer", "5555555555", "e@mail.com", "Address of the customer"));
  }

  @Test
  @Order(1)
  public void itShouldFindSavedFirstCustomer() {
    var customer = customerService.findById(1);

    assertThat(customer.getId()).isEqualTo(1);
    assertThat(customer.getName()).isEqualTo("Name of the customer");
    assertThat(customer.getSurname()).isEqualTo("Surname of the customer");
    assertThat(customer.getPhone()).isEqualTo("5555555555");
    assertThat(customer.getEmail()).isEqualTo("e@mail.com");
    assertThat(customer.getAddress()).isEqualTo("Address of the customer");
  }

  @Test
  @Order(1)
  public void itShouldFindSavedSecondCustomer() {
    var customer = customerService.findById(2);

    assertThat(customer.getId()).isEqualTo(2);
    assertThat(customer.getName()).isEqualTo("Name of the customer");
    assertThat(customer.getSurname()).isEqualTo("Surname of the customer");
    assertThat(customer.getPhone()).isEqualTo("5555555555");
    assertThat(customer.getEmail()).isEqualTo("e@mail.com");
    assertThat(customer.getAddress()).isEqualTo("Address of the customer");
  }

  @Test
  @Order(2)
  public void itShouldThrowExceptionWhenThereIsNoCustomerWithSpecifiedId() {
    var customerId = 3;
    var notFoundException = Assertions.catchThrowableOfType(() ->
            customerService.findById(customerId),
        NotFoundException.class
    );

    assertThat(notFoundException).isNotNull();
    assertThat(notFoundException.getMessage()).isEqualTo("Customer not found with the given id " + customerId);
  }

  @Test
  @Order(3)
  public void itShouldFindAllSavedCustomers() {
    var allCustomers = customerService.findAll();

    assertThat(allCustomers.size()).isEqualTo(2);

    var customer = allCustomers.get(0);
    assertThat(customer.getId()).isEqualTo(1);
    assertThat(customer.getName()).isEqualTo("Name of the customer");
    assertThat(customer.getSurname()).isEqualTo("Surname of the customer");
    assertThat(customer.getPhone()).isEqualTo("5555555555");
    assertThat(customer.getEmail()).isEqualTo("e@mail.com");
    assertThat(customer.getAddress()).isEqualTo("Address of the customer");

    var secondCustomer = allCustomers.get(1);
    assertThat(secondCustomer.getId()).isEqualTo(2);
    assertThat(secondCustomer.getName()).isEqualTo("Name of the customer");
    assertThat(secondCustomer.getSurname()).isEqualTo("Surname of the customer");
    assertThat(secondCustomer.getPhone()).isEqualTo("5555555555");
    assertThat(secondCustomer.getEmail()).isEqualTo("e@mail.com");
    assertThat(secondCustomer.getAddress()).isEqualTo("Address of the customer");
  }

  @Test
  @Order(4)
  public void it_should_find_customers_after_one_customer_deleted() {
    customerService.deleteById(1);

    List<Customer> customersAfterDeletion = customerService.findAll();

    assertThat(customersAfterDeletion.size()).isEqualTo(1);

    var secondCustomer = customersAfterDeletion.get(0);
    assertThat(secondCustomer.getId()).isEqualTo(2);
    assertThat(secondCustomer.getName()).isEqualTo("Name of the customer");
    assertThat(secondCustomer.getSurname()).isEqualTo("Surname of the customer");
    assertThat(secondCustomer.getPhone()).isEqualTo("5555555555");
    assertThat(secondCustomer.getEmail()).isEqualTo("e@mail.com");
    assertThat(secondCustomer.getAddress()).isEqualTo("Address of the customer");
  }
}
