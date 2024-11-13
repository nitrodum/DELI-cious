package com.pluralsight.menu;

public class SignatureSandwich extends Sandwich {
    protected String name;

    public SignatureSandwich(int size, String bread) {
        super(size, bread);
    }

    @Override
    public String toString() {
        return this.name + " " + super.toString();
    }

}
