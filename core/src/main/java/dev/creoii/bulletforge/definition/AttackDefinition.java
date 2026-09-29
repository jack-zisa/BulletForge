package dev.creoii.bulletforge.definition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.creoii.bulletforge.util.editor.EditorSerializable;

import java.util.Objects;

public final class AttackDefinition {
    public static final AttackDefinition DEFAULT = new AttackDefinition(100, 1, 0f, 0f);
    public static final Codec<AttackDefinition> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            Codec.INT.fieldOf("attack_speed").forGetter(AttackDefinition::attackSpeed),
            Codec.INT.fieldOf("bullet_count").forGetter(AttackDefinition::bulletCount),
            Codec.FLOAT.fieldOf("arc_gap").forGetter(AttackDefinition::arcGap),
            Codec.FLOAT.fieldOf("angle_offset").forGetter(AttackDefinition::angleOffset)
        ).apply(instance, AttackDefinition::new);
    });
    @EditorSerializable
    private int attackSpeed;
    @EditorSerializable
    private int bulletCount;
    @EditorSerializable
    private float arcGap;
    @EditorSerializable
    private float angleOffset;

    public AttackDefinition(int attackSpeed, int bulletCount, float arcGap, float angleOffset) {
        this.attackSpeed = attackSpeed;
        this.bulletCount = bulletCount;
        this.arcGap = arcGap;
        this.angleOffset = angleOffset;
    }

    public int attackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public int bulletCount() {
        return bulletCount;
    }

    public void setBulletCount(int bulletCount) {
        this.bulletCount = bulletCount;
    }

    public float arcGap() {
        return arcGap;
    }

    public void setArcGap(float arcGap) {
        this.arcGap = arcGap;
    }

    public float angleOffset() {
        return angleOffset;
    }

    public void setAngleOffset(float angleOffset) {
        this.angleOffset = angleOffset;
    }

    public AttackDefinition copy() {
        return new AttackDefinition(attackSpeed, bulletCount, arcGap, angleOffset);
    }

    public void set(AttackDefinition attack) {
        this.attackSpeed = attack.attackSpeed;
        this.bulletCount = attack.bulletCount;
        this.arcGap = attack.arcGap;
        this.angleOffset = attack.angleOffset;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (AttackDefinition) obj;
        return this.attackSpeed == that.attackSpeed &&
            this.bulletCount == that.bulletCount &&
            Float.floatToIntBits(this.arcGap) == Float.floatToIntBits(that.arcGap) &&
            Float.floatToIntBits(this.angleOffset) == Float.floatToIntBits(that.angleOffset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attackSpeed, bulletCount, arcGap, angleOffset);
    }

    @Override
    public String toString() {
        return "AttackDefinition[" +
            "attackSpeed=" + attackSpeed + ", " +
            "bulletCount=" + bulletCount + ", " +
            "arcGap=" + arcGap + ", " +
            "angleOffset=" + angleOffset + ']';
    }
}
