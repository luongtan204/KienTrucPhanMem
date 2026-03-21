package observer;

import java.util.ArrayList;
import java.util.List;

public class Stock {
    private String stockName;
    private double price;
    private List<Observer> observers = new ArrayList<>();

    public Stock(String stockName, double initialPrice) {
        this.stockName = stockName;
        this.price = initialPrice;
    }

    public void attach(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("[" + stockName + "] Investor attached.");
        }
    }

    public void detach(Observer observer) {
        if (observers.remove(observer)) {
            System.out.println("[" + stockName + "] Investor detached.");
        }
    }

    public void notify(String message) {
        for (Observer observer : observers) {
            observer.update(stockName, price);
        }
    }

    public void setPrice(double newPrice) {
        if (this.price != newPrice) {
            double oldPrice = this.price;
            this.price = newPrice;
            System.out.println("[" + stockName + "] Price changed: " + oldPrice + " -> " + newPrice);
            notify("Price update for " + stockName);
        }
    }

    public String getStockName() {
        return stockName;
    }

    public double getPrice() {
        return price;
    }
}

