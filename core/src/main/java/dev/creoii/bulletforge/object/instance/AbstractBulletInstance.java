package dev.creoii.bulletforge.object.instance;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import dev.creoii.bulletforge.object.definition.BulletNodeDefinition;
import dev.creoii.bulletforge.util.manager.BulletManager;
import dev.creoii.bulletforge.util.provider.BulletForgeValueTypes;
import dev.creoii.providerlib.api.context.Context;

public abstract class AbstractBulletInstance implements BulletNode {
    private final Context context;
    private BulletNodeDefinition definition;
    private long id;
    protected float age;
    protected final Vector2 pos;
    protected final Vector2 direction;
    protected BulletManager parent;
    protected boolean dead;

    public AbstractBulletInstance() {
        context = new Context();
        pos = new Vector2();
        direction = new Vector2();
        parent = null;
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
        } else updateContext();
    }

    public abstract void render(Batch batch);

    @Override
    public void reset() {
        definition = null;
        id = -1L;
        age = 0f;
        pos.setZero();
        direction.setZero();
        parent = null;
        dead = false;

        updateContext();
    }

    @Override
    public Context context() {
        return context;
    }

    public void updateContext() {
        ((Vector2) context.get(BulletForgeValueTypes.POSITION)).set(pos);
        context.set(BulletForgeValueTypes.AGE, age);
    }

    @Override
    public long id() {
        return id;
    }

    @Override
    public void init(long id) {
        this.id = id;
    }

    @Override
    public void set(BulletNodeDefinition definition) {
        this.definition = definition;
    }

    @Override
    public BulletNodeDefinition get() {
        return definition;
    }

    public void spawn(float x, float y, float dirX, float dirY) {
        pos.set(x, y);
        direction.set(dirX, dirY);
    }

    @Override
    public BulletManager parent() {
        return parent;
    }

    @Override
    public void setParent(BulletManager parent) {
        this.parent = parent;
    }

    @Override
    public Vector2 pos() {
        return pos;
    }

    @Override
    public float incrementAge(float f) {
        return age += f;
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
