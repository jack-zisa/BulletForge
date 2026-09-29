package dev.creoii.bulletforge.editor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.creoii.bulletforge.definition.AttackDefinition;
import dev.creoii.bulletforge.definition.BulletDefinition;

public record EditorConfig(AttackDefinition attack, BulletDefinition bullet) {
    public static final Codec<EditorConfig> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            AttackDefinition.CODEC.fieldOf("attack").forGetter(EditorConfig::attack),
            BulletDefinition.CODEC.fieldOf("bullet").forGetter(EditorConfig::bullet)
        ).apply(instance, EditorConfig::new);
    });

    public EditorConfig() {
        this(AttackDefinition.DEFAULT.copy(), BulletDefinition.DEFAULT.copy());
    }

    public void set(EditorConfig config) {
        this.attack.set(config.attack);
        this.bullet.set(config.bullet);
    }
}
