package dev.creoii.bulletforge.bullet;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.definition.BulletDefinition;
import dev.creoii.bulletforge.render.Renderable;
import dev.creoii.bulletforge.util.Tickable;
import dev.creoii.bulletforge.util.provider.BulletForgeValueTypes;
import dev.creoii.providerlib.api.context.Context;
import dev.creoii.providerlib.api.context.ContextProvider;

public class Bullet implements Tickable, Renderable, Pool.Poolable, ContextProvider {
    private final Context context;
    private BulletDefinition definition;
    private long id;
    private float age;
    private final Vector2 pos;
    private final Vector2 direction;
    private boolean dead;

    public Bullet() {
        context = new Context();
        pos = new Vector2();
        direction = new Vector2();
        dead = false;

        context.set(BulletForgeValueTypes.AGE, age);
        context.set(BulletForgeValueTypes.POSITION, pos);
        context.set(BulletForgeValueTypes.DIRECTION, direction);
    }

    @Override
    public void tick(float dt) {
        if (dead) {
            id = -1L;
            return;
        }

        Vector2 velocity = definition.velocity().cpy().rotateDeg(direction.angleDeg());
        pos.mulAdd(velocity, dt);

        if ((age += dt) >= (definition.lifetime() / 1000f)) {
            setDead(true);
        }

        updateContext();
    }

    @Override
    public void render(Batch batch) {
        float scale = definition.display().scale();
        batch.draw(GlobalAssets.DEFAULT_BULLET, pos.x - (scale / 2f), pos.y - (scale / 2f), scale, scale);
    }

    @Override
    public void reset() {
        context.clear();
        definition = null;
        id = -1L;
        age = 0f;
        pos.setZero();
        direction.setZero();
        dead = false;
    }

    @Override
    public Context context() {
        return context;
    }

    public void updateContext() {
        ((Vector2) context.get(BulletForgeValueTypes.POSITION)).set(pos);
        context.set(BulletForgeValueTypes.AGE, age);
    }

    public long getId() {
        return id;
    }

    public void set(BulletDefinition definition) {
        this.definition = definition;
    }

    public void init(long id) {
        this.id = id;
    }

    public void spawn(float x, float y, float dirX, float dirY) {
        pos.set(x, y);
        direction.set(dirX, dirY);
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
