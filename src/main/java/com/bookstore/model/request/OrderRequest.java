package com.bookstore.model.request;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequest {

  @NotNull(message = "Customer id is required")
  private int customerId;

  private List<OrderDetailRequest> orderDetails;
}
