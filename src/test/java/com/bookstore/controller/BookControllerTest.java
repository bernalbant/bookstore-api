package com.bookstore.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bookstore.handler.CustomerHandler;
import com.bookstore.model.entity.Customer;
import com.bookstore.model.response.CustomerListResponse;
import com.bookstore.model.response.CustomerResponse;
import com.bookstore.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomerController.class)
@ActiveProfiles(value = "test")
class BookControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CustomerService customerService;

  @MockBean
  private CustomerHandler customerHandler;

  @BeforeEach()
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @ParameterizedTest
  @MethodSource("provideCustomer")
  public void itShouldSaveCustomer(Customer customer, CustomerResponse customerResponse) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    final String endpoint = String.format("/api/customers/");

    Mockito.when(customerHandler.createCustomer(Mockito.any())).thenReturn(customerResponse);

    mockMvc.perform(post(endpoint)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(customer)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(customerResponse.getId()))
        .andExpect(jsonPath("$.name").value(customerResponse.getName()))
        .andExpect(jsonPath("$.surname").value(customerResponse.getSurname()))
        .andExpect(jsonPath("$.phone").value(customerResponse.getPhone()))
        .andExpect(jsonPath("$.email").value(customerResponse.getEmail()))
        .andExpect(jsonPath("$.address").value(customerResponse.getAddress()))
        .andReturn();
  }

  @ParameterizedTest
  @MethodSource("provideCustomer")
  public void itShouldFindCustomerWithGivenId(Customer customer, CustomerResponse customerResponse) throws Exception {
    final String endpoint = String.format("/api/customers/{id}");
    int customerId = 1;

    Mockito.when(customerService.findById(Mockito.anyInt())).thenReturn(customer);
    Mockito.when(customerHandler.findCustomerById(Mockito.anyInt())).thenReturn(customerResponse);

    mockMvc.perform(get(endpoint, customerId)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(customerResponse.getId()))
        .andExpect(jsonPath("$.name").value(customerResponse.getName()))
        .andExpect(jsonPath("$.surname").value(customerResponse.getSurname()))
        .andExpect(jsonPath("$.phone").value(customerResponse.getPhone()))
        .andExpect(jsonPath("$.email").value(customerResponse.getEmail()))
        .andExpect(jsonPath("$.address").value(customerResponse.getAddress()))
        .andReturn();
  }

  @ParameterizedTest
  @MethodSource("provideCustomers")
  public void itShouldFindAllCustomers(List<Customer> customerList, CustomerListResponse customerListResponse) throws Exception {
    final String endpoint = String.format("/api/customers");

    Mockito.when(customerService.findAll()).thenReturn(customerList);
    Mockito.when(customerHandler.findAllCustomers()).thenReturn(customerListResponse);

    CustomerResponse customerResponse = customerListResponse.getCustomerResponseList().get(0);
    CustomerResponse customerResponse1 = customerListResponse.getCustomerResponseList().get(1);
    mockMvc.perform(get(endpoint)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("customerResponseList[0].id", is(customerResponse.getId())))
        .andExpect(jsonPath("customerResponseList[0].name", is(customerResponse.getName())))
        .andExpect(jsonPath("customerResponseList[0].surname", is(customerResponse.getSurname())))
        .andExpect(jsonPath("customerResponseList[0].phone", is(customerResponse.getPhone())))
        .andExpect(jsonPath("customerResponseList[0].email", is(customerResponse.getEmail())))
        .andExpect(jsonPath("customerResponseList[0].address", is(customerResponse.getAddress())))
        .andExpect(jsonPath("customerResponseList[1].id", is(customerResponse1.getId())))
        .andExpect(jsonPath("customerResponseList[1].name", is(customerResponse1.getName())))
        .andExpect(jsonPath("customerResponseList[1].surname", is(customerResponse1.getSurname())))
        .andExpect(jsonPath("customerResponseList[1].phone", is(customerResponse1.getPhone())))
        .andExpect(jsonPath("customerResponseList[1].email", is(customerResponse1.getEmail())))
        .andExpect(jsonPath("customerResponseList[1].address", is(customerResponse1.getAddress())))
        .andReturn();
  }

  @ParameterizedTest
  @MethodSource("provideCustomer")
  public void itShouldDeleteCustomerWithGivenId(Customer customer) throws Exception {
    final String endpoint = String.format("/api/customers/{id}");
    int customerId = 1;

    Mockito.when(customerService.findById(Mockito.anyInt())).thenReturn(customer);

    mockMvc.perform(delete(endpoint, customerId)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  private static Stream<Arguments> provideCustomer() {
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
        Arguments.of(customer, customerResponse)
    );
  }

  private static Stream<Arguments> provideCustomers() {
    Customer customer = new Customer();
    customer.setId(1);
    customer.setName("Name of customer");
    customer.setSurname("Surname of customer");
    customer.setAddress("Address of customer");
    customer.setEmail("e@mail.com");
    customer.setPhone("5555555555");

    Customer customer1 = new Customer();
    customer1.setId(2);
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

    CustomerResponse customerResponse1 = new CustomerResponse();
    customerResponse1.setId(2);
    customer.setName("Name of customer");
    customer.setSurname("Surname of customer");
    customer.setAddress("Address of customer");
    customer.setEmail("e@mail.com");
    customer.setPhone("5555555555");

    CustomerListResponse customerListResponse = new CustomerListResponse();
    customerListResponse.setCustomerResponseList(List.of(customerResponse, customerResponse1));

    return Stream.of(Arguments.of(List.of(customer, customer1), customerListResponse));
  }
}
