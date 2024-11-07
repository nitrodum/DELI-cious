package com.pluralsight;

import java.util.Scanner;

public class UserInterface {
    private static final Scanner scanner = new Scanner(System.in);
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
            case 1 -> System.out.println("Not implemented");
            case 2 -> System.out.println("Not implemented");
            case 3 -> System.out.println("Not implemented");
            case 4 -> System.out.println("Not implemented");
            case 0 -> runningOrder = false;
            default -> System.out.println("Invalid Choice");
        }

    }
}
