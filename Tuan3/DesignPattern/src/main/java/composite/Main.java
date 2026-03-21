package composite;

public class Main {
    public static void main(String[] args) {
        Folder root = new Folder("root");
        root.add(new File("readme.txt"));

        Folder src = new Folder("src");
        src.add(new File("Main.java"));
        src.add(new File("Utils.java"));

        Folder resources = new Folder("resources");
        resources.add(new File("application.yml"));

        root.add(src);
        root.add(resources);
        root.add(new File("pom.xml"));

        System.out.println("=== File System Composite ===");
        root.showDetails();

        System.out.println();

        Panel mainPanel = new Panel("MainPanel");
        mainPanel.add(new Button("OK"));
        mainPanel.add(new Button("Cancel"));

        Panel toolbar = new Panel("Toolbar");
        toolbar.add(new Button("New"));
        toolbar.add(new Button("Save"));

        mainPanel.add(toolbar);

        System.out.println("=== UI Composite ===");
        mainPanel.showDetails();
    }
}