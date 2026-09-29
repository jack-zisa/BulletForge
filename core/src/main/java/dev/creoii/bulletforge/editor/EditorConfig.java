package dev.creoii.bulletforge.editor;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.creoii.bulletforge.object.definition.AttackDefinition;
import dev.creoii.bulletforge.object.definition.BulletDictionaryDefinition;

import java.util.List;

public record EditorConfig(List<AttackDefinition> attacks, BulletDictionaryDefinition bullets) {
    public static final Codec<EditorConfig> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            AttackDefinition.CODEC.listOf().fieldOf("attacks").forGetter(EditorConfig::attacks),
            BulletDictionaryDefinition.CODEC.fieldOf("bullets").forGetter(EditorConfig::bullets)
        ).apply(instance, EditorConfig::new);
    });

    public EditorConfig() {
        this(Lists.newArrayList(AttackDefinition.DEFAULT.copy()), BulletDictionaryDefinition.DEFAULT);
    }

    public void set(EditorConfig config) {
        attacks.clear();
        attacks.addAll(config.attacks);
        bullets.clear();
        config.bullets.forEach((_, bulletDefinition) -> bullets.addBullet(bulletDefinition));
    }
}
