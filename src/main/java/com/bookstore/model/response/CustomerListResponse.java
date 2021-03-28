package com.bookstore.model.response;

import java.util.List;
import lombok.Data;

@Data
public class CustomerListResponse {

  private List<CustomerResponse> customerResponseList;
}
