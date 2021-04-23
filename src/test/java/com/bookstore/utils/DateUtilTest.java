package com.bookstore.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DateUtilTest {

  @Test
  public void itShouldGetNowDateWithSpecifiedFormat() {
    String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";
    String orderDateFormat = DateUtil.getOrderDateFormat();

    Date now = new Date();
    String formattedDate = new SimpleDateFormat(dateFormat).format(now).split("'T'")[0];

    Assertions.assertThat(orderDateFormat.split("'T'")[0]).isEqualTo(formattedDate);
  }
}
