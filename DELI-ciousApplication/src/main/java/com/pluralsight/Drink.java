package com.pluralsight;

public class Drink extends ExtraProduct {
    private String size;
    private String ice;

    public Drink(String name, double price, String size, String ice) {
        super(name, price);
        this.size = size;
        this.ice = ice;
    }

    @Override
    public String toString() {
        return "Drink: \n" + getName() +
                "\nSize: " + this.size +
                "\nIce: " + this.ice;
    }
}
