package dev.creoii.bulletforge.object.definition;

import com.badlogic.gdx.math.Vector2;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.creoii.bulletforge.util.editor.EditorSerializable;

import java.util.Objects;

public final class BulletDefinition implements BulletNodeDefinition {
    public static final BulletDefinition DEFAULT = new BulletDefinition(2500f, 100f);
    public static final MapCodec<BulletDefinition> CODEC = RecordCodecBuilder.mapCodec(instance -> {
        return instance.group(
            BulletNodeDefinition.lifetimeField(),
            BulletNodeDefinition.velocityField(),
            BulletNodeDefinition.rotationField(),
            DisplayDefinition.CODEC.optionalFieldOf("display", DisplayDefinition.DEFAULT).forGetter(BulletDefinition::display)
        ).apply(instance, BulletDefinition::new);
    });
    @EditorSerializable
    private float lifetime;
    @EditorSerializable
    private final Vector2 velocity;
    @EditorSerializable
    private float rotation;
    @EditorSerializable
    private final DisplayDefinition display;

    public BulletDefinition(float lifetime, Vector2 velocity, float rotation, DisplayDefinition display) {
        this.lifetime = lifetime;
        this.velocity = velocity;
        this.rotation = rotation;
        this.display = display;
    }

    public BulletDefinition(float lifetime, float speed) {
        this(lifetime, new Vector2(speed, 0f), 0f, DisplayDefinition.DEFAULT);
    }

    public BulletDefinition(float lifetime, float speed, float rotation) {
        this(lifetime, new Vector2(speed, 0f), rotation, DisplayDefinition.DEFAULT);
    }

    public BulletDefinition(float lifetime, float speed, float rotation, DisplayDefinition display) {
        this(lifetime, new Vector2(speed, 0f), rotation, display);
    }

    public BulletDefinition(float lifetime, float speed, float rotation, float curve) {
        this(lifetime, new Vector2(speed, curve), rotation, DisplayDefinition.DEFAULT);
    }

    public BulletDefinition(float lifetime, float speed, float curve, float rotation, DisplayDefinition display) {
        this(lifetime, new Vector2(speed, curve), rotation, display);
    }

    @Override
    public Type type() {
        return Type.SINGLE;
    }

    public float lifetime() {
        return lifetime;
    }

    public Vector2 velocity() {
        return velocity;
    }

    public float rotation() {
        return rotation;
    }

    public DisplayDefinition display() {
        return display;
    }

    @Override
    public BulletDefinition copy() {
        return new BulletDefinition(lifetime, velocity.cpy(), rotation, display.copy());
    }

    public void set(BulletDefinition attack) {
        lifetime = attack.lifetime;
        velocity.set(attack.velocity);
        rotation = attack.rotation;
        display.set(attack.display);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        var that = (BulletDefinition) obj;
        return Float.floatToIntBits(lifetime) == Float.floatToIntBits(that.lifetime) &&
            Objects.equals(velocity, that.velocity) &&
            Float.floatToIntBits(rotation) == Float.floatToIntBits(that.rotation) &&
            Objects.equals(display, that.display);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lifetime, velocity, rotation, display);
    }

    @Override
    public String toString() {
        return "BulletDefinition[" +
            "lifetime=" + lifetime + ", " +
            "velocity=" + velocity + ", " +
            "rotation=" + rotation + ", " +
            "display=" + display + ']';
    }
}
