package com.pluralsight.menu;

public abstract class ExtraProduct implements Orderable {
    private final String name;
    private final double price;

    public ExtraProduct(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    public String getName() {
        return name;
    }
}
