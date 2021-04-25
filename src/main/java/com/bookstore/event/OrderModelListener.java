package com.bookstore.event;

import com.bookstore.model.entity.Order;
import com.bookstore.service.SequenceGeneratorServiceImpl;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
class OrderModelListener extends AbstractMongoEventListener<Order> {

  private SequenceGeneratorServiceImpl sequenceGenerator;

  public OrderModelListener(SequenceGeneratorServiceImpl sequenceGenerator) {
    this.sequenceGenerator = sequenceGenerator;
  }

  @Override
  public void onBeforeConvert(BeforeConvertEvent<Order> event) {
    if (event.getSource().getId() < 1) {
      event.getSource().setId(sequenceGenerator.generateSequence(Order.SEQUENCE_NAME));
    }
  }
}
