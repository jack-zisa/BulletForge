package dev.creoii.bulletforge.object.definition;

import com.badlogic.gdx.math.Vector2;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.creoii.bulletforge.util.editor.EditorSerializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class BulletGroupDefinition implements BulletNodeDefinition {
    public static final BulletGroupDefinition DEFAULT = new BulletGroupDefinition(2500f, 100f);
    public static final MapCodec<BulletGroupDefinition> CODEC = RecordCodecBuilder.mapCodec(instance -> {
        return instance.group(
            BulletNodeDefinition.lifetimeField(),
            BulletNodeDefinition.velocityField(),
            BulletNodeDefinition.rotationField(),
            Child.CODEC.listOf().optionalFieldOf("children", new ArrayList<>()).forGetter(BulletGroupDefinition::children)
        ).apply(instance, BulletGroupDefinition::new);
    });
    @EditorSerializable
    private float lifetime;
    @EditorSerializable
    private final Vector2 velocity;
    @EditorSerializable
    private float rotation;
    @EditorSerializable(type = Child.class)
    private final List<Child> children;

    public BulletGroupDefinition(float lifetime, Vector2 velocity, float rotation, List<Child> children) {
        this.lifetime = lifetime;
        this.velocity = velocity;
        this.rotation = rotation;
        this.children = children;
    }

    public BulletGroupDefinition(float lifetime, float speed) {
        this(lifetime, new Vector2(speed, 0f), 0f, new ArrayList<>());
    }

    public BulletGroupDefinition(float lifetime, float speed, float rotation) {
        this(lifetime, new Vector2(speed, 0f), rotation, new ArrayList<>());
    }

    public BulletGroupDefinition(float lifetime, float speed, float rotation, float curve) {
        this(lifetime, new Vector2(speed, curve), rotation, new ArrayList<>());
    }

    @Override
    public Type type() {
        return Type.GROUP;
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

    public List<Child> children() {
        return children;
    }

    @Override
    public BulletGroupDefinition copy() {
        return new BulletGroupDefinition(lifetime, velocity.cpy(), rotation, new ArrayList<>(children));
    }

    public void set(BulletGroupDefinition attack) {
        lifetime = attack.lifetime;
        velocity.set(attack.velocity);
        rotation = attack.rotation;
        children.clear();
        children.addAll(attack.children);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        var that = (BulletGroupDefinition) obj;
        return Float.floatToIntBits(lifetime) == Float.floatToIntBits(that.lifetime) &&
            Objects.equals(velocity, that.velocity) &&
            Float.floatToIntBits(rotation) == Float.floatToIntBits(that.rotation) &&
            Objects.equals(children, that.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lifetime, velocity, rotation, children);
    }

    @Override
    public String toString() {
        return "BulletDefinition[" +
            "lifetime=" + lifetime + ", " +
            "velocity=" + velocity + ", " +
            "rotation=" + rotation + ", " +
            "display=" + children + ']';
    }

    public record Child(@EditorSerializable Offset offset, @EditorSerializable BulletDefinition bullet) {
        public static final Codec<Child> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Offset.CODEC.fieldOf("offset").orElse(Offset.DEFAULT.copy()).forGetter(Child::offset),
            BulletDefinition.CODEC.fieldOf("bullet").forGetter(Child::bullet)
        ).apply(instance, Child::new));

        public Child() {
            this(Offset.DEFAULT.copy(), BulletDefinition.DEFAULT.copy());
        }
    }
}
