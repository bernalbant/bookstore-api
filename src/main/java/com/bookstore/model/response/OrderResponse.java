package com.bookstore.model.response;

import java.util.List;
import lombok.Data;

@Data
public class OrderResponse {

  private int id;
  private int customerId;
  private double totalPrice;
  private String orderDate;
  private String status;
  private List<OrderDetailResponse> orderDetails;
}