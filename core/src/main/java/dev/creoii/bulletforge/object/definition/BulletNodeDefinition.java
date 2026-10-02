package dev.creoii.bulletforge.object.definition;

import com.badlogic.gdx.math.Vector2;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.creoii.bulletforge.object.definition.path.BulletPathType;
import dev.creoii.bulletforge.object.definition.path.StraightBulletPathType;
import dev.creoii.bulletforge.util.Codecs;

public interface BulletNodeDefinition {
    Codec<BulletNodeDefinition> CODEC = BulletNodeDefinition.Type.CODEC.dispatch(BulletNodeDefinition::type, type -> switch (type) {
        case GROUP -> BulletGroupDefinition.CODEC;
        default -> BulletDefinition.CODEC;
    });

    Type type();

    float lifetime();

    Vector2 velocity();

    float rotation();

    BulletPathType<?> path();

    void setPath(BulletPathType<?> pathType);

    BulletNodeDefinition copy();

    static <T extends BulletNodeDefinition> RecordCodecBuilder<T, Float> lifetimeField() {
        return Codec.FLOAT.optionalFieldOf("lifetime", 1000f).forGetter(BulletNodeDefinition::lifetime);
    }

    static <T extends BulletNodeDefinition> RecordCodecBuilder<T, Vector2> velocityField() {
        return Codecs.VELOCITY.optionalFieldOf("velocity", new Vector2(100f, 0f)).forGetter(BulletNodeDefinition::velocity);
    }

    static <T extends BulletNodeDefinition> RecordCodecBuilder<T, Float> rotationField() {
        return Codec.floatRange(0f, 360f).optionalFieldOf("rotation", 0f).forGetter(BulletNodeDefinition::rotation);
    }

    static <T extends BulletNodeDefinition> RecordCodecBuilder<T, BulletPathType<?>> pathField() {
        return BulletPathType.CODEC.fieldOf("path").orElse(StraightBulletPathType.TYPE_INSTANCE).forGetter(BulletNodeDefinition::path);
    }

    enum Type {
        SINGLE,
        GROUP;

        public static final Codec<Type> CODEC = Codec.STRING.xmap(s -> Type.valueOf(s.toUpperCase()), type -> type.name().toLowerCase());
    }
}
