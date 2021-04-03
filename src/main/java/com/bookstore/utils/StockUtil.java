package com.bookstore.utils;


import com.bookstore.exception.InsufficientStockException;
import com.bookstore.model.entity.Book;

public class StockUtil {

  public static void updateStock(Book book, int qty) {
    book.setStock(checkAndReturnStock(book.getStock(), qty));
  }

  private static int checkAndReturnStock(int bookStock, int qty) {
    if (bookStock >= qty) {
      return bookStock - qty;
    }
    throw new InsufficientStockException("INSUFFICIENT_STOCK_MESSAGE");
  }
}