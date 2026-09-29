package dev.creoii.providerlib.api.numberprovider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.creoii.providerlib.api.context.Context;
import dev.creoii.providerlib.api.value.type.ValueTypes;

import java.util.Random;

public record RandomNumberProvider(float min, float max) implements NumberProvider {
    public static final MapCodec<RandomNumberProvider> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            Codec.FLOAT.fieldOf("min").forGetter(RandomNumberProvider::min),
            Codec.FLOAT.fieldOf("max").forGetter(RandomNumberProvider::max)
        ).apply(instance, RandomNumberProvider::new)
    );

    @Override
    public Type getType() {
        return Type.CONSTANT;
    }

    @Override
    public Number get(Context context) {
        if (context.has(ValueTypes.RANDOM)) {
            Random random = context.get(ValueTypes.RANDOM);
            return random.nextFloat(max) - min;
        }
        throw new IllegalStateException("Cannot call get() on a RandomNumberProvider with no random context.");
    }

    @Override
    public String toString() {
        return String.format("%,d-%,d", (int) min, (int) max);
    }
}
