package dev.creoii.bulletforge.definition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record AttackDefinition(int attackSpeed, int bulletCount, float arcGap) {
    public static final AttackDefinition DEFAULT = new AttackDefinition(10, 1, 0f);
    public static final Codec<AttackDefinition> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            Codec.INT.optionalFieldOf("attack_speed", 10).forGetter(AttackDefinition::attackSpeed),
            Codec.INT.optionalFieldOf("bullet_count", 1).forGetter(AttackDefinition::bulletCount),
            Codec.FLOAT.optionalFieldOf("arc_gap", 0f).forGetter(AttackDefinition::arcGap)
        ).apply(instance, AttackDefinition::new);
    });
}
