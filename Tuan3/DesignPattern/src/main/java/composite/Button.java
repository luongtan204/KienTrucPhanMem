package composite;

public class Button implements UIComponent {
    private final String label;

    public Button(String label) {
        this.label = label;
    }

    @Override
    public void showDetails() {
        showDetails("");
    }

    @Override
    public void showDetails(String indent) {
        System.out.println(indent + "Button: " + label);
    }
}

