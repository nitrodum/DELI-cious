package com.pluralsight;

public class PhillyCheeseSteak extends SignatureSandwich {
    public PhillyCheeseSteak() {
        super(8, "White");
        this.name = "Philly Cheesesteak";
        Meat steak = new Meat("Steak", false, 2.0);
        Cheese american = new Cheese("American", false, 1.5);
        addTopping(steak);
        addTopping(american);
        RegularTopping peppers = new RegularTopping("peppers", false);
        RegularTopping mayo = new RegularTopping("mayo", false);
        addTopping(peppers);
        addTopping(mayo);
        setToasted(true);
    }

}
