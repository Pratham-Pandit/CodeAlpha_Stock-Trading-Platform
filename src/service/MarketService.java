package service;

import model.Stock;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MarketService {

    private final FileService fileService;
    private List<Stock> marketStocks;

    // ---------- Constructor ----------
    public MarketService(FileService fileService) {

        if (fileService == null) {
            throw new IllegalArgumentException("FileService cannot be null");
        }

        this.fileService = fileService;
        refreshMarketData();
    }

    // ---------- Load / Refresh Market Data ----------
    public final void refreshMarketData() {

        List<Stock> loadedStocks = fileService.loadStocks();

        if (loadedStocks == null) {
            throw new IllegalStateException("Market data could not be loaded");
        }

        this.marketStocks = loadedStocks;
    }

    // ---------- Get All Market Stocks (Read-Only) ----------
    public List<Stock> getAllStocks() {

        if (marketStocks == null || marketStocks.isEmpty()) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(marketStocks);
    }

    // ---------- Find Stock by Symbol ----------
    public Stock getStockBySymbol(String symbol) {

        if (symbol == null || symbol.trim().isEmpty()) {
            throw new IllegalArgumentException("Stock symbol cannot be null or empty");
        }

        if (marketStocks == null || marketStocks.isEmpty()) {
            throw new IllegalStateException("Market data not available");
        }

        String normalizedSymbol = symbol.trim().toUpperCase();

        for (Stock stock : marketStocks) {
            if (stock.getSymbol().equals(normalizedSymbol)) {
                return stock;
            }
        }

        throw new IllegalArgumentException("Stock not found in market: " + normalizedSymbol);
    }

    // ---------- Check Stock Availability ----------
    public boolean isStockAvailable(String symbol) {

        if (symbol == null || symbol.trim().isEmpty()) {
            return false;
        }

        if (marketStocks == null || marketStocks.isEmpty()) {
            return false;
        }

        String normalizedSymbol = symbol.trim().toUpperCase();

        for (Stock stock : marketStocks) {
            if (Objects.equals(stock.getSymbol(), normalizedSymbol)) {
                return true;
            }
        }
        return false;
    }

    // ---------- Display Market Data ----------
    public void displayMarketData() {

        if (marketStocks == null || marketStocks.isEmpty()) {
            System.out.println("No market data available.");
            return;
        }

        System.out.println("Symbol | Company Name         | Price");
        System.out.println("--------------------------------------");

        for (Stock stock : marketStocks) {
            System.out.println(stock);
        }
    }
}
