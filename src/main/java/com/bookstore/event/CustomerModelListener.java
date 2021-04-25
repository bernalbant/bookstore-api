package com.bookstore.event;

import com.bookstore.model.entity.Customer;
import com.bookstore.service.SequenceGeneratorServiceImpl;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
class CustomerModelListener extends AbstractMongoEventListener<Customer> {

  private SequenceGeneratorServiceImpl sequenceGenerator;

  public CustomerModelListener(SequenceGeneratorServiceImpl sequenceGenerator) {
    this.sequenceGenerator = sequenceGenerator;
  }

  @Override
  public void onBeforeConvert(BeforeConvertEvent<Customer> event) {
    if (event.getSource().getId() < 1) {
      event.getSource().setId(sequenceGenerator.generateSequence(Customer.SEQUENCE_NAME));
    }
  }
}
