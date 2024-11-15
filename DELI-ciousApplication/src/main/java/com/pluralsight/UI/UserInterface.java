package com.pluralsight.UI;

import com.pluralsight.*;
import com.pluralsight.filemanger.ReceiptFileManager;
import com.pluralsight.filemanger.UserFileManager;
import com.pluralsight.menu.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean running = true;
    private static boolean runningOrder = true;
    private static boolean runningSandwichMenu = true;
    private static boolean runningSignatureMenu = true;
    private static boolean runningViewSignature = true;
    private static boolean runningEdit = true;
    private static User user;
    private static boolean isGuest;

    public static void run(User u) {
        user = u;
        isGuest = user.getUsername().equalsIgnoreCase("guest");
        while (running) {
            homeScreen();
        }
        scanner.close();
    }

    private static void homeScreen() {
        int choice;
        runningOrder = true;
        StringBuilder prompt = new StringBuilder();
        prompt.append("----------------------------------------------------------------------------------------------------");
        if (!isGuest) {
            prompt.append("\nHello ").append(user.getUsername()).append("!\n").append("Your current reward points: ").append(user.getRewardPoints());
        }
        prompt.append("\n1) New Order\n");
        if (!isGuest) {
            prompt.append("2) Resume Order\n");
            prompt.append("3) View Previous Orders\n");
        }
        prompt.append("0) Exit");
        choice = inputNumberedChoice(prompt.toString());

        switch (choice) {
            case 0:
                running = false;
                break;
            case 1:
                clearOrder();
                while (runningOrder) {
                    orderScreen();
                }
                break;
            case 2:
                StoreFront.setOrders(user.getSavedOrders());
                while (runningOrder) {
                    orderScreen();
                }
                break;
            case 3:
                if (!isGuest) {
                    displayPreviousOrders();
                    break;
                }
            default:
                System.out.println("Invalid Choice");
                break;
        }
    }

    private static void displayPreviousOrders() {
        if (user.getReceipts().isEmpty()) {
            System.out.println("No previous orders to display");
        } else {
            for (String receipt : user.getReceipts()) {
                System.out.println("*".repeat(100));
                System.out.println(ReceiptFileManager.loadReceipt(receipt));
            }
        }
    }

    private static void rewardScreen() {
        String rewardBar;
        int maxBar = 100;
        boolean runningRewardScreen = true;
        while (runningRewardScreen) {
            int bar = Math.min(user.getRewardPoints(), maxBar);
            rewardBar = "*".repeat(bar) + "-".repeat(maxBar - bar);
            System.out.println("""
                    ----------------------------------------------------------------------------------------------------
                    Reward Screen
                    """ + rewardBar +
                    "\n0        10        20        30        40        50        60        70        80        90        100\n");
            int choice = inputNumberedChoice("""
                    1) Claim Free Sandwich (100 Points)
                    2) Claim Free Drink (20 Points)
                    3) Claim Free Chips (10 Points)
                    4) Exit Reward Screen""");
            switch (choice) {
                case 1 -> sandwichReward();
                case 2 -> drinkReward();
                case 3 -> chipReward();
                case 4 -> runningRewardScreen = false;
                default -> System.out.println("Invalid Input");
            }
        }
    }

    private static void sandwichReward() {
        if (user.getRewardPoints() >= 100) {
            addSandwich(true);
            user.setRewardPoints(user.getRewardPoints() - 100);
        } else {
            System.out.println("Not enough reward points!");
        }
    }

    private static void drinkReward() {
        if (user.getRewardPoints() >= 20) {
            addDrink(true);
            user.setRewardPoints(user.getRewardPoints() - 20);
        } else {
            System.out.println("Not enough reward points!");
        }
    }

    private static void chipReward() {
        if (user.getRewardPoints() >= 10) {
            addChip(true);
            user.setRewardPoints(user.getRewardPoints() - 10);
        } else {
            System.out.println("Not enough reward points!");
        }
    }

    private static void orderScreen() {
        String rewardPrompt = (isGuest) ? "" : "5) Reward Screen\n";
        int choice = inputNumberedChoice("""
                ----------------------------------------------------------------------------------------------------
                What would you like to add to the order?
                1) Add Sandwich
                2) Add Drink
                3) Add Chip
                4) Checkout
                """ + rewardPrompt +
                "0) Cancel Order");

        switch (choice) {
            case 1 -> {
                runningSandwichMenu = true;
                while (runningSandwichMenu) {
                    sandwichMenu();
                }
            }
            case 2 -> addDrink(false);
            case 3 -> addChip(false);
            case 4 -> checkout();
            case 5 -> {
                if (!isGuest) {
                    rewardScreen();
                }
            }
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
            case 2 -> addSandwich(false);
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

        addRegularToppings(sandwich, regularToppingsType, StoreFront.regularToppings.get(regularToppingsType));
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

    private static void addSandwich(boolean reward) {
        int size = chooseSandwichSize();

        String bread = chooseBread();

        Sandwich sandwich = new Sandwich(size, bread);

        String ans = inputYesNo("Would you like to add meat to the sandwich?");

        if (ans.trim().equalsIgnoreCase("y")) {
            addMeat(sandwich);
        }

        ans = inputYesNo("Would you like to add cheese to the sandwich?");

        if (ans.trim().equalsIgnoreCase("y")) {
            addCheese(sandwich);
        }

        addRegularToppings(sandwich, "veggies", StoreFront.regularToppings.get("veggies"));
        addRegularToppings(sandwich, "sauces", StoreFront.regularToppings.get("sauces"));
        addRegularToppings(sandwich, "sides", StoreFront.regularToppings.get("sides"));

        ans = inputYesNo("Would you like the sandwich toasted?");

        if (ans.trim().equalsIgnoreCase("y")) {
            sandwich.setToasted(true);
        }

        StoreFront.addOrder(sandwich);
        runningSandwichMenu = false;

    }

    private static int chooseSandwichSize() {
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
        return size;
    }

    private static String chooseBread() {
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
        return bread;
    }

    private static void addMeat(Sandwich sandwich) {
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
        double meatPrice = StoreFront.meatPrices.get(sandwich.getSize());
        Meat meat = null;
        String ans = inputYesNo("Would you like extra of this meat?");
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
        double cheesePrice = StoreFront.cheesePrices.get(sandwich.getSize());
        Cheese cheese = null;
        String ans = inputYesNo("Would you like extra of this cheese?");
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
        choices.deleteCharAt(choices.length() - 1);

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
            prompt = "Would you like extra " + topping;
            ans = inputYesNo(prompt);
            RegularTopping regularTopping = null;
            if (ans.trim().equalsIgnoreCase("y")) {
                regularTopping = new RegularTopping(topping, true);
            } else if (ans.trim().equalsIgnoreCase("n")) {
                regularTopping = new RegularTopping(topping, false);
            }
            sandwich.addTopping(regularTopping);
        }
    }

    public static void addDrink(boolean reward) {
        String name = input("What drink would you like to get?");
        String size = input("What size would you like? (Small/Medium/Large)").toLowerCase();
        String ice = input("How much ice would you like? (None/Less/Regular/More)");
        Drink drink;
        if (reward) {
            drink = new Drink(name, StoreFront.drinkPrices.get("reward"), size, ice);
        } else {
            drink = new Drink(name, StoreFront.drinkPrices.get(size), size, ice);
        }
        StoreFront.addOrder(drink);
    }

    public static void addChip(boolean reward) {
        String name = input("What bag of chips would you like to get?");
        Chip chip;
        if (reward) {
            chip = new Chip(name, 0.0);
        } else {
            chip = new Chip(name, StoreFront.CHIP_PRICE);
        }
        StoreFront.addOrder(chip);
    }

    public static void checkout() {
        double total = 0;
        DecimalFormat df = new DecimalFormat(".00");
        StringBuilder receipt = new StringBuilder();
        String item;

        receipt.append("============================\n")
                .append("          DELI-cious\n")
                .append("      Thanks for Ordering!\n")
                .append("============================\n\n")
                .append("Order Summary:\n")
                .append("----------------------------\n");

        for (Orderable o : StoreFront.getOrders()) {
            total += o.getPrice();
            item = o + "\nPrice: $" + df.format(o.getPrice()) + "\n";
            receipt.append(item).append("\n");
        }
        item = "Total: $" + df.format(total);
        receipt.append(item);

        receipt.append("\n----------------------------\n")
                .append("\nTotal: $").append(df.format(total)).append("\n\n")
                .append("============================\n")
                .append("  Earned Reward Points: ").append((int) total).append("\n")
                .append("  Total Reward Points: ").append(user.getRewardPoints())
                .append("\n============================\n");
        System.out.println(receipt);

        String savePrompt = (isGuest) ? "\n2)" : "\n2) Save For Later\n3)";

        int choice = inputNumberedChoice("""
                Would you like to purchase this order?
                1) Confirm Purchase"""
                + savePrompt +
                " Cancel");

        switch (choice) {
            case 1:
                String receiptFileName = ReceiptFileManager.getFileName();
                ReceiptFileManager.saveReceipt(receiptFileName, receipt.toString());
                user.addReceipt(receiptFileName);
                addRewards(total);
                StoreFront.clearOrder();
            case 2:
                user.setSavedOrders(StoreFront.getOrders());
                if (!isGuest) {
                    UserFileManager.saveUserData();
                }
            case 3:
                System.out.println("Exiting Checkout!");
                break;
            default:
                System.out.println("Invalid Input");
                break;
        }
        runningOrder = false;
    }

    private static void addRewards(double price) {
        int rewards = user.getRewardPoints() + (int) (price);
        user.setRewardPoints(rewards);
    }

    private static void clearOrder() {
        for (Orderable o : StoreFront.getOrders()) {
            if (o.getPrice() == 0) {
                if (o instanceof Sandwich s) {
                    s.setReward(false);
                    addRewards(100);
                } else if (o instanceof Drink d) {
                    addRewards(20);
                } else {
                    addRewards(10);
                }
            }
        }
        StoreFront.clearOrder();
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

    public static String inputYesNo(String prompt) {
        System.out.println(prompt + " (y/n)");
        String input = scanner.nextLine().trim().toLowerCase();
        while (!input.equals("y") && !input.equals("n")) {
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
            input = scanner.nextLine().trim().toLowerCase();
        }
        return input;
    }

}
