package com.bookstore.service.integrationtests;

import static org.assertj.core.api.Assertions.assertThat;

import com.bookstore.exception.NotFoundException;
import com.bookstore.model.entity.Book;
import com.bookstore.model.entity.Customer;
import com.bookstore.model.entity.Order;
import com.bookstore.model.entity.OrderDetail;
import com.bookstore.model.request.OrderDetailRequest;
import com.bookstore.model.request.OrderRequest;
import com.bookstore.service.BookService;
import com.bookstore.service.CustomerService;
import com.bookstore.service.OrderService;
import com.bookstore.service.integrationtests.base.MongoDbBaseContainer;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
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
public class OrderServiceImpIT extends MongoDbBaseContainer {

  @Autowired
  private OrderService orderService;

  @Autowired
  private BookService bookService;

  @Autowired
  private CustomerService customerService;

  @BeforeEach
  public void setUp() {
    OrderDetail orderDetail = new OrderDetail();
    orderDetail.setBookId(12);
    orderDetail.setCount(42);
    orderDetail.setPrice(22.5);

    OrderDetail secondOrderDetail = new OrderDetail();
    secondOrderDetail.setBookId(13);
    secondOrderDetail.setCount(33);
    secondOrderDetail.setPrice(68.0);

    List<OrderDetail> orderDetailList = List.of(orderDetail);
    List<OrderDetail> secondOrderDetailList = List.of(secondOrderDetail);

    orderService.save(new Order(1, 123, 12.0, "12.02.20", 1, orderDetailList));
    orderService.save(new Order(2, 76, 42.0, "02.06.20", 1, secondOrderDetailList));
  }

  @Test
  @org.junit.jupiter.api.Order(1)
  public void itShouldFindSavedFirstOrder() {
    var order = orderService.findById(1);

    assertThat(order.getId()).isEqualTo(1);
    assertThat(order.getCustomerId()).isEqualTo(123);
    assertThat(order.getTotalPrice()).isEqualTo(12.0);
    assertThat(order.getOrderDate()).isEqualTo("12.02.20");
    assertThat(order.getOrderDetails().get(0)).isNotNull();
    assertThat(order.getOrderDetails().get(0).getBookId()).isEqualTo(12);
    assertThat(order.getOrderDetails().get(0).getCount()).isEqualTo(42);
    assertThat(order.getOrderDetails().get(0).getPrice()).isEqualTo(22.5);
  }

  @Test
  @org.junit.jupiter.api.Order(1)
  public void itShouldFindSavedSecondOrder() {
    var order = orderService.findById(2);

    assertThat(order.getId()).isEqualTo(2);
    assertThat(order.getCustomerId()).isEqualTo(76);
    assertThat(order.getTotalPrice()).isEqualTo(42.0);
    assertThat(order.getOrderDate()).isEqualTo("02.06.20");
    assertThat(order.getOrderDetails().get(0)).isNotNull();
    assertThat(order.getOrderDetails().get(0).getBookId()).isEqualTo(13);
    assertThat(order.getOrderDetails().get(0).getCount()).isEqualTo(33);
    assertThat(order.getOrderDetails().get(0).getPrice()).isEqualTo(68.0);
  }

  @Test
  @org.junit.jupiter.api.Order(2)
  public void itShouldFindAllOrders() {
    var order = orderService.findAll();

    assertThat(order.size()).isEqualTo(2);
    assertThat(order).isNotEmpty();
  }

  @Test
  @org.junit.jupiter.api.Order(3)
  public void itShouldThrowExceptionWhenThereIsNoOrderWithSpecified_Id() {
    var orderId = 5;
    NotFoundException notFoundException = Assertions.catchThrowableOfType(
        () -> orderService.findById(orderId), NotFoundException.class);

    assertThat(notFoundException).isNotNull();
    assertThat(notFoundException.getMessage()).isEqualTo("Order not found with the given id " + orderId);
  }

  @Test
  @org.junit.jupiter.api.Order(4)
  public void itShouldPrepareOrder() {
    OrderRequest orderRequest = new OrderRequest();
    orderRequest.setCustomerId(123);

    OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
    orderDetailRequest.setBookId(1);
    orderDetailRequest.setQty(12);

    orderRequest.setOrderDetails(List.of(orderDetailRequest));

    saveBook();
    saveCustomer();

    var order = orderService.prepareOrder(orderRequest);

    assertThat(order.getCustomerId()).isEqualTo(123);
    assertThat(order.getOrderDetails().get(0).getBookId()).isEqualTo(1);
    assertThat(order.getOrderDetails().get(0).getCount()).isEqualTo(12);
    assertThat(order.getOrderDetails().get(0).getPrice()).isEqualTo(12.0);
    assertThat(order.getOrderDate()).isNotNull();
    assertThat(order.getTotalPrice()).isEqualTo(144.0);
  }

  @Test
  @org.junit.jupiter.api.Order(5)
  public void itShouldDeleteOrderById() {
    orderService.deleteById(1);

    var remainOrder = orderService.findAll();

    assertThat(remainOrder.size()).isEqualTo(1);
    assertThat(remainOrder).isNotEmpty();
    clearCustomerData();
  }

  private void saveBook() {
    bookService.save(new Book(1, "Name of the book", "Description of the book", "Author of the book", 12.0, 20));
  }

  private void saveCustomer() {
    customerService.save(new Customer(123, "Name", "Surname", "05053332211", "name.surname@gmail.com", "address of customer"));
  }

  private void clearCustomerData() {
    customerService.deleteById(123);
  }
}
