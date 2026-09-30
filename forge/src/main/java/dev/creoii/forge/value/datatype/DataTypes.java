package dev.creoii.forge.value.datatype;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class DataTypes {
    public static final Map<String, DataType<?>> DATA_TYPES = new HashMap<>();
    private static final Random GLOBAL = new Random();

    private static <T> DataType<T> register(String id, DataType<T> dataType) {
        DATA_TYPES.put(id, dataType);
        return dataType;
    }

    public static final DataType<Void> VOID = register("void", new DataType<>() {
        @Override
        public boolean isType(Object o) {
            return true;
        }

        @Override
        public Void convert(Object input) {
            return null;
        }

        @Override
        public Void defaultValue() {
            return null;
        }
    });
    public static final DataType<Object> ANY = register("any", new DataType<>() {
        @Override
        public boolean isType(Object o) {
            return true;
        }

        @Override
        public Object convert(Object input) {
            return input;
        }

        @Override
        public Object defaultValue() {
            return null;
        }
    });
    public static final DataType<String> STRING = register("string", new DataType<>() {
        @Override
        public boolean isType(Object o) {
            return o instanceof String;
        }

        @Override
        public String convert(Object input) {
            return input.toString();
        }

        @Override
        public String defaultValue() {
            return "";
        }
    });
    public static final DataType<Number> NUMBER = register("number", new DataType<>() {
        @Override
        public boolean isType(Object o) {
            return o instanceof Number;
        }

        @Override
        public Number convert(Object input) {
            return Double.parseDouble(input.toString());
        }

        @Override
        public Number defaultValue() {
            return 0d;
        }
    });
    public static final DataType<Boolean> BOOLEAN = register("boolean", new DataType<>() {
        @Override
        public boolean isType(Object o) {
            return o instanceof Boolean;
        }

        @Override
        public Boolean convert(Object input) {
            return Boolean.parseBoolean(input.toString());
        }

        @Override
        public Boolean defaultValue() {
            return false;
        }
    });
    public static final DataType<Random> RANDOM = register("random", new DataType<>() {
        @Override
        public boolean isType(Object o) {
            return o instanceof Random;
        }

        @Override
        public Random convert(Object input) {
            return (Random) input;
        }

        @Override
        public Random defaultValue() {
            return GLOBAL;
        }
    });
}
