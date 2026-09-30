package dev.creoii.bulletforge.object.instance;

import com.badlogic.gdx.utils.Pool;
import dev.creoii.bulletforge.object.definition.BulletNodeDefinition;
import dev.creoii.bulletforge.render.Renderable;
import dev.creoii.bulletforge.util.Tickable;
import dev.creoii.providerlib.api.context.ContextProvider;

public interface BulletNode<T extends BulletNodeDefinition> extends Tickable, Renderable, Pool.Poolable, ContextProvider {
    void init(long id);

    void set(T definition);

    T get();

    void spawn(float x, float y, float dirX, float dirY);

    long id();

    boolean isDead();
}
