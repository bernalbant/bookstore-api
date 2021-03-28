package com.bookstore.service;

import com.bookstore.model.entity.Order;
import com.bookstore.model.request.OrderRequest;
import java.util.List;

public interface OrderService {

  List<Order> findAll();

  Order findById(int id);

  Order save(Order order);

  void deleteById(int id);

  Order prepareOrder(OrderRequest orderRequest);

  List<Order> findOrdersByCustomerId(int customerId);
}