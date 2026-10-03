package dev.creoii.bulletforge.util.manager;

import com.badlogic.gdx.utils.Pool;
import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.object.instance.BulletEmitterInstance;
import dev.creoii.bulletforge.object.instance.BulletGroupInstance;
import dev.creoii.bulletforge.object.instance.BulletInstance;
import dev.creoii.bulletforge.object.instance.BulletNode;
import dev.creoii.bulletforge.render.screen.EditorScreen;

public class GlobalBulletManager extends AbstractBulletManager {
    private final BulletForge main;
    private final EditorScreen editor;

    private final Pool<BulletInstance> bulletPool = new Pool<>() {
        @Override
        protected BulletInstance newObject() {
            return new BulletInstance();
        }
    };

    private final Pool<BulletGroupInstance> bulletGroupPool = new Pool<>() {
        @Override
        protected BulletGroupInstance newObject() {
            return new BulletGroupInstance();
        }
    };

    private final Pool<BulletEmitterInstance> bulletEmitterPool = new Pool<>() {
        @Override
        protected BulletEmitterInstance newObject() {
            return new BulletEmitterInstance();
        }
    };

    public GlobalBulletManager(BulletForge main, EditorScreen editor) {
        this.main = main;
        this.editor = editor;
    }

    public EditorScreen getEditor() {
        return editor;
    }

    @Override
    public BulletManager parent() {
        return null;
    }

    @Override
    protected void free(BulletNode bullet) {
        if (bullet instanceof BulletGroupInstance group) {
            bulletGroupPool.free(group);
        } else if (bullet instanceof BulletEmitterInstance emitter) {
            bulletEmitterPool.free(emitter);
        } else bulletPool.free((BulletInstance) bullet);
    }

    @Override
    public Pool<BulletInstance> getBulletPool() {
        return bulletPool;
    }

    @Override
    public Pool<BulletGroupInstance> getBulletGroupPool() {
        return bulletGroupPool;
    }

    @Override
    public Pool<BulletEmitterInstance> getBulletEmitterPool() {
        return bulletEmitterPool;
    }
}
