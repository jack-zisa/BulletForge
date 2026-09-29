package dev.creoii.bulletforge.bullet;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Pool;
import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.object.instance.BulletInstance;
import dev.creoii.bulletforge.render.Renderable;
import dev.creoii.bulletforge.render.screen.EditorScreen;
import dev.creoii.bulletforge.util.Tickable;

import java.util.*;
import java.util.function.Consumer;

public class BulletManager implements Tickable, Renderable {
    private final BulletForge main;
    private final EditorScreen parent;
    private final Pool<BulletInstance> bulletPool = new Pool<>() {
        @Override
        protected BulletInstance newObject() {
            return new BulletInstance();
        }
    };
    private final Map<Long, BulletInstance> bullets;
    private final Set<Long> toRemove;
    private final PriorityQueue<Long> ids;
    private long nextId;

    public BulletManager(BulletForge main, EditorScreen parent) {
        this.main = main;
        this.parent = parent;
        bullets = new HashMap<>();
        toRemove = new HashSet<>();
        ids = new PriorityQueue<>();
        nextId = 0L;
    }

    @Override
    public void tick(float dt) {
        forEach(bullet -> {
            bullet.tick(dt);

            if (bullet.isDead()) toRemove.add(bullet.getId());
        });

        toRemove.forEach(this::removeBullet);
        toRemove.clear();
    }

    @Override
    public void render(Batch batch) {
        forEach(bullet -> bullet.render(batch));
    }

    public Pool<BulletInstance> getBulletPool() {
        return bulletPool;
    }

    public void addBullet(BulletInstance bullet) {
        long id = ids.isEmpty() ? nextId++ : ids.poll();
        bullet.init(id);
        bullets.put(id, bullet);
    }

    public void addBullet(long id, BulletInstance bullet) {
        bullets.put(id, bullet);
        nextId = Math.max(nextId, id + 1);
    }

    public void removeBullet(long id) {
        BulletInstance removed;
        if ((removed = bullets.remove(id)) != null) {
            bulletPool.free(removed);
            ids.add(id);
        }
    }

    public void clearBullets() {
        bullets.clear();
        ids.clear();
        nextId = 0L;
    }

    public void forEach(Consumer<BulletInstance> action) {
        bullets.values().forEach(action);
    }

    public int count() {
        return bullets.size();
    }
}
