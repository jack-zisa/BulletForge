package dev.creoii.bulletforge.object.instance;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import dev.creoii.bulletforge.object.definition.BulletNodeDefinition;
import dev.creoii.bulletforge.render.Renderable;
import dev.creoii.bulletforge.util.Tickable;
import dev.creoii.bulletforge.util.manager.BulletManager;
import dev.creoii.providerlib.api.context.ContextProvider;

public interface BulletNode extends Tickable, Renderable, Pool.Poolable, ContextProvider {
    long id();

    void init(long id);

    void set(BulletNodeDefinition definition);

    BulletNodeDefinition get();

    void spawn(float x, float y, float dirX, float dirY, int index);

    BulletManager parent();

    default int childrenCount() {
        return 0;
    }

    void setParent(BulletManager parent);

    default void detach() {
        setParent(null);
    }

    Vector2 pos();

    Vector2 spawnPos();

    float incrementAge(float f);

    int index();

    boolean isDead();
}
