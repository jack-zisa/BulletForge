package dev.creoii.forge.script;

import java.util.Arrays;
import java.util.List;

public class Forgescript {
    private final int version;
    private final List<Expression> expressions;

    public Forgescript(int version, Expression... expressions) {
        this.version = version;
        this.expressions = Arrays.asList(expressions);
    }

    public boolean execute() {
        // System.out.println("Forgescript version " + version);
        expressions.forEach(Expression::execute);
        return false;
    }

    @Override
    public String toString() {
        return "Forgescript{" +
            "version=" + version +
            '}';
    }
}
