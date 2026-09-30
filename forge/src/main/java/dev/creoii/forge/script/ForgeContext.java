package dev.creoii.forge.script;

import java.util.HashMap;
import java.util.Map;

public class ForgeContext {
    private final Map<String, Object> variables = new HashMap<>();

    public void add(String name) {
        variables.put(name, null);
    }

    public Object get(String name) {
        return variables.get(name);
    }

    public void set(String name, Object value) {
        variables.put(name, value);
    }

    public boolean contains(String name) {
        return variables.containsKey(name);
    }

    @Override
    public String toString() {
        return variables.keySet().toString();
    }
}
