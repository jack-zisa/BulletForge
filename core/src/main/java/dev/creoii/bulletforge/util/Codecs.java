package dev.creoii.bulletforge.util;

import com.badlogic.gdx.math.Vector2;
import com.mojang.serialization.Codec;

import java.util.List;

public final class Codecs {
    public static final Codec<Vector2> VECTOR2 = Codec.FLOAT.listOf(2, 2).xmap(floats -> new Vector2(floats.getFirst(), floats.get(1)), vector2 -> List.of(vector2.x, vector2.y));
}
