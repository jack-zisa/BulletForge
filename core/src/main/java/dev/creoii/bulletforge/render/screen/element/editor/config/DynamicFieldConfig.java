package dev.creoii.bulletforge.render.screen.element.editor.config;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import dev.creoii.providerlib.api.value.datatype.DataType;

public interface DynamicFieldConfig<T> {
    DataType<T> dataType();

    Object owner();

    Field field();

    /**
     * @return true if this dynamic UI config creates its own header element, or false if one should be created for it.
     */
    default boolean hasCustomHeader() {
        return false;
    }

    /**
     * @return true if this dynamic UI config creates a nested element & should be extended to fill to the width of its parent.
     */
    default boolean isNested() {
        return false;
    }

    default T value() {
        try {
            Field field = field();
            field.setAccessible(true);
            return dataType().convert(field.get(owner()));
        } catch (ReflectionException e) {
            throw new RuntimeException(e);
        }
    }

    default void set(T value) {
        try {
            Field field = field();
            field.set(owner(), dataType().convertTo(value, field.getType()));
        } catch (ReflectionException e) {
            throw new RuntimeException(e);
        }
    }

    default Actor create() {
        return create(value());
    }

    Actor create(T value);
}
