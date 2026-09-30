package dev.creoii.bulletforge.object.definition;

import com.mojang.serialization.Codec;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public final class BulletDictionaryDefinition {
    public static final BulletDictionaryDefinition DEFAULT = new BulletDictionaryDefinition(BulletDefinition.DEFAULT);
    public static final Codec<BulletDictionaryDefinition> CODEC = BulletDefinition.CODEC.listOf().xmap(bulletDefinitions -> {
        return new BulletDictionaryDefinition(bulletDefinitions.toArray(BulletDefinition[]::new));
    }, bulletDictionaryDefinition -> {
        return new ArrayList<>(bulletDictionaryDefinition.dictionary.values());
    });
    private final Map<Integer, BulletDefinition> dictionary;
    private int nextId;

    public BulletDictionaryDefinition(Map<Integer, BulletDefinition> dictionary) {
        this.dictionary = new LinkedHashMap<>(dictionary);
        nextId = dictionary.keySet().stream().mapToInt(Integer::intValue).max().orElse(-1) + 1;
    }

    public BulletDictionaryDefinition(BulletDefinition... definitions) {
        dictionary = new LinkedHashMap<>();
        for (int i = 0; i < definitions.length; i++)
            dictionary.put(i, definitions[i]);
        nextId = definitions.length;
    }

    public BulletDictionaryDefinition() {
        dictionary = new LinkedHashMap<>();
    }

    public BulletDictionaryDefinition copy() {
        return new BulletDictionaryDefinition(dictionary);
    }

    public Map<Integer, BulletDefinition> get() {
        return dictionary;
    }

    public void addBullet(BulletDefinition bullet) {
        dictionary.put(nextId++, bullet);
    }

    @Nullable
    public BulletDefinition getBullet(int id) {
        return dictionary.get(id);
    }

    public void removeBullet(int id) {
        dictionary.remove(id);
    }

    public void forEach(BiConsumer<Integer, BulletDefinition> action) {
        dictionary.forEach(action);
    }

    public void clear() {
        dictionary.clear();
        nextId = 0;
    }

    public Collection<BulletDefinition> values() {
        return dictionary.values();
    }
}
