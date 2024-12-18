package com.pluralsight;

import com.pluralsight.menu.Orderable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private String username;
    private String password;
    private List<String> receipts = new ArrayList<>();
    private int rewardPoints;
    private List<Orderable> savedOrders = new ArrayList<>();

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.rewardPoints = 0;
    }

    public User(String username, String password, int rewardPoints, String[] receipts, List<Orderable> savedOrders) {
        this.username = username;
        this.password = password;
        Collections.addAll(this.receipts, receipts);
        this.rewardPoints = rewardPoints;
        this.savedOrders = savedOrders;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setReceipts(List<String> receipts) {
        this.receipts = receipts;
    }

    public List<String> getReceipts() {
        return this.receipts;
    }

    public void addReceipt(String receipt) {
        this.receipts.add(receipt);
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public List<Orderable> getSavedOrders() {
        return savedOrders;
    }

    public void setSavedOrders(List<Orderable> savedOrders) {
        this.savedOrders = savedOrders;
    }
}
