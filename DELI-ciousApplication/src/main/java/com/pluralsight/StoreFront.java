package com.pluralsight;

import com.pluralsight.menu.Orderable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StoreFront {
    public static final Map<Integer, Double> meatPrices = Map.of(4, 1.0, 8, 2.0, 12, 3.0);
    public static final Map<Integer, Double> cheesePrices = Map.of(4, .75, 8, 1.5, 12, 2.25);
    public static final Map<String, Double> drinkPrices = Map.of("reward", 0.0, "small", 2.0, "medium", 2.5, "large", 3.0);
    public static final Map<String, List<String>> regularToppings = Map.of(
            "veggies", List.of("lettuce", "peppers", "onions", "tomatoes", "jalapenos", "cucumbers", "pickles", "guacamole", "mushrooms"),
            "sauces", List.of("mayo", "mustard", "ketchup", "ranch", "thousand islands", "vinaigrette"),
            "sides", List.of("au jus", "sauce")
    );
    public static final double CHIP_PRICE = 1.5;
    private static List<Orderable> orders = new ArrayList<>();

    public static void addOrder(Orderable o) {
        orders.add(o);
    }

    public static List<Orderable> getOrders() {
        return orders;
    }

    public static void setOrders(List<Orderable> toCopy) {
        orders = toCopy;
    }

    public static void clearOrder() {
        orders.clear();
    }

}
