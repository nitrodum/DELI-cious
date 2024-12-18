package com.pluralsight.menu;

public abstract class Topping {
    private final String name;
    private final boolean extra;

    public Topping(String name, boolean extra) {
        this.name = name;
        this.extra = extra;
    }

    public String getName() {
        return name;
    }

    public boolean isExtra() {
        return extra;
    }

    @Override
    public String toString() {
        String isExtra = this.extra ? "Extra " : "";
        return isExtra + this.name;
    }
}
