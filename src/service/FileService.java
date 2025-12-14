package service;

import model.Portfolio;
import model.Stock;
import model.Transaction;

import java.io.*;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileService {

    // ---------- File Paths ----------
    private static final String DATA_DIR = "data";
    private static final String STOCK_FILE = DATA_DIR + "/stocks.txt";
    private static final String PORTFOLIO_FILE = DATA_DIR + "/portfolio.txt";
    private static final String TRANSACTION_FILE = DATA_DIR + "/transactions.txt";

    // ---------- Constructor ----------
    public FileService() {
        ensureDataDirectory();
        ensureFileExists(STOCK_FILE);
        ensureFileExists(PORTFOLIO_FILE);
        ensureFileExists(TRANSACTION_FILE);
    }

    // ---------- Stock File ----------
    public List<Stock> loadStocks() {

        List<Stock> stocks = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(STOCK_FILE))) {

            String line;
            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty())
                    continue;

                try {
                    String[] parts = line.split(",");
                    String symbol = parts[0].trim();
                    String name = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());

                    stocks.add(new Stock(symbol, name, price));

                } catch (Exception e) {
                    // Skip corrupted stock record
                    System.err.println("Skipping invalid stock record: " + line);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read stock file", e);
        }

        return stocks;
    }

    // ---------- Portfolio File ----------
    public Portfolio loadPortfolio() {

        Portfolio portfolio = new Portfolio();

        try (BufferedReader br = new BufferedReader(new FileReader(PORTFOLIO_FILE))) {

            String line;
            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty())
                    continue;

                try {
                    String[] parts = line.split(",");
                    String symbol = parts[0].trim();
                    int quantity = Integer.parseInt(parts[1].trim());

                    portfolio.buyStock(symbol, quantity);

                } catch (Exception e) {
                    System.err.println("Skipping invalid portfolio record: " + line);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read portfolio file", e);
        }

        return portfolio;
    }

    public void savePortfolio(Portfolio portfolio) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PORTFOLIO_FILE))) {

            for (Map.Entry<String, Integer> entry : portfolio.getHoldings().entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to save portfolio", e);
        }
    }

    // ---------- Transaction File ----------
    public List<Transaction> loadTransactions() {

        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(TRANSACTION_FILE))) {

            String line;
            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty())
                    continue;

                try {
                    transactions.add(Transaction.fromFileString(line));
                } catch (Exception e) {
                    System.err.println("Skipping invalid transaction record: " + line);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read transaction file", e);
        }

        return transactions;
    }

    public void appendTransaction(Transaction transaction) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TRANSACTION_FILE, true))) {

            bw.write(transaction.toFileString());
            bw.newLine();

        } catch (IOException e) {
            throw new RuntimeException("Failed to write transaction", e);
        }
    }

    // ---------- Utility Methods ----------
    private void ensureDataDirectory() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private void ensureFileExists(String path) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create file: " + path, e);
        }
    }
}
