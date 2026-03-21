package observer;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Observer Design Pattern: Stock Price Monitoring ===\n");

        Stock applStock = new Stock("APPLE", 150.00);
        Stock googleStock = new Stock("GOOGLE", 2800.00);

        Investor investor1 = new Investor("John");
        Investor investor2 = new Investor("Sarah");
        Investor investor3 = new Investor("Mike");

        System.out.println("--- Attaching investors to APPLE stock ---");
        applStock.attach(investor1);
        applStock.attach(investor2);

        System.out.println("\n--- Attaching investors to GOOGLE stock ---");
        googleStock.attach(investor2);
        googleStock.attach(investor3);

        System.out.println("\n--- APPLE stock price change ---");
        applStock.setPrice(155.00);

        System.out.println("\n--- GOOGLE stock price change ---");
        googleStock.setPrice(2850.00);

        System.out.println("\n--- Detaching investor from APPLE ---");
        applStock.detach(investor1);

        System.out.println("\n--- APPLE stock price change again ---");
        applStock.setPrice(152.50);

        System.out.println("\n--- Multiple price updates ---");
        applStock.setPrice(160.00);
        googleStock.setPrice(2900.00);
    }
}

