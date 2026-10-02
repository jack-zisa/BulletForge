package dev.creoii.providerlib.api.value.datatype;

import java.util.Collection;
import java.util.Random;
import java.util.function.Function;

public final class DataTypes {
    public static final DataType<Integer> INTEGER = createNumber(o -> o.toString().isBlank() ? 0 : Integer.parseInt(o.toString()));
    public static final DataType<Byte> BYTE = createNumber(o -> o.toString().isBlank() ? 0 : Byte.parseByte(o.toString()));
    public static final DataType<Short> SHORT = createNumber(o -> o.toString().isBlank() ? 0 : Short.parseShort(o.toString()));
    public static final DataType<Float> FLOAT = createNumber(o -> o.toString().isBlank() ? 0f : Float.parseFloat(o.toString()));
    public static final DataType<Double> DOUBLE = createNumber(o -> o.toString().isBlank() ? 0d : Double.parseDouble(o.toString()));
    public static final DataType<Long> LONG = createNumber(o -> o.toString().isBlank() ? 0L : Long.parseLong(o.toString()));
    public static final DataType<Boolean> BOOLEAN = new DataType<>() {
        @Override
        public Boolean convert(Object input) {
            if (input instanceof Boolean value)
                return value;
            return Boolean.parseBoolean(input.toString());
        }

        @Override
        public Object convertTo(Boolean value, Class<?> targetType) {
            if (targetType == boolean.class || targetType == Boolean.class) return value;
            if (targetType == byte.class || targetType == Byte.class) return (byte) (value ? 1 : 0);
            if (targetType == short.class || targetType == Short.class) return (short) (value ? 1 : 0);
            if (targetType == int.class || targetType == Integer.class) return value ? 1 : 0;
            if (targetType == long.class || targetType == Long.class) return value ? 1L : 0L;
            if (targetType == float.class || targetType == Float.class) return value ? 1f : 0f;
            if (targetType == double.class || targetType == Double.class) return value ? 1d : 0d;
            if (targetType == String.class) return value.toString();
            throw new IllegalArgumentException("Cannot convert Boolean to " + targetType.getSimpleName());
        }
    };
    public static final DataType<String> STRING = new DataType<>() {
        @Override
        public String convert(Object input) {
            return input.toString();
        }

        @Override
        public Object convertTo(String value, Class<?> targetType) {
            if (targetType == String.class)
                return value;
            throw new IllegalArgumentException("Cannot convert String to " + targetType.getSimpleName());
        }
    };
    public static final DataType<Random> RANDOM = new DataType<>() {
        @Override
        public Random convert(Object input) {
            return (Random) input;
        }

        @Override
        public Object convertTo(Random value, Class<?> targetType) {
            if (targetType.isAssignableFrom(Random.class))
                return value;
            throw new IllegalArgumentException("Cannot convert Random to " + targetType.getSimpleName());
        }
    };

    public static <T> DataType<T> object() {
        return new DataType<>() {
            @Override
            @SuppressWarnings("unchecked")
            public T convert(Object input) {
                return (T) input;
            }

            @Override
            public Object convertTo(T value, Class<?> targetType) {
                if (value == null || targetType.isInstance(value))
                    return value;
                throw new IllegalArgumentException("Cannot convert " + value.getClass().getSimpleName() + " to " + targetType.getSimpleName());
            }
        };
    }

    public static <T> DataType<Collection<T>> collection() {
        return new DataType<>() {
            @Override
            @SuppressWarnings("unchecked")
            public Collection<T> convert(Object input) {
                return (Collection<T>) input;
            }

            @Override
            public Object convertTo(Collection<T> value, Class<?> targetType) {
                if (value == null || targetType.isInstance(value))
                    return value;
                throw new IllegalArgumentException("Cannot convert Collection to " + targetType.getSimpleName());
            }
        };
    }

    private static  <T extends Number> DataType<T> createNumber(Function<Object, T> converter) {
        return new DataType<>() {
            @Override
            public T convert(Object input) {
                return converter.apply(input);
            }

            @Override
            public Object convertTo(T value, Class<?> targetType) {
                return convertNumber(value, targetType);
            }
        };
    }

    private static Object convertNumber(Number number, Class<?> targetType) {
        return switch (targetType.getName()) {
            case "int", "java.lang.Integer" -> number.intValue();
            case "float", "java.lang.Float" -> number.floatValue();
            case "double", "java.lang.Double" -> number.doubleValue();
            case "long", "java.lang.Long" -> number.longValue();
            case "short", "java.lang.Short" -> number.shortValue();
            case "byte", "java.lang.Byte" -> number.byteValue();
            default -> throw new IllegalArgumentException("Cannot convert number to " + targetType.getSimpleName());
        };
    }
}
