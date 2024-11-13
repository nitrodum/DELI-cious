package com.pluralsight.menu;

import java.util.Map;

public  abstract class PremiumTopping extends Topping {
    Map<Double, Double> prices;
    private final double basePrice;

    public PremiumTopping(String name, boolean extra, double basePrice) {
        super(name, extra);
        this.basePrice = basePrice;
    }

    public double getPrice() {
        if (!isExtra()) {
            return this.basePrice;
        } else {
            return basePrice + prices.get(basePrice);
        }
    }

    public void setMap(Map<Double, Double> prices) {
        this.prices = prices;
    }

    public double getBasePrice() {
        return basePrice;
    }
}
