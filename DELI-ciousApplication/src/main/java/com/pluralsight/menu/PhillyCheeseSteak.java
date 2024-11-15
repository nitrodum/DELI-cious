package com.pluralsight.menu;

import com.pluralsight.StoreFront;

public class PhillyCheeseSteak extends SignatureSandwich {
    public PhillyCheeseSteak() {
        super(8, "White");
        this.name = "Philly Cheesesteak";
        Meat steak = new Meat("Steak", false, StoreFront.meatPrices.get(getSize()));
        Cheese american = new Cheese("American", false, StoreFront.cheesePrices.get(getSize()));
        addTopping(steak);
        addTopping(american);
        RegularTopping peppers = new RegularTopping("peppers", false);
        RegularTopping mayo = new RegularTopping("mayo", false);
        addTopping(peppers);
        addTopping(mayo);
        setToasted(true);
    }

}
