package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Portfolio {

    /*
     * Stock Symbol (String) -> Quantity (Integer)
     * Using String makes File I/O simple and reliable
     */
    private final Map<String, Integer> holdings;

    // ---------- Constructor ----------
    public Portfolio() {
        this.holdings = new HashMap<>();
    }

    // ---------- BUY Operation ----------
    public void buyStock(String symbol, int quantity) {

        validateSymbol(symbol);
        validateQuantity(quantity);

        symbol = normalizeSymbol(symbol);

        int existingQty = holdings.getOrDefault(symbol, 0);

        // Prevent integer overflow (edge case)
        if (existingQty > Integer.MAX_VALUE - quantity) {
            throw new IllegalStateException("Stock quantity overflow");
        }

        holdings.put(symbol, existingQty + quantity);
    }

    // ---------- SELL Operation ----------
    public void sellStock(String symbol, int quantity) {

        validateSymbol(symbol);
        validateQuantity(quantity);

        symbol = normalizeSymbol(symbol);

        Integer currentQty = holdings.get(symbol);

        if (currentQty == null) {
            throw new IllegalArgumentException("Stock not present in portfolio");
        }

        if (quantity > currentQty) {
            throw new IllegalArgumentException(
                    "Attempted to sell more stocks than owned");
        }

        int remainingQty = currentQty - quantity;

        if (remainingQty == 0) {
            holdings.remove(symbol);
        } else {
            holdings.put(symbol, remainingQty);
        }
    }

    // ---------- Get Quantity ----------
    public int getQuantity(String symbol) {
        validateSymbol(symbol);
        return holdings.getOrDefault(normalizeSymbol(symbol), 0);
    }

    // ---------- Portfolio Check ----------
    public boolean containsStock(String symbol) {
        validateSymbol(symbol);
        return holdings.containsKey(normalizeSymbol(symbol));
    }

    // ---------- Safe Read-Only Access ----------
    public Map<String, Integer> getHoldings() {
        return Collections.unmodifiableMap(holdings);
    }

    // ---------- Portfolio Empty ----------
    public boolean isEmpty() {
        return holdings.isEmpty();
    }

    // ---------- Clear Portfolio ----------
    public void clear() {
        holdings.clear();
    }

    // ---------- Validation Methods ----------
    private void validateSymbol(String symbol) {
        if (symbol == null) {
            throw new IllegalArgumentException("Stock symbol cannot be null");
        }
        if (symbol.trim().isEmpty()) {
            throw new IllegalArgumentException("Stock symbol cannot be empty");
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be a positive integer");
        }
    }

    private String normalizeSymbol(String symbol) {
        return symbol.trim().toUpperCase();
    }

    // ---------- Display ----------
    @Override
    public String toString() {

        if (holdings.isEmpty()) {
            return "Portfolio is empty.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Stock Symbol\tQuantity\n");
        sb.append("---------------------------\n");

        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            sb.append(entry.getKey())
                    .append("\t\t")
                    .append(entry.getValue())
                    .append("\n");
        }

        return sb.toString();
    }
}
