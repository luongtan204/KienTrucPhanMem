package observer;

public class Investor implements Observer {
    private String name;

    public Investor(String name) {
        this.name = name;
    }

    @Override
    public void update(String stockName, double newPrice) {
        System.out.println("[Notification] Investor " + name + " received update: "
                + stockName + " is now at $" + String.format("%.2f", newPrice));
    }

    public String getName() {
        return name;
    }
}

