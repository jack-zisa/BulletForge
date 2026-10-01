package dev.creoii.bulletforge.util;

import com.badlogic.gdx.math.Vector2;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import java.util.List;
import java.util.function.Function;

public final class Codecs {
    public static final Codec<Vector2> VECTOR2 = Codec.FLOAT.listOf(2, 2).xmap(floats -> new Vector2(floats.getFirst(), floats.get(1)), vector2 -> List.of(vector2.x, vector2.y));

    public static final Codec<Vector2> VELOCITY = Codec.either(Codec.FLOAT, Codecs.VECTOR2).xmap(either -> {
        return either.map(f -> new Vector2(f, 0f), Function.identity());
    }, Either::right);

    public static final Codec<Long> TIME = Codec.either(Codec.STRING, longRange(0L, Long.MAX_VALUE)).xmap(either -> {
        return either.map(Codecs::parseMs, Function.identity());
    }, Either::right);

    public static Codec<Long> longRange(final long minInclusive, final long maxInclusive) {
        final Function<Long, DataResult<Long>> checker = Codec.checkRange(minInclusive, maxInclusive);
        return Codec.LONG.flatXmap(checker, checker);
    }

    private static long parseMs(String s) {
        long ms = -1L;
        try {
            ms = Long.parseLong(s);
        } catch (NumberFormatException e) {
            for (TimePeriod period : TimePeriod.values()) {
                if (s.endsWith(period.suffix())) {
                    s = s.replace(period.suffix(), "");
                    return period.toMs(Long.parseLong(s));
                }
            }
        }
        return ms;
    }
}
