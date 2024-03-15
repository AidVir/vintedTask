package com.aidas;

import java.math.BigDecimal;

// Using Big Decimal instead of double for all the values to prevent floating point arithmetic issues
public class ShippingPriceCalculator {

  private Provider getProviderFromString(String providerString) {
    return Provider.valueOf(providerString);
  }

  private PackageSize getPackageSizeFromString(String packageSizeString) {
    return PackageSize.valueOf(packageSizeString);
  }

  private BigDecimal getPriceByProvider(Provider provider, PackageSize packageSize) {
    return switch (provider) {
      case LP -> switch (packageSize) {
        case S -> Constants.LP_SMALL_PACKAGE_PRICE;
        case M -> Constants.LP_MEDIUM_PACKAGE_PRICE;
        case L -> Constants.LP_LARGE_PACKAGE_PRICE;
      };
      case MR -> switch (packageSize) {
        case S -> Constants.MR_SMALL_PACKAGE_PRICE;
        case M -> Constants.MR_MEDIUM_PACKAGE_PRICE;
        case L -> Constants.MR_LARGE_PACKAGE_PRICE;
      };
    };
  }


  private BigDecimal getTotalDiscount(BigDecimal standardPrice, String packageSizeString, int largePackageCount) {
    PackageSize packageSize = getPackageSizeFromString(packageSizeString);
    BigDecimal discount = BigDecimal.ZERO;

    if (packageSize == PackageSize.S) {
      discount = standardPrice.subtract(getSmallPackagePrice(standardPrice));
    }

    if (packageSize == PackageSize.L && largePackageCount == Constants.LP_FREE_LARGE_PACKAGE_COUNT) {
      discount = standardPrice;
    }

    return discount;
  }

  public BigDecimal getFinalDiscount(String packageSizeString, int largePackageCount, BigDecimal maxDiscount, BigDecimal standardPrice) {
    BigDecimal discount = getTotalDiscount(standardPrice, packageSizeString, largePackageCount);

    if (discount.compareTo(maxDiscount) > 0) {
      discount = maxDiscount;
    }
    return discount;
  }

  public BigDecimal getStandardPrice(String packageSizeString, String providerString) {
    Provider provider = getProviderFromString(providerString);
    PackageSize packageSize = getPackageSizeFromString(packageSizeString);
    return getPriceByProvider(provider, packageSize);
  }

  private BigDecimal getSmallPackagePrice(BigDecimal standardPrice) {
    BigDecimal min = standardPrice;
    for (Provider providerEnum : Provider.values()) {
      BigDecimal providerPrice = getPriceByProvider(providerEnum, PackageSize.S);
      if (providerPrice.compareTo(min) < 0) {
        min = providerPrice;
      }
    }
    return min;
  }
}
