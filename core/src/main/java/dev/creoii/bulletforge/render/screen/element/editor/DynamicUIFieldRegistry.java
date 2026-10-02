package dev.creoii.bulletforge.render.screen.element.editor;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import dev.creoii.bulletforge.object.definition.*;
import dev.creoii.bulletforge.object.definition.path.OrbitBulletPathType;
import dev.creoii.bulletforge.object.definition.path.ParametricBulletPathType;
import dev.creoii.bulletforge.object.definition.path.WavyBulletPathType;
import dev.creoii.bulletforge.render.screen.element.editor.config.*;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class DynamicUIFieldRegistry {
    private static final Map<Class<?>, Function<Object, Map<Field, DynamicFieldConfig<?>>>> REGISTRY = new HashMap<>();

    public static <T> void register(Class<T> type, Function<T, Map<Field, DynamicFieldConfig<?>>> function) {
        REGISTRY.put(type, owner -> function.apply(type.cast(owner)));
    }

    @Nullable
    public static Map<Field, DynamicFieldConfig<?>> get(Object owner) {
        Function<Object, Map<Field, DynamicFieldConfig<?>>> function = REGISTRY.get(owner.getClass());
        return function != null ? function.apply(owner) : null;
    }

    public static Field field(Class<?> type, String name) {
        try {
            return ClassReflection.getDeclaredField(type, name);
        } catch (ReflectionException e) {
            throw new IllegalArgumentException("No such field '" + name + "' found on " + type.getName(), e);
        }
    }

    public static final class FieldBuilder<T> {
        private final T owner;
        private final Map<Field, DynamicFieldConfig<?>> fields = new LinkedHashMap<>();

        private FieldBuilder(T owner) {
            this.owner = owner;
        }

        public <V> FieldBuilder<T> add(String name, BiFunction<T, Field, DynamicFieldConfig<V>> config) {
            Field field = field(owner.getClass(), name);
            fields.put(field, config.apply(owner, field));
            return this;
        }

        public Map<Field, DynamicFieldConfig<?>> build() {
            return fields;
        }
    }

    static {
        register(Offset.class, offset -> new FieldBuilder<>(offset)
            .add("offset", Vector2FieldDynamicFieldConfig::new)
            .add("affectMouse", CheckBoxDynamicFieldConfig::new)
            .add("rotate", CheckBoxDynamicFieldConfig::new)
            .build()
        );
        register(AttackDefinition.class, definition -> new FieldBuilder<>(definition)
            .add("bulletId", NumberFieldDynamicFieldConfig::new)
            .add("attackSpeed", (attackDefinition, field) -> new NumberSliderDynamicFieldConfig(attackDefinition, field, 0f, 9999f, 1f))
            .add("bulletCount", (attackDefinition, field) -> new NumberSliderDynamicFieldConfig(attackDefinition, field, 0f, 360f, 1f))
            .add("arcGap", (attackDefinition, field) -> new NumberSliderDynamicFieldConfig(attackDefinition, field, 0f, 360f, 1f))
            .add("angleOffset", (attackDefinition, field) -> new NumberSliderDynamicFieldConfig(attackDefinition, field, 0f, 360f, 1f))
            .add("offset", ObjectDynamicFieldConfig::new)
            .build()
        );
        register(DisplayDefinition.class, definition -> new FieldBuilder<>(definition)
            .add("spriteId", TextFieldDynamicFieldConfig::new)
            .add("scale", (displayDefinition, field) -> new NumberSliderDynamicFieldConfig(displayDefinition, field, 0f, 100f, 1f))
            .add("shaderId", TextFieldDynamicFieldConfig::new)
            .build()
        );
        register(BulletDefinition.class, definition -> new FieldBuilder<>(definition)
            .add("lifetime", (displayDefinition, field) -> new NumberSliderDynamicFieldConfig(displayDefinition, field, 0f, 9999f, 1f))
            .add("velocity", Vector2FieldDynamicFieldConfig::new)
            .add("rotation", (displayDefinition, field) -> new NumberSliderDynamicFieldConfig(displayDefinition, field, 0f, 360f, 1f))
            .add("path", ObjectDynamicFieldConfig::new)
            .add("display", ObjectDynamicFieldConfig::new)
            .build()
        );
        register(BulletGroupDefinition.class, definition -> new FieldBuilder<>(definition)
            .add("lifetime", (displayDefinition, field) -> new NumberSliderDynamicFieldConfig(displayDefinition, field, 0f, 100000f, 1f))
            .add("velocity", Vector2FieldDynamicFieldConfig::new)
            .add("rotation", (displayDefinition, field) -> new NumberSliderDynamicFieldConfig(displayDefinition, field, 0f, 360f, 1f))
            .add("path", ObjectDynamicFieldConfig::new)
            .add("children", ListDynamicFieldConfig::new)
            .build()
        );
        register(BulletGroupDefinition.Child.class, definition -> new FieldBuilder<>(definition)
            .add("offset", ObjectDynamicFieldConfig::new)
            .add("bullet", ObjectDynamicFieldConfig::new)
            .build()
        );
        register(OrbitBulletPathType.class, pathType -> new FieldBuilder<>(pathType)
            .add("sides", (orbitBulletPathType, field) -> new NumberSliderDynamicFieldConfig(orbitBulletPathType, field, 0f, 100000f, 1f))
            .add("orbitRadius", (orbitBulletPathType, field) -> new NumberSliderDynamicFieldConfig(orbitBulletPathType, field, 0f, 100000f, 1f))
            .build()
        );
        register(ParametricBulletPathType.class, pathType -> new FieldBuilder<>(pathType)
            .add("parametricType", (parametricBulletPathType, field) -> new CycleButtonDynamicFieldConfig<>(parametricBulletPathType, field, ParametricBulletPathType.ParametricType.values()))
            .add("scale", Vector2FieldDynamicFieldConfig::new)
            .build()
        );
        register(WavyBulletPathType.class, pathType -> new FieldBuilder<>(pathType)
            .add("waveType", (wavyBulletPathType, field) -> new CycleButtonDynamicFieldConfig<>(wavyBulletPathType, field, WavyBulletPathType.WaveType.values()))
            .add("amplitude", (displayDefinition, field) -> new NumberSliderDynamicFieldConfig(displayDefinition, field, 0f, 1000f, 1f))
            .add("frequency", (displayDefinition, field) -> new NumberSliderDynamicFieldConfig(displayDefinition, field, 0f, 1000f, 1f))
            .add("indexPhase", CheckBoxDynamicFieldConfig::new)
            .build()
        );
    }
}
