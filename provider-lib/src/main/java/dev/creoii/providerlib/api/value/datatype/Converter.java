package dev.creoii.providerlib.api.value.datatype;

public interface Converter<T> {
    T convert(Object input);

    Object convertTo(T value, Class<?> targetType);
}
