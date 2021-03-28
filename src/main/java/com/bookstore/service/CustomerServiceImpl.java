package com.bookstore.service;

import com.bookstore.model.entity.Customer;
import com.bookstore.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;


@Service
public class CustomerServiceImpl implements CustomerService {

  private CustomerRepository customerRepository;

  public CustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public List<Customer> findAll() {
    return customerRepository.findAll();
  }

  @Override
  public Customer findById(int id) {
    Optional<Customer> customer = customerRepository.findById(id);

    if (customer.isPresent()) {
      return customer.get();
    } else {
      throw new RuntimeException();
    }
  }

  @Override
  public Customer save(Customer customer) {
    return customerRepository.save(customer);
  }

  @Override
  public void deleteById(int id) {
    customerRepository.deleteById(id);
  }
}