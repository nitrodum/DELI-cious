package com.pluralsight;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<Integer, Double> meatPrices = Map.of(4, 1.0, 8, 2.0, 12, 3.0);
    private static final Map<Integer, Double> cheesePrices = Map.of(4, .75, 8, 1.5, 12, 2.25);
    private static final Map<String, Double> drinkPrices = Map.of("small", 2.0, "medium", 2.5, "large", 3.0);
    private static final Map<String, String[]> regularToppings = Map.of(
            "veggies", new String[]{"lettuce", "peppers", "onions", "tomatoes", "jalapenos", "cucumbers", "pickles", "guacamole", "mushrooms"},
            "sauces", new String[]{"mayo", "mustard", "ketchup", "ranch", "thousand islands", "vinaigrette"},
            "sides", new String[]{"au jus", "sauce"}
    );
    private static final double CHIP_PRICE = 1.5;
    private static boolean running = true;
    private static boolean runningOrder = true;

    public static void run() {
        while (running) {
            homeScreen();
        }
        scanner.close();
    }

    private static void homeScreen() {
        System.out.println("----------------------------------------------------------------------------------------------------\n" +
                "Welcome to DELI-cous!\n" +
                "1) New Order\n" +
                "0) Exit");

        int choice = scanner.nextInt();
        scanner.nextLine();

        runningOrder = true;
        StoreFront.clearOrder();

        switch (choice) {
            case 1 -> { while (runningOrder) { orderScreen();} }
            case 0 -> running = false;
            default -> System.out.println("Invalid Choice");
        }
    }

    private static void orderScreen() {
        System.out.println("----------------------------------------------------------------------------------------------------\n" +
                "What would you like to add to the order?\n" +
                "1) Add Sandwich\n" +
                "2) Add Drink\n" +
                "3) Add Chip\n" +
                "4) Checkout\n" +
                "0) Cancel Order");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> addSandwich();
            case 2 -> addDrink();
            case 3 -> addChip();
            case 4 -> checkout();
            case 0 -> runningOrder = false;
            default -> System.out.println("Invalid Choice");
        }

    }

    public static void addSandwich() {
        System.out.println("What size would you like the sandwich to be in inches: (4/8/12)");
        int size = scanner.nextInt();
        scanner.nextLine();

        String bread = input("What type of bread would you like the sandwich to be: (White/Wheat/Rye/Wrap)");
        String ans = input("Would you like to add meat to the sandwich? (y/n)");

        Sandwich sandwich = new Sandwich(size, bread);

        if (ans.trim().equalsIgnoreCase("y")) {
            addMeat(sandwich);
        }

        ans = input("Would you like to add cheese to the sandwich? (y/n)");

        if (ans.trim().equalsIgnoreCase("y")) {
            addCheese(sandwich);
        }

        addRegularToppings(sandwich, "veggies", regularToppings.get("veggies"));
        addRegularToppings(sandwich, "sauces", regularToppings.get("sauces"));
        addRegularToppings(sandwich, "sides", regularToppings.get("sides"));

        ans = input("Would you like the sandwich toasted? (y/n)");

        if (ans.trim().equalsIgnoreCase("y")) {
            sandwich.setToasted(true);
        }

        StoreFront.addOrder(sandwich);

    }

    public static void addMeat(Sandwich sandwich) {
        String meatType = input("What meat would you like?\n" +
                "[Steak/Ham/Salami/Roast Beef/ Chicken/Bacon]");
        double meatPrice = meatPrices.get(sandwich.getSize());
        Meat meat = null;
        String ans = input("Would you like extra of this meat? (y/n)");
        if (ans.trim().equalsIgnoreCase("y")) {
            meat = new Meat(meatType, true, meatPrice);
        } else if (ans.trim().equalsIgnoreCase("n")) {
            meat = new Meat(meatType, false, meatPrice);
        }
        sandwich.addTopping(meat);
    }

    public static void addCheese(Sandwich sandwich) {
        String cheeseType = input("What cheese would you like?\n" +
                "[American/Provolone/Cheddar/Swiss]");
        double cheesePrice = cheesePrices.get(sandwich.getSize());
        Cheese cheese = null;
        String ans = input("Would you like extra of this cheese? (y/n)");
        if (ans.trim().equalsIgnoreCase("y")) {
            cheese = new Cheese(cheeseType, true, cheesePrice);
        } else if (ans.trim().equalsIgnoreCase("n")) {
            cheese = new Cheese(cheeseType, false, cheesePrice);
        }
        sandwich.addTopping(cheese);
    }

    public static void addRegularToppings(Sandwich sandwich, String regularToppingsType, String[] regularToppings) {
        StringBuilder choices = new StringBuilder();

        for (String choice : regularToppings) {
            choices.append(choice);
            choices.append(",");
        }
        choices.deleteCharAt(choices.length()-1);

        String prompt = "Would you like to add " + regularToppingsType + " to this sandwich?\n" +
                "Enter the toppings you would like separated by commas, or enter (all) for all or (none) for none\n" +
                "[" + choices + "]:";
        String ans = input(prompt);

        String[] choosenToppings = null;

        if (ans.trim().equalsIgnoreCase("all")) {
            choosenToppings = regularToppings;
        } else if (ans.trim().equalsIgnoreCase("none")) {
            choosenToppings = null;
        } else {
            choosenToppings = ans.trim().split(",");
        }

        if (choosenToppings == null) {
            return;
        }

        for (String topping : choosenToppings) {
            prompt = "Would you like extra " + topping + "(y/n)";
            ans = input(prompt);
            RegularTopping regularTopping = null;
            if (ans.trim().equalsIgnoreCase("y")) {
                regularTopping = new RegularTopping(topping, true);
            } else if (ans.trim().equalsIgnoreCase("n")) {
                regularTopping = new RegularTopping(topping, false);
            }
            sandwich.addTopping(regularTopping);
        }
    }

    public static void addDrink() {
        String name = input("What drink would you like to get?");
        String size = input("What size would you like? (Small/Medium/Large)").toLowerCase();
        String ice = input("How much ice would you like? (None/Less/Regular/More)");
        Drink drink = new Drink(name, drinkPrices.get(size), size, ice);
        StoreFront.addOrder(drink);
    }

    public static void addChip() {
        String name = input("What bag of chips would you like to get?");
        Chip chip = new Chip(name, CHIP_PRICE);
        StoreFront.addOrder(chip);
    }

    public static void checkout() {
        double total = 0;
        DecimalFormat df = new DecimalFormat(".00");
        StringBuilder receipt = new StringBuilder();
        String item;
        for (Orderable o : StoreFront.getOrders()) {
            total += o.getPrice();
            item = o + "\nPrice: $" + df.format(o.getPrice()) + "\n";
            receipt.append(item);
        }
        item = "Total: $" + df.format(total);
        receipt.append(item);
        System.out.println(receipt);

        System.out.println("Would you like to purchase this order? (Confirm/Cancel)");
        String ans = scanner.nextLine();

        if (ans.trim().equalsIgnoreCase("Confirm")) {
            ReceiptFileManager.saveReceipt(receipt.toString());
        } else {
            runningOrder = false;
        }
    }

    public static String input(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

}
