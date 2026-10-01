package dev.creoii.bulletforge.object.definition.path;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.creoii.bulletforge.object.instance.BulletNode;
import dev.creoii.bulletforge.util.editor.EditorSerializable;

import java.util.Objects;

public final class OrbitBulletPathType implements BulletPathType<OrbitBulletPathType.OrbitBulletPathInstance> {
    public static final MapCodec<OrbitBulletPathType> TYPE_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        Codec.INT.fieldOf("sides").orElse(-1).forGetter(OrbitBulletPathType::sides),
        Codec.FLOAT.fieldOf("orbit_radius").orElse(1f).forGetter(OrbitBulletPathType::orbitRadius)
    ).apply(instance, OrbitBulletPathType::new));
    @EditorSerializable
    private int sides;
    @EditorSerializable
    private float orbitRadius;

    public OrbitBulletPathType(int sides, float orbitRadius) {
        this.sides = sides;
        this.orbitRadius = orbitRadius;
    }

    @Override
    public Type type() {
        return Type.ORBIT;
    }

    @Override
    public OrbitBulletPathInstance create() {
        return new OrbitBulletPathInstance(this);
    }

    public int sides() {
        return sides;
    }

    public float orbitRadius() {
        return orbitRadius;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (OrbitBulletPathType) obj;
        return this.sides == that.sides &&
            Float.floatToIntBits(this.orbitRadius) == Float.floatToIntBits(that.orbitRadius);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sides, orbitRadius);
    }

    @Override
    public String toString() {
        return "OrbitBulletPathType[" +
            "sides=" + sides + ", " +
            "orbitRadius=" + orbitRadius + ']';
    }

    public static class OrbitBulletPathInstance extends Instance<OrbitBulletPathType> {
        private float orbitPhase;

        public OrbitBulletPathInstance(OrbitBulletPathType definition) {
            super(definition);
        }

        public void setOrbitPhase(float orbitPhase) {
            this.orbitPhase = orbitPhase;
        }

        @Override
        public void reset() {
            super.reset();
            orbitPhase = 0f;
        }

        /**
         * Returns a point along a circle whose radius is defined by the {@link OrbitBulletPathType#orbitRadius}. The return value is treated as an offset, meaning it assumes the center of the circle is at (0,0).
         */
        @Override
        public Vector2 getPathOffset(BulletNode node, float t) {
            float angle = (t / 1000f) * MathUtils.PI2 + orbitPhase;
            int sides = getType().sides();
            float radius = getType().orbitRadius();

            if (sides < 0) {
                setOffset(MathUtils.sin(angle) * radius, MathUtils.cos(angle) * radius);
            } else if (sides <= 1) {
                setOffset(0f, 0f);
            } else if (sides == 2) {
                float x = MathUtils.sin(angle) * radius;
                setOffset(0f, x);
            } else {
                float sector = MathUtils.PI2 / sides;
                float localAngle = (angle % sector) - sector / 2f;

                float apothem = radius * MathUtils.cos(MathUtils.PI / sides);
                float polygonRadius = apothem / MathUtils.cos(localAngle);

                setOffset(MathUtils.sin(angle) * polygonRadius, MathUtils.cos(angle) * polygonRadius);
            }
            return getOffset();
        }

        @Override
        public OrbitBulletPathInstance copy() {
            return new OrbitBulletPathInstance(getType());
        }
    }
}
