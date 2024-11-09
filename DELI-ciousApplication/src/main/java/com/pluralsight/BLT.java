package com.pluralsight;

public class BLT extends Sandwich {
    public BLT() {
        super(8, "White");
        Meat bacon = new Meat("Bacon", false, 2.0);
        Cheese cheddar = new Cheese("Cheddar", false, 1.5);
        addTopping(bacon);
        addTopping(cheddar);
        RegularTopping lettuce = new RegularTopping("Lettuce", false);
        RegularTopping tomato = new RegularTopping("Tomato", false);
        RegularTopping ranch = new RegularTopping("Ranch", false);
        addTopping(lettuce);
        addTopping(tomato);
        addTopping(ranch);
        setToasted(true);
    }
}
