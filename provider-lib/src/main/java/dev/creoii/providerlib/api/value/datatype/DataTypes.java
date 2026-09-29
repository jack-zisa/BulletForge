package dev.creoii.providerlib.api.value.datatype;

import java.util.Random;

public final class DataTypes {
    public static final DataType<Integer> INTEGER = input -> Integer.parseInt(input.toString());
    public static final DataType<String> STRING = Object::toString;
    public static final DataType<Byte> BYTE = input -> Byte.parseByte(input.toString());
    public static final DataType<Short> SHORT = input -> Short.parseShort(input.toString());
    public static final DataType<Float> FLOAT = input -> Float.parseFloat(input.toString());
    public static final DataType<Double> DOUBLE = input -> Double.parseDouble(input.toString());
    public static final DataType<Long> LONG = input -> Long.parseLong(input.toString());
    public static final DataType<Boolean> BOOLEAN = input -> Boolean.parseBoolean(input.toString());
    public static final DataType<Random> RANDOM = input -> (Random) input;
}
