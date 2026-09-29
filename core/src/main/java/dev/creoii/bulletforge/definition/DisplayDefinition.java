package dev.creoii.bulletforge.definition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record DisplayDefinition(String spriteId, float scale, String shaderId) {
    public static final DisplayDefinition DEFAULT = new DisplayDefinition("", 1f, "");
    public static final Codec<DisplayDefinition> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            Codec.STRING.optionalFieldOf("sprite_id", "").forGetter(DisplayDefinition::spriteId),
            Codec.FLOAT.optionalFieldOf("scale", 1f).forGetter(DisplayDefinition::scale),
            Codec.STRING.optionalFieldOf("shader_id", "").forGetter(DisplayDefinition::shaderId)
        ).apply(instance, DisplayDefinition::new);
    });

    public DisplayDefinition(String spriteId, String shaderId) {
        this(spriteId, 1f, shaderId);
    }
}
