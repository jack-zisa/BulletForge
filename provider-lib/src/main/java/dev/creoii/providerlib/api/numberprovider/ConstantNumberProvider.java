package dev.creoii.providerlib.api.numberprovider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.creoii.providerlib.api.context.Context;

public record ConstantNumberProvider(float value) implements NumberProvider {
    public static final ConstantNumberProvider ZERO = new ConstantNumberProvider(0);
    public static final ConstantNumberProvider ONE = new ConstantNumberProvider(1);
    public static final MapCodec<ConstantNumberProvider> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            Codec.FLOAT.fieldOf("value").forGetter(ConstantNumberProvider::value)
        ).apply(instance, ConstantNumberProvider::new)
    );

    @Override
    public Type getType() {
        return Type.CONSTANT;
    }

    @Override
    public Number get(Context context) {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%,d", (int) value);
    }
}
