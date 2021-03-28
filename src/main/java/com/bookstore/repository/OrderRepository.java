package com.bookstore.repository;

import com.bookstore.model.entity.Order;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, Integer> {

  List<Order> findOrdersByCustomerId(int customerId);
}
