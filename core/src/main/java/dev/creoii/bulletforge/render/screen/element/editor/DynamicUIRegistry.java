package dev.creoii.bulletforge.render.screen.element.editor;

import dev.creoii.bulletforge.object.definition.*;
import dev.creoii.bulletforge.object.definition.path.OrbitBulletPathType;
import dev.creoii.bulletforge.object.definition.path.ParametricBulletPathType;
import dev.creoii.bulletforge.object.definition.path.WavyBulletPathType;
import dev.creoii.bulletforge.render.screen.element.editor.config.*;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class DynamicUIRegistry {
    private static final Map<Class<?>, Function<Object, DynamicUIConfig>> REGISTRY = new HashMap<>();

    public static <T> void register(Class<T> type, Function<T, DynamicUIConfig> function) {
        REGISTRY.put(type, owner -> function.apply(type.cast(owner)));
    }

    @Nullable
    public static DynamicUIConfig get(Object owner) {
        Function<Object, DynamicUIConfig> function = REGISTRY.get(owner.getClass());
        return function != null ? function.apply(owner) : null;
    }

    static {
        register(Offset.class, offset -> new DynamicUIConfig.Builder<>(offset)
            .addField("offset", Vector2FieldDynamicFieldConfig::new)
            .addField("affectMouse", CheckBoxDynamicFieldConfig::new)
            .addField("rotate", CheckBoxDynamicFieldConfig::new)
            .build()
        );
        register(AttackDefinition.class, definition -> new DynamicUIConfig.Builder<>(definition)
            .addField("bulletId", NumberFieldDynamicFieldConfig::new)
            .addField("attackSpeed", (attackDefinition, field) -> new NumberSliderDynamicFieldConfig(attackDefinition, field, 0f, 9999f, 1f))
            .addField("bulletCount", (attackDefinition, field) -> new NumberSliderDynamicFieldConfig(attackDefinition, field, 0f, 360f, 1f))
            .addField("arcGap", (attackDefinition, field) -> new NumberSliderDynamicFieldConfig(attackDefinition, field, 0f, 360f, 1f))
            .addField("angleOffset", (attackDefinition, field) -> new NumberSliderDynamicFieldConfig(attackDefinition, field, 0f, 360f, 1f))
            .addField("offset", ObjectDynamicFieldConfig::new)
            .build()
        );
        register(DisplayDefinition.class, definition -> new DynamicUIConfig.Builder<>(definition)
            .addField("spriteId", TextFieldDynamicFieldConfig::new)
            .addField("scale", (displayDefinition, field) -> new NumberSliderDynamicFieldConfig(displayDefinition, field, 1f, 100f, 1f))
            .addField("shaderId", TextFieldDynamicFieldConfig::new)
            .build()
        );
        register(BulletDefinition.class, definition -> new DynamicUIConfig.Builder<>(definition)
            .addField("lifetime", (displayDefinition, field) -> new NumberSliderDynamicFieldConfig(displayDefinition, field, 0f, 9999f, 1f))
            .addField("velocity", Vector2FieldDynamicFieldConfig::new)
            .addField("rotation", (displayDefinition, field) -> new NumberSliderDynamicFieldConfig(displayDefinition, field, 0f, 360f, 1f))
            .addField("path", ObjectDynamicFieldConfig::new)
            .addField("display", ObjectDynamicFieldConfig::new)
            .build()
        );
        register(BulletGroupDefinition.class, definition -> new DynamicUIConfig.Builder<>(definition)
            .addField("lifetime", (displayDefinition, field) -> new NumberSliderDynamicFieldConfig(displayDefinition, field, 0f, 100000f, 1f))
            .addField("velocity", Vector2FieldDynamicFieldConfig::new)
            .addField("rotation", (displayDefinition, field) -> new NumberSliderDynamicFieldConfig(displayDefinition, field, 0f, 360f, 1f))
            .addField("path", ObjectDynamicFieldConfig::new)
            .addField("children", ListDynamicFieldConfig::new)
            .build()
        );
        register(BulletGroupDefinition.Child.class, definition -> new DynamicUIConfig.Builder<>(definition)
            .addField("offset", ObjectDynamicFieldConfig::new)
            .addField("bullet", ObjectDynamicFieldConfig::new)
            .build()
        );
        register(OrbitBulletPathType.class, pathType -> new DynamicUIConfig.Builder<>(pathType)
            .addField("sides", (orbitBulletPathType, field) -> new NumberSliderDynamicFieldConfig(orbitBulletPathType, field, 0f, 100000f, 1f))
            .addField("orbitRadius", (orbitBulletPathType, field) -> new NumberSliderDynamicFieldConfig(orbitBulletPathType, field, 0f, 100000f, 1f))
            .build()
        );
        register(ParametricBulletPathType.class, pathType -> new DynamicUIConfig.Builder<>(pathType)
            .addField("parametricType", (parametricBulletPathType, field) -> new CycleButtonDynamicFieldConfig<>(parametricBulletPathType, field, ParametricBulletPathType.ParametricType.values()))
            .addField("scale", Vector2FieldDynamicFieldConfig::new)
            .build()
        );
        register(WavyBulletPathType.class, pathType -> new DynamicUIConfig.Builder<>(pathType)
            .addField("waveType", (wavyBulletPathType, field) -> new CycleButtonDynamicFieldConfig<>(wavyBulletPathType, field, WavyBulletPathType.WaveType.values()))
            .addField("amplitude", (displayDefinition, field) -> new NumberSliderDynamicFieldConfig(displayDefinition, field, 0f, 1000f, 1f))
            .addField("frequency", (displayDefinition, field) -> new NumberSliderDynamicFieldConfig(displayDefinition, field, 0f, 1000f, 1f))
            .addField("indexPhase", CheckBoxDynamicFieldConfig::new)
            .build()
        );
    }
}
