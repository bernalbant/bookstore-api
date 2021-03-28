package com.bookstore.service;

import com.bookstore.model.entity.Customer;
import java.util.List;

public interface CustomerService {

  List<Customer> findAll();

  Customer findById(int id);

  Customer save(Customer customer);

  void deleteById(int id);
}