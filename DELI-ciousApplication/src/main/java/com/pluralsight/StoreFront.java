package com.pluralsight;

import com.pluralsight.menu.Orderable;

import java.util.ArrayList;
import java.util.List;

public class StoreFront {
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
