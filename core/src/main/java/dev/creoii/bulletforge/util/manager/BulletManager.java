package dev.creoii.bulletforge.util.manager;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Pool;
import dev.creoii.bulletforge.object.instance.BulletEmitterInstance;
import dev.creoii.bulletforge.object.instance.BulletGroupInstance;
import dev.creoii.bulletforge.object.instance.BulletInstance;
import dev.creoii.bulletforge.object.instance.BulletNode;

public interface BulletManager {
    BulletManager parent();

    default GlobalBulletManager getMaster() {
        BulletManager manager = this;
        while (manager != null) {
            if (manager instanceof GlobalBulletManager global) return global;
            manager = manager.parent();
        }
        return null;
    }

    long allocateId();

    void freeId(long id);

    void addBullet(BulletNode bullet);

    void adoptBullet(BulletNode bullet);

    void removeBullet(long id);

    void transferBullet(BulletNode bullet);

    void tick(float dt);

    void render(Batch batch);

    void refresh();

    default Pool<BulletInstance> getBulletPool() {
        return parent().getBulletPool();
    }

    default Pool<BulletGroupInstance> getBulletGroupPool() {
        return parent().getBulletGroupPool();
    }

    default Pool<BulletEmitterInstance> getBulletEmitterPool() {
        return parent().getBulletEmitterPool();
    }
}
