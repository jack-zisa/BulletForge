package dev.creoii.bulletforge.object.definition;

import com.badlogic.gdx.math.Vector2;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.creoii.bulletforge.attack.OffsetModifiers;
import dev.creoii.bulletforge.util.Codecs;
import dev.creoii.bulletforge.util.editor.EditorSerializable;

import java.util.Objects;

public final class AttackDefinition {
    public static final AttackDefinition DEFAULT = new AttackDefinition(0, 100, 1, 0f, 0f, new Vector2(), OffsetModifiers.DEFAULT.copy());
    public static final Codec<AttackDefinition> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            Codec.INT.fieldOf("bullet_id").forGetter(AttackDefinition::bulletId),
            Codec.INT.optionalFieldOf("attack_speed", 100).forGetter(AttackDefinition::attackSpeed),
            Codec.INT.optionalFieldOf("bullet_count", 1).forGetter(AttackDefinition::bulletCount),
            Codec.FLOAT.optionalFieldOf("arc_gap", 0f).forGetter(AttackDefinition::arcGap),
            Codec.FLOAT.optionalFieldOf("angle_offset", 0f).forGetter(AttackDefinition::angleOffset),
            Codecs.VECTOR2.optionalFieldOf("offset", new Vector2()).forGetter(AttackDefinition::offset),
            OffsetModifiers.CODEC.optionalFieldOf("offset_modifiers", OffsetModifiers.DEFAULT).forGetter(AttackDefinition::offsetModifiers)
        ).apply(instance, AttackDefinition::new);
    });
    @EditorSerializable
    private int bulletId;
    @EditorSerializable
    private int attackSpeed;
    @EditorSerializable
    private int bulletCount;
    @EditorSerializable
    private float arcGap;
    @EditorSerializable
    private float angleOffset;
    @EditorSerializable
    private final Vector2 offset;
    @EditorSerializable
    private final OffsetModifiers offsetModifiers;

    public AttackDefinition(int bulletId, int attackSpeed, int bulletCount, float arcGap, float angleOffset, Vector2 offset, OffsetModifiers offsetModifiers) {
        this.bulletId = bulletId;
        this.attackSpeed = attackSpeed;
        this.bulletCount = bulletCount;
        this.arcGap = arcGap;
        this.angleOffset = angleOffset;
        this.offset = offset;
        this.offsetModifiers = offsetModifiers;
    }

    public int bulletId() {
        return bulletId;
    }

    public int attackSpeed() {
        return attackSpeed;
    }

    public int bulletCount() {
        return bulletCount;
    }

    public float arcGap() {
        return arcGap;
    }

    public float angleOffset() {
        return angleOffset;
    }

    public Vector2 offset() {
        return offset;
    }

    public OffsetModifiers offsetModifiers() {
        return offsetModifiers;
    }

    public AttackDefinition copy() {
        return new AttackDefinition(bulletId, attackSpeed, bulletCount, arcGap, angleOffset, offset.cpy(), offsetModifiers.copy());
    }

    public void set(AttackDefinition attack) {
        this.bulletId = attack.bulletId;
        this.attackSpeed = attack.attackSpeed;
        this.bulletCount = attack.bulletCount;
        this.arcGap = attack.arcGap;
        this.angleOffset = attack.angleOffset;
        offset.set(attack.offset);
        offsetModifiers.set(attack.offsetModifiers);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        var that = (AttackDefinition) obj;
        return bulletId == that.bulletId &&
            attackSpeed == that.attackSpeed &&
            bulletCount == that.bulletCount &&
            Float.floatToIntBits(arcGap) == Float.floatToIntBits(that.arcGap) &&
            Float.floatToIntBits(angleOffset) == Float.floatToIntBits(that.angleOffset) &&
            offset.equals(that.offset) &&
            offsetModifiers.equals(that.offsetModifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bulletId, attackSpeed, bulletCount, arcGap, angleOffset, offset);
    }

    @Override
    public String toString() {
        return "AttackDefinition[" +
            "bulletId=" + bulletId + ", " +
            "attackSpeed=" + attackSpeed + ", " +
            "bulletCount=" + bulletCount + ", " +
            "arcGap=" + arcGap + ", " +
            "angleOffset=" + angleOffset + ", " +
            "offset=" + offset + ", " +
            "offsetModifiers=" + offsetModifiers + ']';
    }
}
