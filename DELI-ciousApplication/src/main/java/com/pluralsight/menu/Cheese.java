package com.pluralsight.menu;

import java.util.Map;

public class Cheese extends PremiumTopping {

    public Cheese(String name, boolean extra, double basePrice) {
        super(name, extra, basePrice);
        setMap(Map.of(.75, .3, 1.5, .6, 2.25, .9));
    }

    public Cheese(Cheese toCopy) {
        this(toCopy.getName(), toCopy.isExtra(), toCopy.getBasePrice());
    }

}
