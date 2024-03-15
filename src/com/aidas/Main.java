package com.aidas;

public class Main {
    public static void main(String[] args) {
        ShippingDataProcessor shippingDataProcessor = new ShippingDataProcessor(new ShippingPriceCalculator());
        shippingDataProcessor.processFile(args[0]);
    }
}
