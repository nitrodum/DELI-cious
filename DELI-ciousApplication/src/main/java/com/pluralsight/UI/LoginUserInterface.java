package com.pluralsight.UI;

import com.pluralsight.User;
import com.pluralsight.filemanger.UserFileManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginUserInterface extends UserInterface {

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
        int choice = inputNumberedChoice("""
                ----------------------------------------------------------------------------------------------------
                Login Screen
                1) Log In
                2) Sign Up
                """);

        switch (choice) {
            case 1 -> {
                return logIn();
            }
            case 2 -> {
                return signUp();
            }
            default -> System.out.println("Invalid Input");
        }
        return null;
    }

    private static User logIn() {
        String username = input("Enter your username");
        String password = input("Enter your password");
        return UserFileManager.validateUser(username, password);
    }

    private static User signUp() {
        String username = input("Enter a username");
        String password = input("Enter a password");
        //Need to add user to user list
        User user = new User(username, password);
        UserFileManager.addNewUser(user);
        return user;
    }

}
