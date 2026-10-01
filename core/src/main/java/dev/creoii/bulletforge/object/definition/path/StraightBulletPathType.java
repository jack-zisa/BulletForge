package dev.creoii.bulletforge.object.definition.path;

import com.badlogic.gdx.math.Vector2;
import com.mojang.serialization.MapCodec;
import dev.creoii.bulletforge.object.instance.BulletNode;

public record StraightBulletPathType() implements BulletPathType<StraightBulletPathType.EmptyBulletPathInstance> {
    public static final StraightBulletPathType TYPE_INSTANCE = new StraightBulletPathType();
    private static final EmptyBulletPathInstance INSTANCE = new EmptyBulletPathInstance(TYPE_INSTANCE);
    public static final MapCodec<StraightBulletPathType> TYPE_CODEC = MapCodec.unit(TYPE_INSTANCE);

    @Override
    public Type type() {
        return Type.STRAIGHT;
    }

    @Override
    public EmptyBulletPathInstance create() {
        return INSTANCE;
    }

    public static class EmptyBulletPathInstance extends Instance<StraightBulletPathType> {
        public EmptyBulletPathInstance(StraightBulletPathType definition) {
            super(definition);
        }

        @Override
        public Vector2 getPathOffset(BulletNode node, float t) {
            setOffset(0f, t);
            return getOffset();
        }

        @Override
        public EmptyBulletPathInstance copy() {
            return INSTANCE;
        }
    }
}
