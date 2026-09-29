package dev.creoii.bulletforge.definition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.creoii.bulletforge.util.editor.EditorSerializable;

import java.util.Objects;

public final class DisplayDefinition {
    public static final DisplayDefinition DEFAULT = new DisplayDefinition("", 10f, "");
    public static final Codec<DisplayDefinition> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            Codec.STRING.optionalFieldOf("sprite_id", "").forGetter(DisplayDefinition::spriteId),
            Codec.FLOAT.optionalFieldOf("scale", 10f).forGetter(DisplayDefinition::scale),
            Codec.STRING.optionalFieldOf("shader_id", "").forGetter(DisplayDefinition::shaderId)
        ).apply(instance, DisplayDefinition::new);
    });
    @EditorSerializable
    private String spriteId;
    @EditorSerializable
    private float scale;
    @EditorSerializable
    private String shaderId;

    public DisplayDefinition(String spriteId, float scale, String shaderId) {
        this.spriteId = spriteId;
        this.scale = scale;
        this.shaderId = shaderId;
    }

    public DisplayDefinition(String spriteId, String shaderId) {
        this(spriteId, 10f, shaderId);
    }

    public String spriteId() {
        return spriteId;
    }

    public void setSpriteId(String spriteId) {
        this.spriteId = spriteId;
    }

    public float scale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public String shaderId() {
        return shaderId;
    }

    public void setShaderId(String shaderId) {
        this.shaderId = shaderId;
    }

    public DisplayDefinition copy() {
        return new DisplayDefinition(spriteId, scale, shaderId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (DisplayDefinition) obj;
        return Objects.equals(this.spriteId, that.spriteId) &&
            Float.floatToIntBits(this.scale) == Float.floatToIntBits(that.scale) &&
            Objects.equals(this.shaderId, that.shaderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spriteId, scale, shaderId);
    }

    @Override
    public String toString() {
        return "DisplayDefinition[" +
            "spriteId=" + spriteId + ", " +
            "scale=" + scale + ", " +
            "shaderId=" + shaderId + ']';
    }
}
