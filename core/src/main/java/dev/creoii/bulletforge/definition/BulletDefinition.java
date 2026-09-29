package dev.creoii.bulletforge.definition;

import com.badlogic.gdx.math.Vector2;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.creoii.bulletforge.util.Codecs;
import dev.creoii.bulletforge.util.editor.EditorSerializable;

import java.util.Objects;
import java.util.function.Function;

public final class BulletDefinition {
    public static final BulletDefinition DEFAULT = new BulletDefinition(2500f, 100f);
    private static final Codec<Vector2> VELOCITY_CODEC = Codec.either(Codec.FLOAT, Codecs.VECTOR2).xmap(either -> {
        return either.map(f -> new Vector2(f, 0f), Function.identity());
    }, Either::right);
    public static final Codec<BulletDefinition> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            Codec.FLOAT.fieldOf("lifetime").forGetter(BulletDefinition::lifetime),
            VELOCITY_CODEC.fieldOf("velocity").forGetter(BulletDefinition::velocity),
            DisplayDefinition.CODEC.fieldOf("display").forGetter(BulletDefinition::display)
        ).apply(instance, BulletDefinition::new);
    });
    @EditorSerializable
    private float lifetime;
    @EditorSerializable
    private final Vector2 velocity;
    @EditorSerializable
    private final DisplayDefinition display;

    public BulletDefinition(float lifetime, Vector2 velocity, DisplayDefinition display) {
        this.lifetime = lifetime;
        this.velocity = velocity;
        this.display = display;
    }

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

    public float lifetime() {
        return lifetime;
    }

    public void setLifetime(float lifetime) {
        this.lifetime = lifetime;
    }

    public Vector2 velocity() {
        return velocity;
    }

    public void setVelocity(float x, float y) {
        velocity.set(x, y);
    }

    public DisplayDefinition display() {
        return display;
    }

    public BulletDefinition copy() {
        return new BulletDefinition(lifetime, velocity.cpy(), display.copy());
    }

    public void set(BulletDefinition attack) {
        this.lifetime = attack.lifetime;
        velocity.set(attack.velocity);
        display.set(attack.display);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (BulletDefinition) obj;
        return Float.floatToIntBits(this.lifetime) == Float.floatToIntBits(that.lifetime) &&
            Objects.equals(this.velocity, that.velocity) &&
            Objects.equals(this.display, that.display);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lifetime, velocity, display);
    }

    @Override
    public String toString() {
        return "BulletDefinition[" +
            "lifetime=" + lifetime + ", " +
            "velocity=" + velocity + ", " +
            "display=" + display + ']';
    }
}
