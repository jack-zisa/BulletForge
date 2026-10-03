package dev.creoii.bulletforge.object.definition;

import com.badlogic.gdx.math.Vector2;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.creoii.bulletforge.object.definition.path.BulletPathType;
import dev.creoii.bulletforge.object.definition.path.StraightBulletPathType;
import dev.creoii.bulletforge.util.editor.EditorSerializable;

import java.util.Objects;

public final class BulletEmitterDefinition implements BulletNodeDefinition {
    public static final MapCodec<BulletEmitterDefinition> CODEC = RecordCodecBuilder.mapCodec(instance -> {
        return instance.group(
            BulletNodeDefinition.lifetimeField(),
            BulletNodeDefinition.velocityField(),
            BulletNodeDefinition.rotationField(),
            BulletNodeDefinition.pathField(),
            AttackDefinition.CODEC.fieldOf("attack").forGetter(BulletEmitterDefinition::attack)
        ).apply(instance, BulletEmitterDefinition::new);
    });
    @EditorSerializable
    private float lifetime;
    @EditorSerializable
    private final Vector2 velocity;
    @EditorSerializable
    private float rotation;
    @EditorSerializable
    private BulletPathType<?> path;
    @EditorSerializable
    private final AttackDefinition attack;

    public BulletEmitterDefinition(float lifetime, Vector2 velocity, float rotation, BulletPathType<?> path, AttackDefinition attack) {
        this.lifetime = lifetime;
        this.velocity = velocity;
        this.rotation = rotation;
        this.path = path;
        this.attack = attack;
    }

    public BulletEmitterDefinition(float lifetime, float speed) {
        this(lifetime, new Vector2(speed, 0f), 0f, StraightBulletPathType.TYPE_INSTANCE, AttackDefinition.DEFAULT.copy());
    }

    @Override
    public Type type() {
        return Type.EMITTER;
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

    @Override
    public BulletPathType<?> path() {
        return path;
    }

    @Override
    public void setPath(BulletPathType<?> pathType) {
        path = pathType;
    }

    public AttackDefinition attack() {
        return attack;
    }

    @Override
    public BulletEmitterDefinition copy() {
        return new BulletEmitterDefinition(lifetime, velocity.cpy(), rotation, path, attack.copy());
    }

    public void set(BulletEmitterDefinition bulletEmitter) {
        lifetime = bulletEmitter.lifetime;
        velocity.set(bulletEmitter.velocity);
        rotation = bulletEmitter.rotation;
        attack.set(bulletEmitter.attack);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        var that = (BulletEmitterDefinition) obj;
        return Float.floatToIntBits(lifetime) == Float.floatToIntBits(that.lifetime) &&
            Objects.equals(velocity, that.velocity) &&
            Float.floatToIntBits(rotation) == Float.floatToIntBits(that.rotation) &&
            Objects.equals(attack, that.attack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lifetime, velocity, rotation, attack);
    }

    @Override
    public String toString() {
        return "BulletDefinition[" +
            "lifetime=" + lifetime + ", " +
            "velocity=" + velocity + ", " +
            "rotation=" + rotation + ", " +
            "attack=" + attack + ']';
    }
}
