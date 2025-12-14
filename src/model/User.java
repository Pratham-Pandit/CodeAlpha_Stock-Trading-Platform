package model;

import java.util.Objects;

public class User {

    // ---------- Fields ----------
    private final String userId;
    private final String userName;
    private double balance;
    private final Portfolio portfolio;

    // ---------- Constructor ----------
    public User(String userId, String userName, double balance) {

        validateUserId(userId);
        validateUserName(userName);
        validateBalance(balance);

        this.userId = userId.trim();
        this.userName = userName.trim();
        this.balance = balance;
        this.portfolio = new Portfolio();
    }

    // ---------- Getters ----------
    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public double getBalance() {
        return balance;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    // ---------- Balance Operations ----------
    public void debit(double amount) {
        validateAmount(amount);

        if (amount > balance) {
            throw new IllegalStateException("Insufficient balance");
        }

        balance -= amount;
    }

    public void credit(double amount) {
        validateAmount(amount);
        balance += amount;
    }

    // ---------- Validation ----------
    private void validateUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
    }

    private void validateUserName(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
    }

    private void validateBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
    }

    private void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }

    // ---------- Equality ----------
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        return userId.equals(other.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    // ---------- Display ----------
    @Override
    public String toString() {
        return "User ID   : " + userId + "\n" +
                "User Name : " + userName + "\n" +
                "Balance   : ₹" + String.format("%.2f", balance);
    }
}
