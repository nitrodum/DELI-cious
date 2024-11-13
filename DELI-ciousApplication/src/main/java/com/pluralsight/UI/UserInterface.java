package com.pluralsight.UI;

import com.pluralsight.*;
import com.pluralsight.filemanger.ReceiptFileManager;
import com.pluralsight.menu.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<Integer, Double> meatPrices = Map.of(4, 1.0, 8, 2.0, 12, 3.0);
    private static final Map<Integer, Double> cheesePrices = Map.of(4, .75, 8, 1.5, 12, 2.25);
    private static final Map<String, Double> drinkPrices = Map.of("small", 2.0, "medium", 2.5, "large", 3.0);
    private static final Map<String, List<String>> regularToppings = Map.of(
            "veggies", List.of("lettuce", "peppers", "onions", "tomatoes", "jalapenos", "cucumbers", "pickles", "guacamole", "mushrooms"),
            "sauces", List.of("mayo", "mustard", "ketchup", "ranch", "thousand islands", "vinaigrette"),
            "sides", List.of("au jus", "sauce")
    );
    private static final double CHIP_PRICE = 1.5;
    private static boolean running = true;
    private static boolean runningOrder = true;
    private static boolean runningSandwichMenu = true;
    private static boolean runningSignatureMenu = true;
    private static boolean runningViewSignature = true;
    private static boolean runningEdit = true;
    private static User user;

    public static void run(User u) {
        user = u;
        while (running) {
            homeScreen();
        }
        scanner.close();
    }

    private static void homeScreen() {
        runningOrder = true;
        StoreFront.clearOrder();
        int choice;
        if (user.getUsername().equalsIgnoreCase("guest")) {
             choice = inputNumberedChoice("""
                    ----------------------------------------------------------------------------------------------------
                    1) New Order
                    0) Exit""");

            switch (choice) {
                case 1 -> {
                    while (runningOrder) {
                        orderScreen();
                    }
                }
                case 0 -> running = false;
                default -> System.out.println("Invalid Choice");
            }
        } else {
            choice = inputNumberedChoice("""
                    ----------------------------------------------------------------------------------------------------
                    1) New Order
                    2) View Previous Orders
                    0) Exit""");

            switch (choice) {
                case 1 -> {
                    while (runningOrder) {
                        orderScreen();
                    }
                }
                case 2 -> displayPreviousOrders();
                case 0 -> running = false;
                default -> System.out.println("Invalid Choice");
            }
        }
    }

    private static void displayPreviousOrders() {
        if (user.getReceipts().isEmpty()) {
            System.out.println("No previous orders to display");
        } else {
            for (String receipt : user.getReceipts()) {
                System.out.println(ReceiptFileManager.loadReceipt(receipt));
            }
        }
    }

    private static void orderScreen() {
        int choice = inputNumberedChoice("""
                ----------------------------------------------------------------------------------------------------
                What would you like to add to the order?
                1) Add Sandwich
                2) Add Drink
                3) Add Chip
                4) Checkout
                0) Cancel Order""");

        switch (choice) {
            case 1 -> {
                runningSandwichMenu = true;
                while (runningSandwichMenu) {
                    sandwichMenu();
                }
            }
            case 2 -> addDrink();
            case 3 -> addChip();
            case 4 -> checkout();
            case 0 -> runningOrder = false;
            default -> System.out.println("Invalid Choice");
        }

    }

    private static void sandwichMenu() {
        int choice = inputNumberedChoice("""
                ----------------------------------------------------------------------------------------------------
                1) Add Signature Sandwiches
                2) Create Custom Sandwich
                """);

        switch (choice) {
            case 1 -> {
                runningSignatureMenu = true;
                while (runningSignatureMenu) {
                    addSignatureSandwich();
                }
            }
            case 2 -> addSandwich();
            default -> System.out.println("Invalid Choice");
        }
    }

    private static void addSignatureSandwich() {
        int choice = inputNumberedChoice("""
                ----------------------------------------------------------------------------------------------------
                1) BLT
                2) Philly Cheesesteak
                """);
        runningViewSignature = true;
        switch (choice) {
            case 1 -> viewSignature(new BLT());
            case 2 -> viewSignature(new PhillyCheeseSteak());
            default -> {
                System.out.println("Invalid Input");
                addSignatureSandwich();
            }
        }
    }

    private static void viewSignature(SignatureSandwich sandwich) {
        while (runningViewSignature) {
            int choice = inputNumberedChoice("----------------------------------------------------------------------------------------------------\n" +
                    sandwich +
                    """
                            \n
                            1) Add to Order
                            2) Edit Order
                            3) Return
                            """);

            switch (choice) {
                case 1 -> {
                    StoreFront.addOrder(sandwich);
                    runningSignatureMenu = false;
                    runningViewSignature = false;
                    runningSandwichMenu = false;
                }
                case 2 -> {
                    runningEdit = true;
                    while (runningEdit) {
                        editMenu(sandwich);
                    }
                }
                case 3 -> runningViewSignature = false;
                default -> System.out.println("Invalid Input");
            }
        }
    }

    private static void editMenu(SignatureSandwich sandwich) {
        int choice = inputNumberedChoice("""
                1) Add topping
                2) Remove topping
                3) Return
                """);

        switch (choice) {
            case 1 -> addTopping(sandwich);
            case 2 -> removeTopping(sandwich);
            case 3 -> runningEdit = false;
            default -> System.out.println("Invalid Input");
        }
    }

    private static void addTopping(SignatureSandwich sandwich) {
        int choice = inputNumberedChoice("""
                Choose the type of topping you would like to add:
                1) Regular Topping
                2) Sauce
                3) Side
                """);

        String regularToppingsType;
        switch (choice) {
            case 1 -> regularToppingsType = "veggies";
            case 2 -> regularToppingsType = "sauces";
            case 3 -> regularToppingsType = "sides";
            default -> regularToppingsType = null;
        }

        addRegularToppings(sandwich, regularToppingsType, regularToppings.get(regularToppingsType));
    }

    private static void removeTopping(SignatureSandwich sandwich) {
        List<String> choices = new ArrayList<>();
        for (Topping t : sandwich.getToppings()) {
            choices.add(t.getName());
        }
        String prompt = "Enter the topping you would like to remove:\n" +
                choices;
        String ans = input(prompt);

        if (choices.contains(ans.trim().toLowerCase())) {
            sandwich.removeTopping(ans);
        }
    }

    private static void addSandwich() {
        int size = 0;
        while (size == 0) {
            int choice = inputNumberedChoice("""
                What size would you like the sandwich to be in inches?
                1) 4 inches
                2) 8 inches
                3) 12 inches""");

            switch (choice) {
                case 1 -> size = 4;
                case 2 -> size = 8;
                case 3 -> size = 12;
                default -> System.out.println("Invalid Choice!");
            }
        }

        String bread = "";
        while (bread.isEmpty()) {
            int choice = inputNumberedChoice("""
                    What type of bread would you like the sandwich to be?
                    1) White
                    2) Wheat
                    3) Rye
                    4) Wrap)""");

            switch (choice) {
                case 1 -> bread = "White";
                case 2 -> bread = "Wheat";
                case 3 -> bread = "Rye";
                case 4 -> bread = "Wrap";
                default -> System.out.println("Invalid Choice!");
            }
        }

        Sandwich sandwich = new Sandwich(size, bread);

        String ans = input("Would you like to add meat to the sandwich? (y/n)");

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
        runningSandwichMenu = false;

    }

    public static void addMeat(Sandwich sandwich) {
        String meatType = "";
        while (meatType.isEmpty()) {
            int choice = inputNumberedChoice("""
                    What meat would you like?
                    1) Steak
                    2) Ham
                    3) Salami
                    4) Roast Beef
                    5) Chicken
                    6) Bacon""");

            switch (choice) {
                case 1 -> meatType = "Steak";
                case 2 -> meatType = "Ham";
                case 3 -> meatType = "Salami";
                case 4 -> meatType = "Roast Beef";
                case 5 -> meatType = "Chicken";
                case 6 -> meatType = "Bacon";
                default -> System.out.println("Invalid Choice!");
            }
        }
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
        String cheeseType = "";
        while (cheeseType.isEmpty()) {
            int choice = inputNumberedChoice("""
                    What cheese would you like?
                    1) American
                    2) Provolone
                    3) Cheddar
                    4) Swiss""");

            switch (choice) {
                case 1 -> cheeseType = "American";
                case 2 -> cheeseType = "Provolone";
                case 3 -> cheeseType = "Cheddar";
                case 4 -> cheeseType = "Swiss";
                default -> System.out.println("Invalid Choice!");
            }
        }
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

    public static void addRegularToppings(Sandwich sandwich, String regularToppingsType, List<String> regularToppingsList) {
        StringBuilder choices = new StringBuilder();

        for (String choice : regularToppingsList) {
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
        } else if (ans.trim().equalsIgnoreCase("none")) {
            choosenToppings = null;
        } else {
            choosenToppings = ans.trim().split(",");
        }

        if (choosenToppings == null) {
            return;
        }

        for (String topping : choosenToppings) {
            topping = topping.trim().toLowerCase();
            if (!regularToppingsList.contains(topping)) {
                System.out.println("Invalid topping chosen");
                addRegularToppings(sandwich, regularToppingsType, regularToppingsList);
            }
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
            String receiptFileName = ReceiptFileManager.getFileName();
            ReceiptFileManager.saveReceipt(receiptFileName, receipt.toString());
            user.addReceipt(receiptFileName);
        }

        runningOrder = false;
    }

    protected static String input(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    protected static int inputNumberedChoice(String prompt) {
        System.out.println(prompt);
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

}
