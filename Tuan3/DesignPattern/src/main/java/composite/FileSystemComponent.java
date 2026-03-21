package composite;

public interface FileSystemComponent {
    void showDetails();

    default void showDetails(String indent) {
        showDetails();
    }
}

