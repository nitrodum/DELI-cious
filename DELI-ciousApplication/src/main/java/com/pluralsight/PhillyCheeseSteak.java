package com.pluralsight;

public class PhillyCheeseSteak extends Sandwich {
    public PhillyCheeseSteak() {
        super(8, "White");
        Meat steak = new Meat("Steak", false, 2.0);
        Cheese american = new Cheese("American", false, 1.5);
        addTopping(steak);
        addTopping(american);
        RegularTopping peppers = new RegularTopping("Peppers", false);
        RegularTopping mayo = new RegularTopping("Mayo", false);
        addTopping(peppers);
        addTopping(mayo);
        setToasted(true);
    }
}
