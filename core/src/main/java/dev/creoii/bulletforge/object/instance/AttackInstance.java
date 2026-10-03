package dev.creoii.bulletforge.object.instance;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import dev.creoii.bulletforge.object.definition.BulletNodeDefinition;
import dev.creoii.bulletforge.util.manager.AttackManager;
import dev.creoii.bulletforge.object.definition.Offset;
import dev.creoii.bulletforge.object.definition.AttackDefinition;

public class AttackInstance {
    private final AttackManager manager;
    private final AttackDefinition definition;
    private final Vector2 origin;
    private float rotation;
    private float timer;
    private boolean shouldQueue;

    public AttackInstance(AttackManager manager, AttackDefinition definition) {
        this.manager = manager;
        this.definition = definition;
        origin = new Vector2();
    }

    public void setOrigin(float x, float y) {
        origin.set(x, y);
    }

    public void setShouldQueue(boolean shouldQueue) {
        this.shouldQueue = shouldQueue;
    }

    public void tick(float dt) {
        timer -= dt;

        if (timer <= 0f) {
            timer += definition.attackSpeed() / 1000f;
            fire();

            if (definition.rotateOnShoot()) rotate(definition.rotation());
        }

        if (!definition.rotateOnShoot())
            rotate(definition.rotation());
    }

    public void rotate(float angle) {
        rotation += angle;
        rotation %= 360f;
    }

    private void fire() {
        BulletNodeDefinition bulletDefinition = manager.getParent().getConfig().bullets().getBullet(definition.bulletId());
        if (bulletDefinition == null)
            return;

        float baseAngle = -definition.arcGap() * (definition.bulletCount() - 1) / 2f;

        Vector3 mousePos = manager.getMain().getInputHandler().getMousePos();

        Offset offset = definition.offset();

        Vector2 effectiveOffset = offset.offset().cpy();

        if (offset.rotate()) {
            Vector2 mouseDirection = new Vector2(mousePos.x, mousePos.y).nor();
            float angle = MathUtils.atan2(mouseDirection.y, mouseDirection.x);
            effectiveOffset.rotateRad(angle);
        }

        Vector3 spawnPos = new Vector3(effectiveOffset.x + origin.x, effectiveOffset.y + origin.y, 0f);

        if (offset.affectMouse()) {
            mousePos.add(effectiveOffset.x, effectiveOffset.y, 0f);
        }

        Vector3 mouseDir = manager.shouldTargetMouse() ? new Vector3(mousePos).sub(spawnPos).nor() : Vector3.X.cpy();

        for (int i = 0; i < definition.bulletCount(); ++i) {
            float angle = (baseAngle + i * definition.arcGap()) + definition.angleOffset() + rotation;

            float radians = angle * MathUtils.degreesToRadians;
            float cos = MathUtils.cos(radians);
            float sin = MathUtils.sin(radians);

            float rotatedX = mouseDir.x * cos - mouseDir.y * sin;
            float rotatedY = mouseDir.y * cos + mouseDir.x * sin;

            BulletNode bulletNode = switch (bulletDefinition.type()) {
                case SINGLE -> manager.getParent().getBulletManager().getBulletPool().obtain();
                case GROUP -> manager.getParent().getBulletManager().getBulletGroupPool().obtain();
                case EMITTER -> manager.getParent().getBulletManager().getBulletEmitterPool().obtain();
            };

            if (bulletNode instanceof BulletGroupInstance group) {
                group.initManager(manager.getParent().getBulletManager());
            }

            bulletNode.set(bulletDefinition);
            bulletNode.spawn(spawnPos.x, spawnPos.y, rotatedX, rotatedY, i);

            if (shouldQueue) {
                manager.getParent().getBulletManager().queueAddBullet(bulletNode);
            } else manager.getParent().getBulletManager().addBullet(bulletNode);
        }
    }
}
