package dev.creoii.forge.value.datatype;

public interface DataType<T> extends Converter<T> {
    boolean isType(Object o);

    T defaultValue();
}
