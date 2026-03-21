package adapter;

public class Client {
    private JSONService jsonService;

    public Client(JSONService jsonService) {
        this.jsonService = jsonService;
    }

    public void displayData() {
        String json = jsonService.getJSON();
        System.out.println("Received JSON: " + json);
    }
}

