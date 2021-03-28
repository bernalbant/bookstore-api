package com.bookstore.service;

import com.bookstore.model.entity.Order;
import com.bookstore.model.request.OrderRequest;
import com.bookstore.repository.OrderRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

  private OrderRepository orderRepository;
  private CustomerService customerService;

  public OrderServiceImpl(OrderRepository orderRepository,
                          CustomerService customerService) {
    this.orderRepository = orderRepository;
    this.customerService = customerService;
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
      throw new RuntimeException();
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
    return null;
  }

  @Override
  public List<Order> findOrdersByCustomerId(int customerId) {
    return orderRepository.findOrdersByCustomerId(customerId);
  }
}