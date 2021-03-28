package com.bookstore.model.response;

import java.util.List;
import lombok.Data;

@Data
public class BookListResponse {

  private List<BookResponse> bookResponses;
}
