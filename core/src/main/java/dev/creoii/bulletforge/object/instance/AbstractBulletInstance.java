package dev.creoii.bulletforge.object.instance;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import dev.creoii.bulletforge.object.definition.BulletNodeDefinition;
import dev.creoii.bulletforge.object.definition.path.BulletPathType;
import dev.creoii.bulletforge.object.definition.path.OrbitBulletPathType;
import dev.creoii.bulletforge.util.manager.BulletManager;
import dev.creoii.bulletforge.util.provider.BulletForgeValueTypes;
import dev.creoii.providerlib.api.context.Context;

public abstract class AbstractBulletInstance implements BulletNode {
    private final Context context;
    private BulletNodeDefinition definition;
    private long id;
    protected float age;
    protected float distanceTravelled;
    protected final Vector2 pos;
    protected final Vector2 spawnPos;
    protected final Vector2 direction;
    protected BulletPathType.Instance<?> pathInstance;
    protected BulletManager parent;
    protected int index;
    protected boolean dead;

    public AbstractBulletInstance() {
        context = new Context();
        pos = new Vector2();
        spawnPos = new Vector2();
        direction = new Vector2();
        pathInstance = null;
        parent = null;
        index = 0;
        dead = false;

        context.set(BulletForgeValueTypes.AGE, age);
        context.set(BulletForgeValueTypes.POSITION, pos);
        context.set(BulletForgeValueTypes.DIRECTION, direction);
    }

    @Override
    public void tick(float dt) {
        if (dead) return;

        age += dt;

        float speed = definition.velocity().len();
        distanceTravelled += speed * dt;

        Vector2 pathOffset = pathInstance.getPathOffset(this, distanceTravelled);

        float dirX = direction.x;
        float dirY = direction.y;

        float perpX = -dirY;
        float perpY = dirX;

        float pathX = perpX * pathOffset.x + dirX * pathOffset.y;
        float pathY = perpY * pathOffset.x + dirY * pathOffset.y;

        pos.set(spawnPos.x + pathX, spawnPos.y + pathY);

        if (age >= definition.lifetime() / 1000f) {
            setDead(true);
        } else updateContext();
    }

    public abstract void render(Batch batch);

    @Override
    public void reset() {
        definition = null;
        id = -1L;
        age = 0f;
        distanceTravelled = 0f;
        pos.setZero();
        spawnPos.setZero();
        direction.setZero();
        parent = null;
        index = 0;
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
        pathInstance = definition.path().create();
    }

    @Override
    public BulletNodeDefinition get() {
        return definition;
    }

    @Override
    public void spawn(float x, float y, float dirX, float dirY, int index) {
        pos.set(x, y);
        spawnPos.set(x, y);
        direction.set(dirX, dirY);

        if (pathInstance instanceof OrbitBulletPathType.OrbitBulletPathInstance instance && parent instanceof BulletGroupInstance groupInstance) {
            int siblings = groupInstance.childrenCount() - 1;
            float phase = siblings <= 1 ? 0f : MathUtils.PI2 * index / siblings;
            instance.setOrbitPhase(phase);
        }
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
    public Vector2 spawnPos() {
        return spawnPos;
    }

    @Override
    public float incrementAge(float f) {
        return age += f;
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
