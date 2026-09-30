package dev.creoii.forge.script;

import dev.creoii.forge.parse.FunctionDefinition;

public class Expression {
    private final String representation;
    private final FunctionNode node;
    private final ForgeContext context;

    public Expression(String representation, FunctionDefinition definition) {
        this.representation = representation;
        node = new FunctionNode(definition);
        context = new ForgeContext();
    }

    public FunctionNode node() {
        return node;
    }

    public ForgeContext context() {
        return context;
    }

    public void execute() {
        System.out.println("EXECUTE: " + representation);
        System.out.println("NODE:    " + node);
        System.out.println("CONTEXT: " + context);
        System.out.println();
    }

    @Override
    public String toString() {
        return representation;
    }
}
