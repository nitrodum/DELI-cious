package com.pluralsight.menu;

import java.util.Map;

public class Meat extends PremiumTopping {

    public Meat(String name, boolean extra, double basePrice) {
        super(name, extra, basePrice);
        setMap(Map.of(1.0, .5, 2.0, 1.0, 3.0, 1.5));
    }

    public Meat(Meat toCopy) {
        this(toCopy.getName(), toCopy.isExtra(), toCopy.getBasePrice());
    }
}
