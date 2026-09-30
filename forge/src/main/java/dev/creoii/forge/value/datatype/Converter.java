package dev.creoii.forge.value.datatype;

public interface Converter<T> {
    T convert(Object input);
}
