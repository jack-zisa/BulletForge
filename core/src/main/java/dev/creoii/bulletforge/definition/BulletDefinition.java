package dev.creoii.bulletforge.definition;

import com.badlogic.gdx.math.Vector2;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.creoii.bulletforge.util.Codecs;

import java.util.function.Function;

public record BulletDefinition(float lifetime, Vector2 velocity, DisplayDefinition display) {
    public static final BulletDefinition DEFAULT = new BulletDefinition(100f, 100f);
    private static final Codec<Vector2> VELOCITY_CODEC = Codec.either(Codec.FLOAT, Codecs.VECTOR2).xmap(either -> {
        return either.map(f -> new Vector2(f, 1f), Function.identity());
    }, Either::right);
    public static final Codec<BulletDefinition> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            Codec.FLOAT.optionalFieldOf("lifetime", 100f).forGetter(BulletDefinition::lifetime),
            VELOCITY_CODEC.optionalFieldOf("velocity", new Vector2(100f, 0f)).forGetter(BulletDefinition::velocity),
            DisplayDefinition.CODEC.optionalFieldOf("display", DisplayDefinition.DEFAULT).forGetter(BulletDefinition::display)
        ).apply(instance, BulletDefinition::new);
    });

    public BulletDefinition(float lifetime, float speed) {
        this(lifetime, new Vector2(speed, 0f), DisplayDefinition.DEFAULT);
    }

    public BulletDefinition(float lifetime, float speed, DisplayDefinition display) {
        this(lifetime, new Vector2(speed, 0f), display);
    }

    public BulletDefinition(float lifetime, float speed, float curve) {
        this(lifetime, new Vector2(speed, curve), DisplayDefinition.DEFAULT);
    }

    public BulletDefinition(float lifetime, float speed, float curve, DisplayDefinition display) {
        this(lifetime, new Vector2(speed, curve), display);
    }
}
