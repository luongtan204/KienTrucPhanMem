package adapter;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Adapter Design Pattern: XML to JSON Conversion ===\n");

        // Create XMLSystem with XML data
        String xmlData = "<?xml version=\"1.0\"?>" +
                "<user>" +
                "<name>John Doe</name>" +
                "<email>john@example.com</email>" +
                "<age>30</age>" +
                "</user>";

        System.out.println("Original XML Data:");
        System.out.println(xmlData);
        System.out.println();

        // Create XMLSystem instance
        XMLSystem xmlSystem = new XMLSystem(xmlData);

        // Use Adapter to convert XML to JSON
        JSONService adapter = new DataAdapter(xmlSystem);

        // Client only works with JSONService interface
        Client client = new Client(adapter);
        client.displayData();

        System.out.println("\n--- Another Example ---");
        String xmlData2 = "<?xml version=\"1.0\"?>" +
                "<product>" +
                "<id>P001</id>" +
                "<name>Laptop</name>" +
                "<price>999.99</price>" +
                "</product>";

        System.out.println("Original XML Data:");
        System.out.println(xmlData2);
        System.out.println();

        XMLSystem xmlSystem2 = new XMLSystem(xmlData2);
        JSONService adapter2 = new DataAdapter(xmlSystem2);
        Client client2 = new Client(adapter2);
        client2.displayData();
    }
}

