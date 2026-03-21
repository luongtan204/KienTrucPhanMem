package composite;

import java.util.ArrayList;
import java.util.List;

public class Folder implements FileSystemComponent {
    private final String name;
    private final List<FileSystemComponent> children = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    public void add(FileSystemComponent component) {
        children.add(component);
    }

    public void remove(FileSystemComponent component) {
        children.remove(component);
    }

    @Override
    public void showDetails() {
        showDetails("");
    }

    @Override
    public void showDetails(String indent) {
        System.out.println(indent + "Folder: " + name);
        String childIndent = indent + "  ";
        for (FileSystemComponent child : children) {
            child.showDetails(childIndent);
        }
    }
}

