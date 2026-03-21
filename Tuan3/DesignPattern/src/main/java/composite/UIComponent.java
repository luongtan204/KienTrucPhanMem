package composite;

public interface UIComponent {
    void showDetails();

    default void showDetails(String indent) {
        showDetails();
    }
}

