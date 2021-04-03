package com.bookstore.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

  public static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

  public static String getOrderDateFormat() {
    return new SimpleDateFormat(DATE_FORMAT).format(new Date());
  }
}