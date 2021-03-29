package com.bookstore.controller;

import com.bookstore.handler.OrderHandler;
import com.bookstore.model.request.OrderRequest;
import com.bookstore.model.response.OrderListResponse;
import com.bookstore.model.response.OrderResponse;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  private OrderHandler orderHandler;

  public OrderController(OrderHandler orderHandler) {
    this.orderHandler = orderHandler;
  }

  @GetMapping
  public OrderListResponse findAllOrders() {
    return orderHandler.findAllOrders();
  }

  @GetMapping("/{id}")
  public OrderResponse findOrderById(@PathVariable int id) {
    return orderHandler.findOrderById(id);
  }

  @GetMapping("/customer/{customerId}")
  public OrderListResponse findOrdersByCustomerId(@PathVariable int customerId) {
    return orderHandler.findOrdersByCustomerId(customerId);
  }

  @PostMapping
  public OrderResponse createOrder(@Valid @RequestBody OrderRequest orderRequest) {
    return orderHandler.createOrder(orderRequest);
  }

  @DeleteMapping("/{id}")
  public void deleteOrder(@PathVariable int id) {
    orderHandler.deleteOrder(id);
  }
}