package dev.creoii.forge;

import dev.creoii.forge.parse.FunctionDefinition;
import dev.creoii.forge.value.datatype.DataTypes;

import java.util.HashMap;
import java.util.Map;

public class FunctionRegistry {
    public static final Map<String, FunctionDefinition> REGISTRY = new HashMap<>();

    public static void register(FunctionDefinition function) {
        REGISTRY.put(function.name(), function);
    }

    public static FunctionDefinition get(String id) {
        return REGISTRY.get(id);
    }

    public static boolean isKeyword(String s) {
        return REGISTRY.containsKey(s);
    }

    static {
        register(new FunctionDefinition("condition", DataTypes.VOID, DataTypes.ANY, DataTypes.ANY));
        register(new FunctionDefinition("after", DataTypes.BOOLEAN, DataTypes.NUMBER));
        register(new FunctionDefinition("incr", DataTypes.NUMBER, DataTypes.NUMBER, DataTypes.NUMBER));
        register(new FunctionDefinition("set", DataTypes.VOID, DataTypes.ANY, DataTypes.ANY));
        register(new FunctionDefinition("print", DataTypes.STRING, DataTypes.STRING));
        register(new FunctionDefinition("if", DataTypes.BOOLEAN, DataTypes.ANY, DataTypes.ANY, DataTypes.ANY));
        register(new FunctionDefinition("gt", DataTypes.BOOLEAN, DataTypes.NUMBER, DataTypes.NUMBER));
        register(new FunctionDefinition("mul", DataTypes.NUMBER, DataTypes.NUMBER, DataTypes.NUMBER));
        register(new FunctionDefinition("concat", DataTypes.STRING, DataTypes.STRING, DataTypes.STRING));
    }
}
