package dev.creoii.bulletforge.util.provider;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector4;
import dev.creoii.providerlib.api.value.datatype.DataType;

public class BulletForgeDataTypes {
    public static final DataType<Vector2> VEC2 = new DataType<>() {
        @Override
        public Vector2 convert(Object input) {
            if (input instanceof Vector2 vector2) return vector2;
            else if (input instanceof Vector3 vector3) return new Vector2(vector3.x, vector3.y);
            else if (input instanceof Vector4 vector4) return new Vector2(vector4.x, vector4.y);
            else if (input instanceof Number number) return new Vector2(number.floatValue(), 0f);
            throw new IllegalArgumentException("Cannot convert " + input.getClass().getSimpleName() + " to Vector2");
        }

        @Override
        public Object convertTo(Vector2 value, Class<?> targetType) {
            if (targetType == Vector2.class) return value;
            if (targetType == Vector3.class) return new Vector3(value.x, value.y, 0f);
            if (targetType == Vector4.class) return new Vector4(value.x, value.y, 0f, 0f);
            if (targetType.isAssignableFrom(Number.class)) return value.x;
            throw new IllegalArgumentException("Cannot convert Vector2 to " + targetType.getSimpleName());
        }
    };
}
