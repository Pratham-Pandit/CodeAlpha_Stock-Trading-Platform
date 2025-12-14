# 📈 Stock Trading Platform (File I/O Based)

## 📌 Project Overview
This project is a **Java-based Stock Trading Platform** that simulates a basic stock market environment.  
Users can view market data, buy and sell stocks, and track their portfolio performance.  
All data is **persisted using File I/O**, without using any database.

---

## 🎯 Objectives
- Simulate a basic stock trading environment
- Display market stock data
- Perform buy and sell operations
- Track portfolio performance over time
- Persist data using text files (File I/O)
- Apply Object-Oriented Programming (OOP) principles

---

## 🛠 Technologies Used
- **Java**
- **Object-Oriented Programming (OOP)**
- **Java File I/O**
- **Collections Framework**
- **Console-based Interface**

---

## 🧩 Key Concepts Implemented
- Classes and Objects
- Encapsulation and Composition
- Polymorphism for transactions
- File handling using `BufferedReader` and `BufferedWriter`
- Exception handling
- Menu-driven program design

---

## 📁 Project Structure
    │
    ├── data/
    │ ├── stocks.txt # Stores market stock data
    │ ├── portfolio.txt # Stores user portfolio holdings
    │ └── transactions.txt # Stores transaction history
    │
    ├── src/
    │ ├── model/
    │ │ ├── Stock.java
    │ │ ├── User.java
    │ │ ├── Portfolio.java
    │ │ └── Transaction.java
    │ │
    │ ├── service/
    │ │ ├── MarketService.java
    │ │ ├── TradingService.java
    │ │ └── FileService.java
    │ │
    │ └── Main.java
    │
    └── README.md


---

## 📄 Data Files Description

### `stocks.txt`
Stores available stocks and their prices.


### `portfolio.txt`
Stores user stock holdings.


### `transactions.txt`
Stores buy/sell transaction history.


---

## ⚙️ Features
- View current market stock prices
- Buy stocks (with balance validation)
- Sell stocks (with quantity validation)
- Automatically update portfolio
- Track profit and loss
- Persistent storage using File I/O

---

## ▶️ How to Run the Project
1. Clone or download the repository
2. Ensure Java is installed on your system
3. Navigate to the `src` folder
4. Compile the project:
5. Run the program:



---

## 📊 Portfolio Performance Tracking
The application calculates:
- Total invested amount
- Current portfolio value
- Profit or loss

All calculations are performed by reading data from files in the `data` folder.

---

## 🚫 Why No Database?
This project intentionally avoids databases to:
- Keep the system lightweight
- Focus on File I/O concepts
- Simplify deployment and understanding
- Demonstrate data persistence using files

---

## 🚀 Future Enhancements
- GUI-based interface
- Real-time stock price API integration
- Multiple user support
- Database integration
- Graphical performance analytics

---

## 🧠 Learning Outcomes
- Practical understanding of Java File I/O
- Strong application of OOP principles
- Experience in building a real-world simulation project
- Improved software design and modular coding skills

---

## 👤 Author
**Your Name**  
(Java Developer / Student)

---

## 📜 License
This project is for educational purposes only.