package com.aidas.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.aidas.ShippingPriceCalculator;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShippingPriceCalculatorTests {

  private ShippingPriceCalculator calculator;

  @BeforeEach
  public void setUp() {
    calculator = new ShippingPriceCalculator();
  }

  @Test
  public void testGetStandardPrice() {
    BigDecimal expectedPrice = BigDecimal.valueOf(6.9);
    BigDecimal actualPrice = calculator.getStandardPrice("L", "LP");
    assertEquals(expectedPrice, actualPrice);
  }

  @Test
  public void testGetFinalDiscountNoDiscount() {
    BigDecimal standardPrice = BigDecimal.valueOf(10);
    int largePackageCount = 5;
    BigDecimal maxDiscount = BigDecimal.ZERO;
    BigDecimal expectedDiscount = BigDecimal.ZERO;
    BigDecimal actualDiscount = calculator.getFinalDiscount("S", largePackageCount, maxDiscount, standardPrice);
    assertEquals(expectedDiscount, actualDiscount);
  }

  @Test
  public void testGetFinalDiscountSmallPackage() {
    BigDecimal standardPrice = BigDecimal.valueOf(2);
    int largePackageCount = 3;
    BigDecimal maxDiscount = BigDecimal.valueOf(10);
    BigDecimal expectedDiscount = BigDecimal.valueOf(0.5);
    BigDecimal actualDiscount = calculator.getFinalDiscount("S", largePackageCount, maxDiscount, standardPrice);
    assertEquals(expectedDiscount, actualDiscount);
  }

  @Test
  public void testGetFinalDiscountLargePackage() {
    BigDecimal standardPrice = BigDecimal.valueOf(10);
    int largePackageCount = 3;
    BigDecimal maxDiscount = BigDecimal.valueOf(10);
    BigDecimal expectedDiscount = BigDecimal.valueOf(10);
    BigDecimal actualDiscount = calculator.getFinalDiscount("L", largePackageCount, maxDiscount, standardPrice);
    assertEquals(expectedDiscount, actualDiscount);
  }

  @Test
  public void testGetFinalDiscountPartialDiscount() {
    BigDecimal standardPrice = BigDecimal.valueOf(10);
    int largePackageCount = 3;
    BigDecimal maxDiscount = BigDecimal.valueOf(5);
    BigDecimal expectedDiscount = BigDecimal.valueOf(5);
    BigDecimal actualDiscount = calculator.getFinalDiscount("L", largePackageCount, maxDiscount, standardPrice);
    assertEquals(expectedDiscount, actualDiscount);
  }
}