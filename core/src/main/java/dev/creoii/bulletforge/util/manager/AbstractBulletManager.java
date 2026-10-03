package dev.creoii.bulletforge.util.manager;

import com.badlogic.gdx.graphics.g2d.Batch;
import dev.creoii.bulletforge.object.instance.BulletGroupInstance;
import dev.creoii.bulletforge.object.instance.BulletNode;

import java.util.*;

public abstract class AbstractBulletManager implements BulletManager {
    protected final Map<Long, BulletNode> bullets;
    protected final List<BulletNode> toAdd;
    protected final Set<Long> toRemove;
    protected final PriorityQueue<Long> ids;
    protected long nextId;

    protected AbstractBulletManager() {
        bullets = new HashMap<>();
        toAdd = new ArrayList<>();
        toRemove = new HashSet<>();
        ids = new PriorityQueue<>();
        nextId = 0L;
    }

    @Override
    public long allocateId() {
        return ids.isEmpty() ? nextId++ : ids.poll();
    }

    @Override
    public void freeId(long id) {
        ids.add(id);
    }

    @Override
    public void addBullet(BulletNode bullet) {
        bullet.init(allocateId());
        adoptBullet(bullet);
    }

    public void queueAddBullet(BulletNode bullet) {
        bullet.init(allocateId());
        bullet.setParent(this);
        toAdd.add(bullet);
        nextId = Math.max(nextId, bullet.id() + 1);

        if (bullet instanceof BulletGroupInstance group) {
            group.initManager(this);
        }
    }

    @Override
    public void adoptBullet(BulletNode bullet) {
        bullet.setParent(this);
        addBulletInternal(bullet);
        nextId = Math.max(nextId, bullet.id() + 1);

        if (bullet instanceof BulletGroupInstance group) {
            group.initManager(this);
        }
    }

    private void addBulletInternal(BulletNode bullet) {
        bullets.put(bullet.id(), bullet);
    }

    @Override
    public void removeBullet(long id) {
        BulletNode bullet = bullets.remove(id);
        if (bullet == null) return;

        bullet.setParent(null);
        freeId(id);
        free(bullet);
    }

    @Override
    public void transferBullet(BulletNode bullet) {
        bullets.remove(bullet.id());
        parent().adoptBullet(bullet);
    }

    @Override
    public void tick(float dt) {
        for (BulletNode bullet : bullets.values()) {
            bullet.tick(dt);

            if (bullet.isDead()) {
                toRemove.add(bullet.id());
            }
        }

        for (BulletNode bullet : toAdd) {
            addBulletInternal(bullet);
        }
        toAdd.clear();

        toRemove.forEach(this::removeBullet);
        toRemove.clear();
    }

    @Override
    public void render(Batch batch) {
        bullets.values().forEach(bullet -> bullet.render(batch));
    }

    @Override
    public void refresh() {
        bullets.clear();
        toAdd.clear();
        toRemove.clear();
        ids.clear();
        nextId = 0L;
    }

    protected abstract void free(BulletNode bullet);
}
