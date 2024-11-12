package com.pluralsight.filemanger;

import com.pluralsight.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserFileManager {
    public static void addNewUser(User user) {
        String hashedPassword = hashPassword(user.getPassword());
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("user_data.txt"));
            bufferedWriter.write(user.getUsername() + "|" + hashedPassword);
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error Writing to File");
        }
    }

    public static User validateUser(String username, String password) {
        String hash = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("user_data.txt"));
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                String[] data = input.split("\\|");
                if (username.equals(data[0])) {
                    hash = data[1];
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Error Reading File");
        }

        if (hash != null && hash.equals(hashPassword(password))) {
            System.out.println("Successfully logged in!");
            //Implement loading in user
            return new User(username, password);
        } else {
            System.out.println("Incorrect Credentials!");
        }
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
