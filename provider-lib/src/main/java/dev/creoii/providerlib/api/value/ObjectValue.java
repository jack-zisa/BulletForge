package dev.creoii.providerlib.api.value;

public record ObjectValue<T>(T value) implements Value<T> {
    @Override
    public T get() {
        return value;
    }
}
