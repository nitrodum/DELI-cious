package com.pluralsight;

import java.util.ArrayList;
import java.util.List;

public class StoreFront {
    private static final List<Orderable> orders = new ArrayList<>();

    public static void addOrder(Orderable o) {
        orders.add(o);
    }

}
