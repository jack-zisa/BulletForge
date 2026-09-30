package dev.creoii.forge.script;

import dev.creoii.forge.value.datatype.DataType;

public class VariableNode<T> implements Node {
    private final DataType<T> dataType;
    private final String name;
    private T value;

    public VariableNode(DataType<T> dataType, String name) {
        this.dataType = dataType;
        this.name = name;
        value = dataType.defaultValue();
    }

    public DataType<T> getDataType() {
        return dataType;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name + ": " + dataType.getClass().getSimpleName();
    }
}
