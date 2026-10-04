package dev.creoii.bulletforge.render.screen.element.editor;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import dev.creoii.bulletforge.render.screen.element.editor.config.DynamicFieldConfig;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;

public record DynamicUIConfig(Class<?> type, Map<Field, DynamicFieldConfig<?>> fields) {
    public static final class Builder<T> {
        private final T owner;
        private final Map<Field, DynamicFieldConfig<?>> fields = new LinkedHashMap<>();

        public Builder(T owner) {
            this.owner = owner;
        }

        public <V> Builder<T> addField(String name, BiFunction<T, Field, DynamicFieldConfig<V>> config) {
            Field field = field(owner.getClass(), name);
            fields.put(field, config.apply(owner, field));
            return this;
        }

        public DynamicUIConfig build() {
            return new DynamicUIConfig(owner.getClass(), fields);
        }

        private static Field field(Class<?> type, String name) {
            try {
                return ClassReflection.getDeclaredField(type, name);
            } catch (ReflectionException e) {
                throw new IllegalArgumentException("No such field '" + name + "' found on " + type.getName(), e);
            }
        }
    }
}
