package dev.creoii.bulletforge.object.instance;

import com.badlogic.gdx.graphics.g2d.Batch;
import dev.creoii.bulletforge.object.definition.BulletEmitterDefinition;
import dev.creoii.bulletforge.object.definition.BulletNodeDefinition;
import dev.creoii.bulletforge.util.manager.BulletManager;

import java.util.*;

public class BulletEmitterInstance extends AbstractBulletInstance {
    private final List<AttackInstance> attacks;

    public BulletEmitterInstance() {
        attacks = new ArrayList<>();
    }

    @Override
    public void tick(float dt) {
        super.tick(dt);

        for (AttackInstance attack : attacks) {
            attack.setOrigin(pos.x, pos.y);
            attack.rotate(get().rotation() * dt);
            attack.tick(dt);
        }
    }

    @Override
    public void render(Batch batch) {
    }

    @Override
    public void onDead() {
    }

    @Override
    public void reset() {
        super.reset();
        attacks.clear();
    }

    @Override
    public void set(BulletNodeDefinition definition) {
        super.set(definition);
        attacks.clear();
    }

    @Override
    public void setParent(BulletManager parent) {
        super.setParent(parent);

        if (parent != null && get() instanceof BulletEmitterDefinition emitter) {
            AttackInstance attack = new AttackInstance(parent.getMaster().getEditor().getAttackManager(), emitter.attack());
            attack.setShouldQueue(true);
            attacks.add(attack);
        }
    }
}
