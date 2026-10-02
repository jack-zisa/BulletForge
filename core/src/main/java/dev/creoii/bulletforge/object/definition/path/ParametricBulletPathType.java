package dev.creoii.bulletforge.object.definition.path;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.creoii.bulletforge.object.instance.BulletNode;
import dev.creoii.bulletforge.util.Codecs;

import java.util.Objects;

public final class ParametricBulletPathType implements BulletPathType<ParametricBulletPathType.ParametricBulletPathInstance> {
    public static final MapCodec<ParametricBulletPathType> TYPE_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        ParametricType.CODEC.fieldOf("parametric_type").orElse(ParametricType.FIGURE_EIGHT).forGetter(ParametricBulletPathType::parametricType),
        Codecs.VECTOR2.fieldOf("scale").orElse(new Vector2(1f, 1f)).forGetter(ParametricBulletPathType::scale)
    ).apply(instance, ParametricBulletPathType::new));
    private ParametricType parametricType;
    private final Vector2 scale;

    public ParametricBulletPathType(ParametricType parametricType, Vector2 scale) {
        this.parametricType = parametricType;
        this.scale = scale;
    }

    @Override
    public Type type() {
        return Type.PARAMETRIC;
    }

    @Override
    public ParametricBulletPathInstance create() {
        return new ParametricBulletPathInstance(this);
    }

    public ParametricType parametricType() {
        return parametricType;
    }

    public void setParametricType(ParametricType parametricType) {
        this.parametricType = parametricType;
    }

    public Vector2 scale() {
        return scale;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ParametricBulletPathType) obj;
        return Objects.equals(this.parametricType, that.parametricType) &&
            Objects.equals(this.scale, that.scale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parametricType, scale);
    }

    @Override
    public String toString() {
        return "ParametricBulletPathType[" +
            "parametricType=" + parametricType + ", " +
            "scale=" + scale + ']';
    }

    public static class ParametricBulletPathInstance extends Instance<ParametricBulletPathType> {
        public ParametricBulletPathInstance(ParametricBulletPathType definition) {
            super(definition);
        }

        @Override
        public Vector2 getPathOffset(BulletNode node, float t) {
            float x = 0f;
            float y = 0f;

            switch (getType().parametricType) {
                case CIRCLE -> {
                    x = MathUtils.cos(t);
                    y = MathUtils.sin(t);
                }
                case FIGURE_EIGHT -> {
                    x = MathUtils.sin(t);
                    y = MathUtils.sin(t * 2f);
                }
                case SPIRAL -> {
                    float r = t * .1f;
                    x = MathUtils.cos(t) * r;
                    y = MathUtils.sin(t) * r;
                }
                case ROSE -> {
                    float r = MathUtils.cos(5f * t);
                    x = r * MathUtils.cos(t);
                    y = r * MathUtils.sin(t);
                }
                case HEART -> {
                    x = 16f * MathUtils.sin(t) * MathUtils.sin(t) * MathUtils.sin(t);
                    y = 13f * MathUtils.cos(t) - 5f * MathUtils.cos(2f * t) - 2f * MathUtils.cos(3f * t) - MathUtils.cos(4f * t);
                    x /= 16f;
                    y /= 16f;
                }
                case ASTROID -> {
                    x = MathUtils.cos(t);
                    x = x * x * x;
                    y = MathUtils.sin(t);
                    y = y * y * y;
                }
                case LISSAJOUS -> {
                    x = MathUtils.sin(3f * t + MathUtils.PI / 2f);
                    y = MathUtils.sin(2f * t);
                }
            }
            setOffset(x * getType().scale.x, y * getType().scale.y);
            return getOffset();
        }

        @Override
        public ParametricBulletPathInstance copy() {
            return new ParametricBulletPathInstance(getType());
        }
    }

    public enum ParametricType {
        CIRCLE,
        FIGURE_EIGHT,
        SPIRAL,
        ROSE,
        HEART,
        ASTROID,
        LISSAJOUS;

        public static final Codec<ParametricType> CODEC = Codec.STRING.xmap(s -> ParametricType.valueOf(s.toUpperCase()), type -> type.name().toLowerCase());
    }
}
