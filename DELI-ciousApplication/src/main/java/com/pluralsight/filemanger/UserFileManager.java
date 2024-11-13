package com.pluralsight.filemanger;

import com.pluralsight.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UserFileManager {
    private static List<User> users = new ArrayList<>();

    public static void addNewUser(User user) {
        String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        users.add(user);
    }

    public static void loadUserData() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("user_data.txt"));
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                String[] data = input.split("\\|");
                User user;

                if (data.length < 4) {
                    user = new User(data[0], data[1]);
                } else {
                    String[] receipts = data[3].split(",");
                    user = new User(data[0], data[1], Integer.parseInt(data[2]), receipts);
                }
                users.add(user);
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Error Reading File");
            e.printStackTrace();
        }
    }

    public static void saveUserData() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("user_data.txt", false));
            for (User u : users) {
                String receipts = String.join(",", u.getReceipts());

                bufferedWriter.write(u.getUsername() + "|" + u.getPassword() + "|" + u.getRewardPoints() + "|" + receipts);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error Writing File");
        }
    }

    public static User validateUser(String username, String password) {
        String hash = null;

        for (User u : users) {
            if (u.getUsername().equals(username)) {
                hash = u.getPassword();
                if (hash.equals(hashPassword(password))) {
                    return u;
                }
            }
        }

        System.out.println("Incorrect Credentials!");
        return null;
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
