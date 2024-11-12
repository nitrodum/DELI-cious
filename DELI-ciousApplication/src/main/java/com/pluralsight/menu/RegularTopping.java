package com.pluralsight.menu;

public class RegularTopping extends Topping {

    public RegularTopping(String name, boolean extra) {
        super(name, extra);
    }

    public RegularTopping(RegularTopping toCopy) {
        this(toCopy.getName(), toCopy.isExtra());
    }
}
