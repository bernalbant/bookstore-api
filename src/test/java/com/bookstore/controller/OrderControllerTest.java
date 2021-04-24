package com.bookstore.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bookstore.handler.OrderHandler;
import com.bookstore.model.entity.Order;
import com.bookstore.model.entity.OrderDetail;
import com.bookstore.model.request.OrderDetailRequest;
import com.bookstore.model.request.OrderRequest;
import com.bookstore.model.response.OrderDetailResponse;
import com.bookstore.model.response.OrderListResponse;
import com.bookstore.model.response.OrderResponse;
import com.bookstore.service.OrderService;
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
@WebMvcTest(controllers = OrderController.class)
@ActiveProfiles(value = "test")
class OrderControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OrderService orderService;

  @MockBean
  private OrderHandler orderHandler;

  @BeforeEach()
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @ParameterizedTest
  @MethodSource("provideOrderRequest")
  public void itShouldCreateOrder(OrderRequest orderRequest, OrderResponse orderResponse) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    final String endpoint = String.format("/api/orders/");

    Mockito.when(orderHandler.createOrder(Mockito.any())).thenReturn(orderResponse);

    mockMvc.perform(post(endpoint)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(orderRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.customerId").value(orderResponse.getCustomerId()))
        .andExpect(jsonPath("$.orderDetails[0].bookId").value(orderResponse.getOrderDetails().get(0).getBookId()))
        .andExpect(jsonPath("$.orderDetails[0].count").value(orderResponse.getOrderDetails().get(0).getCount()))
        .andReturn();
  }

  @ParameterizedTest
  @MethodSource("provideOrder")
  public void itShouldFindOrderWithGivenId(Order order, OrderResponse orderResponse) throws Exception {
    final String endpoint = String.format("/api/orders/{id}");
    int bookId = 1;

    Mockito.when(orderService.findById(Mockito.anyInt())).thenReturn(order);
    Mockito.when(orderHandler.findOrderById(Mockito.anyInt())).thenReturn(orderResponse);

    mockMvc.perform(get(endpoint, bookId)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(orderResponse.getId()))
        .andExpect(jsonPath("$.customerId").value(orderResponse.getCustomerId()))
        .andExpect(jsonPath("$.totalPrice").value(orderResponse.getTotalPrice()))
        .andExpect(jsonPath("$.orderDate").value(orderResponse.getOrderDate()))
        .andExpect(jsonPath("$.orderDetails[0].bookId").value(orderResponse.getOrderDetails().get(0).getBookId()))
        .andExpect(jsonPath("$.orderDetails[0].count").value(orderResponse.getOrderDetails().get(0).getCount()))
        .andExpect(jsonPath("$.orderDetails[0].price").value(orderResponse.getOrderDetails().get(0).getPrice()))
        .andReturn();
  }

  @ParameterizedTest
  @MethodSource("provideOrderResponseList")
  public void itShouldFindAllOrders(List<Order> orderList, OrderListResponse orderListResponse) throws Exception {
    final String endpoint = String.format("/api/orders");

    Mockito.when(orderService.findAll()).thenReturn(orderList);
    Mockito.when(orderHandler.findAllOrders()).thenReturn(orderListResponse);

    OrderResponse orderResponse = orderListResponse.getOrderResponseList().get(0);
    mockMvc.perform(get(endpoint)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("orderResponseList[0].id", is(orderResponse.getId())))
        .andExpect(jsonPath("orderResponseList[0].customerId", is(orderResponse.getCustomerId())))
        .andExpect(jsonPath("orderResponseList[0].totalPrice", is(orderResponse.getTotalPrice())))
        .andExpect(jsonPath("orderResponseList[0].orderDate", is(orderResponse.getOrderDate())))
        .andExpect(jsonPath("orderResponseList[0].orderDetails[0].bookId", is(orderResponse.getOrderDetails().get(0).getBookId())))
        .andExpect(jsonPath("orderResponseList[0].orderDetails[0].count", is((orderResponse.getOrderDetails().get(0).getCount()))))
        .andExpect(jsonPath("orderResponseList[0].orderDetails[0].price", is(orderResponse.getOrderDetails().get(0).getPrice())))
        .andReturn();
  }

  @ParameterizedTest
  @MethodSource("provideOrderResponseList")
  public void itShouldFindOrdersWithGivenCustomerId(List<Order> orderList, OrderListResponse orderListResponse) throws Exception {
    final String endpoint = String.format("/api/orders/customer/{customerId}");
    int customerId = 1;

    Mockito.when(orderService.findOrdersByCustomerId(Mockito.anyInt())).thenReturn(orderList);
    Mockito.when(orderHandler.findOrdersByCustomerId(Mockito.anyInt())).thenReturn(orderListResponse);

    OrderResponse orderResponse = orderListResponse.getOrderResponseList().get(0);
    mockMvc.perform(get(endpoint, customerId)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("orderResponseList[0].id", is(orderResponse.getId())))
        .andExpect(jsonPath("orderResponseList[0].customerId", is(orderResponse.getCustomerId())))
        .andExpect(jsonPath("orderResponseList[0].totalPrice", is(orderResponse.getTotalPrice())))
        .andExpect(jsonPath("orderResponseList[0].orderDate", is(orderResponse.getOrderDate())))
        .andExpect(jsonPath("orderResponseList[0].orderDetails[0].bookId", is(orderResponse.getOrderDetails().get(0).getBookId())))
        .andExpect(jsonPath("orderResponseList[0].orderDetails[0].count", is((orderResponse.getOrderDetails().get(0).getCount()))))
        .andExpect(jsonPath("orderResponseList[0].orderDetails[0].price", is(orderResponse.getOrderDetails().get(0).getPrice())))
        .andReturn();
  }

  @ParameterizedTest
  @MethodSource("provideOrder")
  public void itShouldDeleteOrderWithGivenId(Order order) throws Exception {
    final String endpoint = String.format("/api/orders/{id}");
    int orderId = 1;

    Mockito.when(orderService.findById(Mockito.anyInt())).thenReturn(order);

    mockMvc.perform(delete(endpoint, orderId)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  private static Stream<Arguments> provideOrder() {
    Order order = new Order();
    order.setId(1);
    order.setCustomerId(1);
    order.setTotalPrice(61.5);
    order.setOrderDate("01.01.2020");

    OrderDetail orderDetail = new OrderDetail();
    orderDetail.setBookId(1);
    orderDetail.setCount(2);
    orderDetail.setPrice(61.5);

    order.setOrderDetails(List.of(orderDetail));

    OrderResponse orderResponse = new OrderResponse();
    orderResponse.setId(1);
    orderResponse.setCustomerId(1);
    orderResponse.setTotalPrice(61.5);
    orderResponse.setOrderDate("01.01.2020");

    OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
    orderDetailResponse.setBookId(1);
    orderDetailResponse.setCount(2);
    orderDetailResponse.setPrice(61.5);

    orderResponse.setOrderDetails(List.of(orderDetailResponse));

    return Stream.of(
        Arguments.of(order, orderResponse)
    );
  }

  private static Stream<Arguments> provideOrderResponseList() {
    Order order = new Order();
    order.setId(1);
    order.setCustomerId(1);
    order.setTotalPrice(61.5);
    order.setOrderDate("01.01.2020");

    OrderDetail orderDetail = new OrderDetail();
    orderDetail.setBookId(1);
    orderDetail.setCount(2);
    orderDetail.setPrice(61.5);

    order.setOrderDetails(List.of(orderDetail));

    OrderResponse orderResponse = new OrderResponse();
    orderResponse.setId(1);
    orderResponse.setCustomerId(1);
    orderResponse.setTotalPrice(61.5);
    orderResponse.setOrderDate("01.01.2020");

    OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
    orderDetailResponse.setBookId(1);
    orderDetailResponse.setCount(2);
    orderDetailResponse.setPrice(61.5);

    orderResponse.setOrderDetails(List.of(orderDetailResponse));

    OrderListResponse orderListResponse = new OrderListResponse();
    orderListResponse.setOrderResponseList(List.of(orderResponse));

    return Stream.of(
        Arguments.of(List.of(order), orderListResponse)
    );
  }

  private static Stream<Arguments> provideOrderRequest() {
    OrderRequest orderRequest = new OrderRequest();
    orderRequest.setCustomerId(1);

    OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
    orderDetailRequest.setBookId(1);
    orderDetailRequest.setQty(2);

    OrderDetailRequest orderDetailRequest1 = new OrderDetailRequest();
    orderDetailRequest.setBookId(2);
    orderDetailRequest.setQty(3);

    orderRequest.setOrderDetails(List.of(orderDetailRequest, orderDetailRequest1));

    OrderResponse orderResponse = new OrderResponse();
    orderResponse.setId(1);
    orderResponse.setCustomerId(1);
    orderResponse.setTotalPrice(61.5);
    orderResponse.setOrderDate("01.01.2020");

    OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
    orderDetailResponse.setBookId(1);
    orderDetailResponse.setCount(2);
    orderDetailResponse.setPrice(21.5);

    OrderDetailResponse orderDetailResponse1 = new OrderDetailResponse();
    orderDetailResponse.setBookId(2);
    orderDetailResponse.setCount(3);
    orderDetailResponse.setPrice(40);

    orderResponse.setOrderDetails(List.of(orderDetailResponse, orderDetailResponse1));

    return Stream.of(
        Arguments.of(orderRequest, orderResponse)
    );
  }
}
