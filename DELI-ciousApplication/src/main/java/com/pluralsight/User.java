package com.pluralsight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private String username;
    private String password;
    private List<String> receipts = new ArrayList<>();

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String[] receipts) {
        this.username = username;
        this.password = password;
        Collections.addAll(this.receipts, receipts);
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
}
