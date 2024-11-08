package com.pluralsight;

import java.util.ArrayList;
import java.util.List;

public class Sandwich implements Orderable {
    private final int size;
    private final String bread;
    private final List<Topping> toppings = new ArrayList<>();
    private boolean toasted;

    public Sandwich(int size, String bread) {
        this.size = size;
        this.bread = bread;
    }

    @Override
    public double getPrice() {
        return 0;
    }

    public int getSize() {
        return size;
    }

    public String getBread() {
        return bread;
    }

    public List<Topping> getToppings() {
        List<Topping> copy = new ArrayList<>();
        for (Topping t : this.toppings) {
            if (t instanceof Meat m) {
                copy.add(new Meat(m));
            } else if (t instanceof Cheese c) {
                copy.add(new Cheese(c));
            } else if (t instanceof RegularTopping rt) {
                copy.add(new RegularTopping(rt));
            }
        }
        return copy;
    }

    public void addTopping(Topping t) {
        toppings.add(t);
    }

    public boolean isToasted() {
        return toasted;
    }

    public void setToasted(boolean toasted) {
        this.toasted = toasted;
    }
}
