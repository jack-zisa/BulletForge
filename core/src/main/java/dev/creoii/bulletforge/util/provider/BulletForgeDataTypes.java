package dev.creoii.bulletforge.util.provider;

import com.badlogic.gdx.math.Vector2;
import dev.creoii.providerlib.api.value.datatype.DataType;

public class BulletForgeDataTypes {
    public static final DataType<Vector2> VEC2 = input -> (Vector2) input;
}
