package model;

import java.util.Objects;

public class Stock {

    // ---------- Fields ----------
    private final String symbol;
    private final String companyName;
    private double price;

    // ---------- Constructor with Validation ----------
    public Stock(String symbol, String companyName, double price) {

        if (symbol == null || symbol.trim().isEmpty()) {
            throw new IllegalArgumentException("Stock symbol cannot be null or empty");
        }

        if (companyName == null || companyName.trim().isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be null or empty");
        }

        if (price <= 0) {
            throw new IllegalArgumentException("Stock price must be greater than zero");
        }

        this.symbol = symbol.trim().toUpperCase();
        this.companyName = companyName.trim();
        this.price = price;
    }

    // ---------- Getters ----------
    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getPrice() {
        return price;
    }

    // ---------- Setter with Validation ----------
    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        this.price = price;
    }

    // ---------- Equality (important for collections) ----------
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Stock))
            return false;
        Stock other = (Stock) obj;
        return symbol.equals(other.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }

    // ---------- Display ----------
    @Override
    public String toString() {
        return String.format("%-6s | %-20s | ₹%.2f", symbol, companyName, price);
    }
}
