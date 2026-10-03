package dev.creoii.bulletforge.object.definition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.creoii.bulletforge.util.editor.EditorSerializable;

import java.util.Objects;

public final class AttackDefinition {
    public static final AttackDefinition DEFAULT = new AttackDefinition(0, 100, 1, 0f, 0f, 0f, true, Offset.DEFAULT.copy());
    public static final Codec<AttackDefinition> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            Codec.INT.fieldOf("bullet_id").forGetter(AttackDefinition::bulletId),
            Codec.INT.optionalFieldOf("attack_speed", 100).forGetter(AttackDefinition::attackSpeed),
            Codec.INT.optionalFieldOf("bullet_count", 1).forGetter(AttackDefinition::bulletCount),
            Codec.FLOAT.optionalFieldOf("arc_gap", 0f).forGetter(AttackDefinition::arcGap),
            Codec.FLOAT.optionalFieldOf("angle_offset", 0f).forGetter(AttackDefinition::angleOffset),
            Codec.floatRange(0f, 360f).optionalFieldOf("rotation", 0f).forGetter(AttackDefinition::rotation),
            Codec.BOOL.optionalFieldOf("rotate_on_shoot", true).forGetter(AttackDefinition::rotateOnShoot),
            Offset.CODEC.optionalFieldOf("offset", Offset.DEFAULT.copy()).forGetter(AttackDefinition::offset)
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
    private float rotation;
    @EditorSerializable
    private boolean rotateOnShoot;
    @EditorSerializable
    private final Offset offset;

    public AttackDefinition(int bulletId, int attackSpeed, int bulletCount, float arcGap, float angleOffset, float rotation, boolean rotateOnShoot, Offset offset) {
        this.bulletId = bulletId;
        this.attackSpeed = attackSpeed;
        this.bulletCount = bulletCount;
        this.arcGap = arcGap;
        this.angleOffset = angleOffset;
        this.rotation = rotation;
        this.rotateOnShoot = rotateOnShoot;
        this.offset = offset;
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

    public float rotation() {
        return rotation;
    }

    public boolean rotateOnShoot() {
        return rotateOnShoot;
    }

    public Offset offset() {
        return offset;
    }

    public AttackDefinition copy() {
        return new AttackDefinition(bulletId, attackSpeed, bulletCount, arcGap, angleOffset, rotation, rotateOnShoot, offset.copy());
    }

    public void set(AttackDefinition attack) {
        this.bulletId = attack.bulletId;
        this.attackSpeed = attack.attackSpeed;
        this.bulletCount = attack.bulletCount;
        this.arcGap = attack.arcGap;
        this.angleOffset = attack.angleOffset;
        this.rotation = attack.rotation;
        this.rotateOnShoot = attack.rotateOnShoot;
        offset.set(attack.offset);
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
            Float.floatToIntBits(rotation) == Float.floatToIntBits(that.rotation) &&
            rotateOnShoot == that.rotateOnShoot &&
            offset.equals(that.offset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bulletId, attackSpeed, bulletCount, arcGap, angleOffset, rotation, rotateOnShoot, offset);
    }

    @Override
    public String toString() {
        return "AttackDefinition[" +
            "bulletId=" + bulletId + ", " +
            "attackSpeed=" + attackSpeed + ", " +
            "bulletCount=" + bulletCount + ", " +
            "arcGap=" + arcGap + ", " +
            "angleOffset=" + angleOffset + ", " +
            "rotation=" + rotation + ", " +
            "rotateOnShoot=" + rotateOnShoot + ", " +
            "offset=" + offset;
    }
}
