package com.pluralsight.menu;

import java.util.ArrayList;
import java.util.List;

public class Sandwich implements Orderable {
    private final int size;
    private final String bread;
    private final List<Topping> toppings = new ArrayList<>();
    private boolean toasted;
    private boolean reward;

    public Sandwich(int size, String bread) {
        this.size = size;
        this.bread = bread;
        this.reward = false;
    }

    @Override
    public double getPrice() {
        if (reward) {
            return 0;
        }
        double price = 0;
        if (size == 4) {
            price = 5.5;
        } else if (size == 8) {
            price = 7.0;
        } else {
            price = 8.5;
        }

        for (Topping topping : toppings) {
            if (topping instanceof PremiumTopping p) {
                price += p.getPrice();
            }
        }
        return price;
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

    public void removeTopping(String name) {
        Topping toRemove = null;
        for (Topping topping : toppings) {
            if (topping.getName().equalsIgnoreCase(name)) {
                toRemove = topping;
                break;
            }
        }
        toppings.remove(toRemove);
    }

    public boolean isToasted() {
        return toasted;
    }

    public void setToasted(boolean toasted) {
        this.toasted = toasted;
    }


    public void setReward(boolean reward) { this.reward = reward;}

    @Override
    public String toString() {
        String isToasted = toasted ? "Toasted" : "Not Toasted";
        StringBuilder toppingString = new StringBuilder();

        for (Topping t : toppings) {
            toppingString.append(t).append(", ");
        }
        toppingString.replace(toppingString.length()-2, toppingString.length()-1, "");
        return "Sandwich:\n" +
                "Size: " + size + "\"\n" +
                "Bread: " + bread + '\n' +
                "Toppings: " + toppingString + "\n" +
                isToasted;
    }
}
