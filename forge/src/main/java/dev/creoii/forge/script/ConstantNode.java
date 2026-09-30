package dev.creoii.forge.script;

import dev.creoii.forge.value.datatype.DataType;

public class ConstantNode<T> implements Node {
    private final DataType<T> dataType;
    private final T value;

    public ConstantNode(DataType<T> dataType, T value) {
        this.dataType = dataType;
        this.value = value;
    }

    public DataType<T> getDataType() {
        return dataType;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
