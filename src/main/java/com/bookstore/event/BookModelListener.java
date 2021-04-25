package com.bookstore.event;

import com.bookstore.model.entity.Book;
import com.bookstore.service.SequenceGeneratorServiceImpl;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
class BookModelListener extends AbstractMongoEventListener<Book> {

  private SequenceGeneratorServiceImpl sequenceGenerator;

  public BookModelListener(SequenceGeneratorServiceImpl sequenceGenerator) {
    this.sequenceGenerator = sequenceGenerator;
  }

  @Override
  public void onBeforeConvert(BeforeConvertEvent<Book> event) {
    if (event.getSource().getId() < 1) {
      event.getSource().setId(sequenceGenerator.generateSequence(Book.SEQUENCE_NAME));
    }
  }
}
