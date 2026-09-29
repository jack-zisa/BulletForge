package dev.creoii.providerlib.api.context;

import dev.creoii.providerlib.api.value.ObjectValue;
import dev.creoii.providerlib.api.value.Value;
import dev.creoii.providerlib.api.value.type.ValueType;

import java.util.HashMap;
import java.util.Map;

public class Context {
    public final Map<ValueType, Value<?>> values;

    public Context() {
        values = new HashMap<>();
    }

    public boolean has(ValueType valueType) {
        return values.containsKey(valueType);
    }

    public boolean has(ValueType... valueTypes) {
        for (ValueType valueType : valueTypes) {
            if (!values.containsKey(valueType)) return false;
        }
        return true;
    }

    public Context set(ValueType valueType, Object value) {
        Object converted = valueType.getDataType().convert(value);
        values.put(valueType, new ObjectValue<>(converted));
        return this;
    }

    public Context remove(ValueType valueType) {
        values.remove(valueType);
        return this;
    }

    public Context remove(ValueType... valueTypes) {
        for (ValueType valueType : valueTypes) {
            values.remove(valueType);
        }
        return this;
    }

    public void removeExcept(ValueType exclude) {
        values.keySet().removeIf(valueType -> valueType != exclude);
    }

    public Context clear() {
        values.clear();
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(ValueType valueType) {
        return (T) values.get(valueType).get();
    }
}
