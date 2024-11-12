package com.pluralsight.UI;

import com.pluralsight.User;
import com.pluralsight.filemanger.UserFileManager;

public class LoginUserInterface extends UserInterface {
    public static boolean runningLoginScreen = true;

    public static User runLogin() {
        int choice = inputNumberedChoice("""
                ----------------------------------------------------------------------------------------------------
                Welcome to DELI-cous!
                1) User Login
                2) Guest Login
                """);

        switch (choice) {
            case 1 -> {
                return userLoginScreen();
            }
            case 2 -> {
                return new User("Guest");
            }
            default -> System.out.println("Invalid Input");
        }
        return null;
    }

    private static User userLoginScreen() {
        UserFileManager.loadUserData();
        User user = null;
        while (runningLoginScreen) {
            int choice = inputNumberedChoice("""
                    ----------------------------------------------------------------------------------------------------
                    Login Screen
                    1) Log In
                    2) Sign Up
                    """);

            switch (choice) {
                case 1 -> {
                    user = logIn();
                }
                case 2 -> {
                    user = signUp();
                }
                default -> System.out.println("Invalid Input");
            }
        }
        return user;
    }

    private static User logIn() {
        String username = input("Enter your username");
        String password = input("Enter your password");
        User user = UserFileManager.validateUser(username, password);
        if (user != null) {
            runningLoginScreen = false;
        }
        return user;
    }

    private static User signUp() {
        String username = input("Enter a username");
        String password = input("Enter a password");
        //Need to add user to user list
        User user = new User(username, password);
        UserFileManager.addNewUser(user);
        runningLoginScreen = false;
        return user;
    }

}
