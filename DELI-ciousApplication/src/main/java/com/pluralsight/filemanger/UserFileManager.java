package com.pluralsight.filemanger;

import com.pluralsight.User;
import com.pluralsight.menu.*;

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

                String[] receipts = (data.length > 3) ? data[3].split(",") : new String[0];
                List<Orderable> savedOrders = (data.length > 4) ? loadSavedOrders(data[4]) : new ArrayList<>();

                user = new User(data[0], data[1], Integer.parseInt(data[2]), receipts, savedOrders);

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
                String savedOrders = writeSavedOrders(u.getSavedOrders());

                bufferedWriter.write(u.getUsername() + "|" + u.getPassword() + "|" + u.getRewardPoints() + "|" + receipts + "|" + savedOrders);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error Writing File");
        }
    }

    public static String writeSavedOrders(List<Orderable> savedOrders) {
        StringBuilder orders = new StringBuilder();
        for (Orderable o : savedOrders) {
            orders.append(o).append("\n").append("Price: $").append(o.getPrice()).append("\n");
        }
        return orders.toString().replaceAll("\n", "-");
    }

    public static List<Orderable> loadSavedOrders(String orderString) {
        List<Orderable> savedOrders = new ArrayList<>();
        String[] lines = orderString.split("-");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();

            if (line.contains("Sandwich")) {
                String sizeLine = lines[++i].trim();
                String[] sizeData = sizeLine.split(": ")[1].split("\"");
                int size = Integer.parseInt(sizeData[0]);

                String bread = lines[++i].split(": ")[1];

                Sandwich s = new Sandwich(size, bread);

                String toppingsLine = lines[++i].split(": ")[1];
                String[] toppingsArray = toppingsLine.split(",");

                for (String topping : toppingsArray) {
                    boolean extra = false;
                    String name;
                    String[] toppingData = topping.trim().split(" ");
                    if (toppingData[0].equalsIgnoreCase("extra")) {
                        extra = true;
                        name = toppingData[1];
                    } else {
                        name = toppingData[0];
                    }
                    Topping t = new RegularTopping(name, extra);
                    s.addTopping(t);
                }
                String toastedLine = lines[++i];
                boolean toasted = toastedLine.equalsIgnoreCase("Toasted");

                s.setToasted(toasted);

                double price = Double.parseDouble(lines[++i].split(": ")[1].replace("$", ""));

                if (price == 0) {
                    s.setReward(true);
                }

                savedOrders.add(s);
            } else if (line.startsWith("Drink:")) {
                String drinkName = lines[++i];
                String size = lines[++i].split(": ")[1];
                String ice = lines[++i].split(": ")[1];
                double price = Double.parseDouble(lines[++i].split(": ")[1].replace("$", ""));

                Drink drink = new Drink(drinkName, price, size, ice);
                savedOrders.add(drink);
            } else if (line.startsWith("Chip:")) {
                String chipName = lines[++i];
                double price = Double.parseDouble(lines[++i].split(": ")[1].replace("$", ""));

                Chip chip = new Chip(chipName, price);
                savedOrders.add(chip);
            }
        }
        return savedOrders;
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
