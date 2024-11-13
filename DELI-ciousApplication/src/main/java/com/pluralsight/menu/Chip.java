package com.pluralsight.menu;

public class Chip extends ExtraProduct {
    public Chip(String name, double price) {
        super(name, price);
    }

    public String toString() {
        return "Chip: \n" + getName();
    }
}
