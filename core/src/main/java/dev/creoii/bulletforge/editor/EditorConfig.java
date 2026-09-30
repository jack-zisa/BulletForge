package dev.creoii.bulletforge.editor;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.creoii.bulletforge.object.definition.AttackDefinition;
import dev.creoii.bulletforge.object.definition.BulletDictionaryDefinition;

import java.util.List;
import java.util.Objects;

public final class EditorConfig {
    public static final Codec<EditorConfig> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            AttackDefinition.CODEC.listOf().fieldOf("attacks").forGetter(EditorConfig::attacks),
            BulletDictionaryDefinition.CODEC.fieldOf("bullets").forGetter(EditorConfig::bullets)
        ).apply(instance, EditorConfig::new);
    });
    private final List<AttackDefinition> attacks;
    private final BulletDictionaryDefinition bullets;
    private boolean dirty;

    public EditorConfig(List<AttackDefinition> attacks, BulletDictionaryDefinition bullets) {
        this.attacks = attacks;
        this.bullets = bullets;
    }

    public EditorConfig() {
        this(Lists.newArrayList(AttackDefinition.DEFAULT.copy()), BulletDictionaryDefinition.DEFAULT.copy());
    }

    public void set(EditorConfig config) {
        attacks.clear();
        config.attacks.forEach(attackDefinition -> attacks.add(attackDefinition.copy()));
        bullets.clear();
        config.bullets.forEach((_, bulletDefinition) -> bullets.addBullet(bulletDefinition.copy()));
        dirty = true;
    }

    public List<AttackDefinition> attacks() {
        return attacks;
    }

    public BulletDictionaryDefinition bullets() {
        return bullets;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setNotDirty() {
        dirty = false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (EditorConfig) obj;
        return Objects.equals(this.attacks, that.attacks) &&
            Objects.equals(this.bullets, that.bullets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attacks, bullets);
    }

    @Override
    public String toString() {
        return "EditorConfig[" +
            "attacks=" + attacks + ", " +
            "bullets=" + bullets + ']';
    }

}
