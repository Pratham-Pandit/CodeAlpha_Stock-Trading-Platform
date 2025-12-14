import service.FileService;
import service.TradingService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        FileService fileService = new FileService();
        TradingService tradingService = new TradingService(fileService);

        boolean exit = false;

        while (!exit) {

            printMenu();

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {

                    case "1":
                        tradingService.displayMarketData();
                        break;

                    case "2":
                        System.out.print("Enter stock symbol to BUY: ");
                        String buySymbol = scanner.nextLine();

                        System.out.print("Enter quantity: ");
                        int buyQty = Integer.parseInt(scanner.nextLine());

                        tradingService.buyStock(buySymbol, buyQty);
                        break;

                    case "3":
                        System.out.print("Enter stock symbol to SELL: ");
                        String sellSymbol = scanner.nextLine();

                        System.out.print("Enter quantity: ");
                        int sellQty = Integer.parseInt(scanner.nextLine());

                        tradingService.sellStock(sellSymbol, sellQty);
                        break;

                    case "4":
                        tradingService.displayPortfolio();
                        break;

                    case "5":
                        System.out.println("Exiting application...");
                        exit = true;
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid number input. Please enter numeric values only.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error occurred: " + e.getMessage());
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("====== STOCK TRADING PLATFORM ======");
        System.out.println("1. View Market Data");
        System.out.println("2. Buy Stock");
        System.out.println("3. Sell Stock");
        System.out.println("4. View Portfolio");
        System.out.println("5. Exit");
        System.out.println("===================================");
    }
}
