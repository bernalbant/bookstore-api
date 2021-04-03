package com.bookstore.service;

import com.bookstore.exception.NotFoundException;
import com.bookstore.model.entity.Order;
import com.bookstore.model.enums.OrderStatus;
import com.bookstore.model.request.OrderRequest;
import com.bookstore.repository.OrderRepository;
import com.bookstore.utils.BookUtil;
import com.bookstore.utils.DateUtil;
import com.bookstore.utils.OrderDetailsUtil;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

  private OrderRepository orderRepository;
  private CustomerService customerService;
  private OrderDetailsUtil orderDetailsUtil;
  private BookUtil bookUtil;

  public OrderServiceImpl(OrderRepository orderRepository,
                          CustomerService customerService,
                          OrderDetailsUtil orderDetailsUtil,
                          BookUtil bookUtil) {
    this.orderRepository = orderRepository;
    this.customerService = customerService;
    this.orderDetailsUtil = orderDetailsUtil;
    this.bookUtil = bookUtil;
  }

  @Override
  public List<Order> findAll() {
    return orderRepository.findAll();
  }

  @Override
  public Order findById(int id) {
    Optional<Order> order = orderRepository.findById(id);

    if (order.isPresent()) {
      return order.get();
    } else {
      throw new NotFoundException("ORDER_NOT_FOUND_MESSAGE" + id);
    }
  }

  @Override
  public Order save(Order order) {
    return orderRepository.save(order);
  }

  @Override
  public void deleteById(int id) {
    orderRepository.deleteById(id);
  }

  @Override
  public Order prepareOrder(OrderRequest orderRequest) {
    Order order = new Order();

    if (Objects.nonNull(orderRequest)) {
      order.setCustomerId(customerService.findById(orderRequest.getCustomerId()).getId());
      if (Objects.nonNull(orderRequest.getOrderDetails())) {
        order.setOrderDetails(orderDetailsUtil.getOrderDetails(orderRequest));
      }
      order.setStatus(OrderStatus.ORDERED.getValue());
      order.setOrderDate(DateUtil.getOrderDateFormat());
      order.setTotalPrice(bookUtil.getTotalBookPrice(orderRequest));
    }
    return order;
  }

  @Override
  public List<Order> findOrdersByCustomerId(int customerId) {
    return orderRepository.findOrdersByCustomerId(customerId);
  }
}