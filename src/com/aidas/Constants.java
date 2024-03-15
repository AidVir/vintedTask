package com.aidas;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Constants {
  public static final BigDecimal MAX_DISCOUNT = BigDecimal.valueOf(10);
  public static final int LP_FREE_LARGE_PACKAGE_COUNT = 3;
  public static final BigDecimal LP_LARGE_PACKAGE_PRICE = BigDecimal.valueOf(6.9);
  public static final BigDecimal LP_MEDIUM_PACKAGE_PRICE = BigDecimal.valueOf(4.9);
  public static final BigDecimal LP_SMALL_PACKAGE_PRICE = BigDecimal.valueOf(1.5);
  public static final BigDecimal MR_LARGE_PACKAGE_PRICE = BigDecimal.valueOf(4);
  public static final BigDecimal MR_MEDIUM_PACKAGE_PRICE = BigDecimal.valueOf(3);
  public static final BigDecimal MR_SMALL_PACKAGE_PRICE = BigDecimal.valueOf(2);
  public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.00");
}
