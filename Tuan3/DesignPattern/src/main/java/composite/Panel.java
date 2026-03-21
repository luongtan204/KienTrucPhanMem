package composite;

import java.util.ArrayList;
import java.util.List;

public class Panel implements UIComponent {
    private final String name;
    private final List<UIComponent> children = new ArrayList<>();

    public Panel(String name) {
        this.name = name;
    }

    public void add(UIComponent component) {
        children.add(component);
    }

    public void remove(UIComponent component) {
        children.remove(component);
    }

    @Override
    public void showDetails() {
        showDetails("");
    }

    @Override
    public void showDetails(String indent) {
        System.out.println(indent + "Panel: " + name);
        String childIndent = indent + "  ";
        for (UIComponent child : children) {
            child.showDetails(childIndent);
        }
    }
}

