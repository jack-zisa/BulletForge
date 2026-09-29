package dev.creoii.bulletforge.bullet;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.definition.BulletDefinition;
import dev.creoii.bulletforge.render.Renderable;
import dev.creoii.bulletforge.util.Tickable;

public class Bullet implements Tickable, Renderable, Pool.Poolable {
    private BulletDefinition definition;
    private long id;
    private float age;
    private Vector2 pos;
    private Vector2 direction;
    private boolean dead;

    public Bullet() {
        dead = false;
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
    }

    @Override
    public void render(Batch batch) {
        float scale = definition.display().scale();
        batch.draw(GlobalAssets.DEFAULT_BULLET, pos.x - (scale / 2f), pos.y - (scale / 2f), scale, scale);
    }

    @Override
    public void reset() {
        definition = null;
        id = -1L;
        age = 0f;
        pos.setZero();
        direction.setZero();
        dead = false;
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

    public void spawn(Vector2 pos, Vector2 direction) {
        this.pos = pos;
        this.direction = direction;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
