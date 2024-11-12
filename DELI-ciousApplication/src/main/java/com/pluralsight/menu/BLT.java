package com.pluralsight.menu;

public class BLT extends SignatureSandwich {
    public BLT() {
        super(8, "White");
        this.name = "BLT";
        Meat bacon = new Meat("bacon", false, 2.0);
        Cheese cheddar = new Cheese("cheddar", false, 1.5);
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
