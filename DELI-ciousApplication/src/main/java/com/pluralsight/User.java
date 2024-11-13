package com.pluralsight;

public class User {
    private String username;
    private String password;
    private String[] receipts;

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
        this.receipts = receipts;
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

    public void setReceipts(String[] receipts) {
        this.receipts = receipts;
    }

    public String[] getReceipts() {
        return this.receipts;
    }
}
