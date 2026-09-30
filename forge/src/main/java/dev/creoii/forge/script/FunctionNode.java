package dev.creoii.forge.script;

import dev.creoii.forge.parse.FunctionDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FunctionNode implements Node {
    private final FunctionDefinition definition;
    private final List<Node> children;

    public FunctionNode(FunctionDefinition definition) {
        this.definition = definition;
        children = new ArrayList<>(definition.args().length);
    }

    public void add(Node node) {
        children.add(node);
    }

    public List<Node> getChildren() {
        return children;
    }

    public void forEach(Consumer<Node> action) {
        children.forEach(action);
    }

    @Override
    public String toString() {
        return "FunctionNode[" +
            "name=" + definition.name() +
            ", children=" + children +
            "]: " + definition.dataType().getClass().getSimpleName();
    }
}
