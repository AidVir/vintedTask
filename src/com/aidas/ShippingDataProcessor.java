package com.aidas;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class ShippingDataProcessor {
  private final ShippingPriceCalculator shippingPriceCalculator;

  int largePackageCount = 0;
  BigDecimal maxDiscount = Constants.MAX_DISCOUNT;
  int currentMonth = -1;

  public ShippingDataProcessor(ShippingPriceCalculator shippingPriceCalculator) {
    this.shippingPriceCalculator = shippingPriceCalculator;
  }

  public void processFile(String fileName) {
    try {
      Scanner scanner = new Scanner(new File(fileName));

      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();

        try {
          String[] tokens = line.split("\\s+");
          String dateString = tokens[0];
          String packageSizeString = tokens[1];
          String providerString = tokens[2];

          int month = LocalDate.parse(dateString).getMonthValue();
          if (currentMonth != month) {
            largePackageCount = 0;
            maxDiscount = Constants.MAX_DISCOUNT;
            currentMonth = month;
          }

          if (PackageSize.valueOf(packageSizeString) == PackageSize.L
            && Provider.valueOf(providerString) == Provider.LP) {
            largePackageCount++;
          }

          processShippingRecord(dateString, packageSizeString, providerString);
        } catch (Exception e) {
          // Any records that fail to be parsed will be ignored
          System.out.println(line + " Ignored");
        }
      }

      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void processShippingRecord(String dateString, String packageSizeString, String providerString) {
    BigDecimal standardPrice = shippingPriceCalculator.getStandardPrice(packageSizeString, providerString);
    BigDecimal discount = shippingPriceCalculator.getFinalDiscount(packageSizeString, largePackageCount, maxDiscount, standardPrice);

    maxDiscount = maxDiscount.subtract(discount);
    BigDecimal discountedPrice = standardPrice.subtract(discount);

    printResults(dateString, packageSizeString, providerString, discount, discountedPrice);

  }

  private void printResults(String dateString, String packageSizeString, String providerString, BigDecimal discount, BigDecimal discountedPrice) {
    System.out.println(dateString + " " + packageSizeString + " " + providerString + " " + Constants.DECIMAL_FORMAT.format(discountedPrice) + " " + getDiscountString(discount));
  }

  private String getDiscountString(BigDecimal discount) {
    if (discount.equals(BigDecimal.ZERO)) {
      return "-";
    }

    return Constants.DECIMAL_FORMAT.format(discount);
  }
}