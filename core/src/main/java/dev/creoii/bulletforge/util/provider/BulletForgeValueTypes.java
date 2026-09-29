package dev.creoii.bulletforge.util.provider;

import dev.creoii.providerlib.api.value.datatype.DataTypes;
import dev.creoii.providerlib.api.value.type.ValueType;

public class BulletForgeValueTypes {
    public static final ValueType AGE = () -> DataTypes.FLOAT;
    public static final ValueType POSITION = () -> BulletForgeDataTypes.VEC2;
    public static final ValueType DIRECTION = () -> BulletForgeDataTypes.VEC2;
}
