package com.bookstore.model.response;

import java.util.List;
import lombok.Data;

@Data
public class OrderListResponse {

  private List<OrderResponse> orderResponseList;
}
