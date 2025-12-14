package service;

import model.Portfolio;
import model.Stock;
import model.Transaction;

import java.util.List;

public class TradingService {

    private final FileService fileService;
    private Portfolio portfolio;
    private List<Stock> marketStocks;

    // ---------- Constructor ----------
    public TradingService(FileService fileService) {
        if (fileService == null) {
            throw new IllegalArgumentException("FileService cannot be null");
        }

        this.fileService = fileService;
        this.portfolio = fileService.loadPortfolio();
        this.marketStocks = fileService.loadStocks();
    }

    // ---------- Market Data ----------
    public void displayMarketData() {

        if (marketStocks.isEmpty()) {
            System.out.println("No stocks available in the market.");
            return;
        }

        System.out.println("Symbol | Company Name         | Price");
        System.out.println("--------------------------------------");

        for (Stock stock : marketStocks) {
            System.out.println(stock);
        }
    }

    // ---------- BUY Stock ----------
    public void buyStock(String symbol, int quantity) {

        validateSymbol(symbol);
        validateQuantity(quantity);

        Stock stock = findStock(symbol);

        if (stock == null) {
            throw new IllegalArgumentException("Stock not found in market");
        }

        // Update portfolio
        portfolio.buyStock(symbol, quantity);

        // Save portfolio
        fileService.savePortfolio(portfolio);

        // Record transaction
        Transaction transaction = new Transaction(Transaction.Type.BUY, symbol, quantity, stock.getPrice());

        fileService.appendTransaction(transaction);

        System.out.println("Stock purchased successfully.");
    }

    // ---------- SELL Stock ----------
    public void sellStock(String symbol, int quantity) {

        validateSymbol(symbol);
        validateQuantity(quantity);

        Stock stock = findStock(symbol);

        if (stock == null) {
            throw new IllegalArgumentException("Stock not found in market");
        }

        // Update portfolio
        portfolio.sellStock(symbol, quantity);

        // Save portfolio
        fileService.savePortfolio(portfolio);

        // Record transaction
        Transaction transaction = new Transaction(Transaction.Type.SELL, symbol, quantity, stock.getPrice());

        fileService.appendTransaction(transaction);

        System.out.println("Stock sold successfully.");
    }

    // ---------- View Portfolio ----------
    public void displayPortfolio() {
        System.out.println(portfolio);
    }

    // ---------- Helper Methods ----------
    private Stock findStock(String symbol) {

        symbol = symbol.trim().toUpperCase();

        for (Stock stock : marketStocks) {
            if (stock.getSymbol().equals(symbol)) {
                return stock;
            }
        }
        return null;
    }

    private void validateSymbol(String symbol) {
        if (symbol == null || symbol.trim().isEmpty()) {
            throw new IllegalArgumentException("Stock symbol cannot be null or empty");
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
    }
}
