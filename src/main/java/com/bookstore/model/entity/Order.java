package com.bookstore.model.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {

  @Transient
  public static final String SEQUENCE_NAME = "order_sequence";

  @Id
  private int id;
  private int customerId;
  private double totalPrice;
  private String orderDate;
  private int status;
  private List<OrderDetail> orderDetails;
}
