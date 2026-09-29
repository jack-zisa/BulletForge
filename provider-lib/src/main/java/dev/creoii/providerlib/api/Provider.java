package dev.creoii.providerlib.api;

import dev.creoii.providerlib.api.context.Context;

public interface Provider<T> {
    T get(Context context);
}
