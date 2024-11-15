package com.pluralsight.menu;

import com.pluralsight.StoreFront;

public class BLT extends SignatureSandwich {
    public BLT() {
        super(8, "White");
        this.name = "BLT";
        Meat bacon = new Meat("bacon", false, StoreFront.meatPrices.get(getSize()));
        Cheese cheddar = new Cheese("cheddar", false, StoreFront.cheesePrices.get(getSize()));
        addTopping(bacon);
        addTopping(cheddar);
        RegularTopping lettuce = new RegularTopping("lettuce", false);
        RegularTopping tomato = new RegularTopping("tomato", false);
        RegularTopping ranch = new RegularTopping("ranch", false);
        addTopping(lettuce);
        addTopping(tomato);
        addTopping(ranch);
        setToasted(true);
    }

}
