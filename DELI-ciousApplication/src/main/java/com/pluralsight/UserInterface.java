package com.pluralsight;

import java.util.Scanner;

public class UserInterface {
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean running = true;

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
        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> System.out.println("Not implemented");
            case "0" -> running = false;
            default -> System.out.println("Invalid Choice");
        }
    }
}
