package dev.creoii.forge.value;

public record ObjectValue<T>(T value) implements Value<T> {
    @Override
    public T get() {
        return value;
    }
}
