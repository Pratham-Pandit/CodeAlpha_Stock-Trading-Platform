package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public final class Transaction {

    // ---------- Transaction Type ----------
    public enum Type {
        BUY, SELL
    }

    // ---------- Fields (Immutable) ----------
    private final Type type;
    private final String stockSymbol;
    private final int quantity;
    private final double price;
    private final LocalDateTime timestamp;

    // ---------- Date Format ----------
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // ---------- Constructor (New Transaction) ----------
    public Transaction(Type type, String stockSymbol, int quantity, double price) {
        this(type, stockSymbol, quantity, price, LocalDateTime.now());
    }

    // ---------- Constructor (File Restore) ----------
    private Transaction(Type type, String stockSymbol, int quantity,
            double price, LocalDateTime timestamp) {

        validateType(type);
        validateSymbol(stockSymbol);
        validateQuantity(quantity);
        validatePrice(price);

        if (timestamp == null) {
            throw new IllegalArgumentException("Transaction timestamp cannot be null");
        }

        this.type = type;
        this.stockSymbol = normalizeSymbol(stockSymbol);
        this.quantity = quantity;
        this.price = price;
        this.timestamp = timestamp;
    }

    // ---------- Getters ----------
    public Type getType() {
        return type;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // ---------- File I/O Serialization ----------
    public String toFileString() {
        return type + "," +
                stockSymbol + "," +
                quantity + "," +
                price + "," +
                timestamp.format(FORMATTER);
    }

    // ---------- File I/O Deserialization ----------
    public static Transaction fromFileString(String line) {

        if (line == null || line.trim().isEmpty()) {
            throw new IllegalArgumentException("Transaction record is empty or null");
        }

        String[] parts = line.split(",");

        if (parts.length != 5) {
            throw new IllegalArgumentException(
                    "Invalid transaction format (expected 5 fields): " + line);
        }

        try {
            Type type = Type.valueOf(parts[0].trim().toUpperCase());
            String symbol = parts[1].trim();
            int quantity = Integer.parseInt(parts[2].trim());
            double price = Double.parseDouble(parts[3].trim());
            LocalDateTime timestamp = LocalDateTime.parse(parts[4].trim(), FORMATTER);

            return new Transaction(type, symbol, quantity, price, timestamp);

        } catch (IllegalArgumentException | DateTimeParseException ex) {
            // NumberFormatException is already covered by IllegalArgumentException
            throw new IllegalArgumentException(
                    "Failed to parse transaction record: " + line, ex);
        }
    }

    // ---------- Validation ----------
    private static void validateType(Type type) {
        if (type == null) {
            throw new IllegalArgumentException("Transaction type cannot be null");
        }
    }

    private static void validateSymbol(String symbol) {
        if (symbol == null || symbol.trim().isEmpty()) {
            throw new IllegalArgumentException("Stock symbol cannot be null or empty");
        }
    }

    private static void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Transaction quantity must be positive");
        }
    }

    private static void validatePrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Transaction price must be greater than zero");
        }
    }

    private static String normalizeSymbol(String symbol) {
        return symbol.trim().toUpperCase();
    }

    // ---------- Equality ----------
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Transaction))
            return false;
        Transaction other = (Transaction) obj;
        return type == other.type &&
                quantity == other.quantity &&
                Double.compare(other.price, price) == 0 &&
                stockSymbol.equals(other.stockSymbol) &&
                timestamp.equals(other.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, stockSymbol, quantity, price, timestamp);
    }

    // ---------- Display ----------
    @Override
    public String toString() {
        return String.format(
                "%s | %s | Qty: %d | Price: %.2f | %s",
                type,
                stockSymbol,
                quantity,
                price,
                timestamp.format(FORMATTER));
    }
}
