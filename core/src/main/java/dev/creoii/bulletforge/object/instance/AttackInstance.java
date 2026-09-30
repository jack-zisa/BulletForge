package dev.creoii.bulletforge.object.instance;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import dev.creoii.bulletforge.object.definition.BulletNodeDefinition;
import dev.creoii.bulletforge.util.manager.AttackManager;
import dev.creoii.bulletforge.object.definition.OffsetModifiers;
import dev.creoii.bulletforge.object.definition.AttackDefinition;

public class AttackInstance {
    private final AttackManager manager;
    private final AttackDefinition definition;
    private float timer;

    public AttackInstance(AttackManager manager, AttackDefinition definition) {
        this.manager = manager;
        this.definition = definition;
    }

    public void tick(float dt) {
        timer -= dt;

        if (timer <= 0f) {
            timer += definition.attackSpeed() / 1000f;
            fire();
        }
    }

    private void fire() {
        BulletNodeDefinition bulletDefinition = manager.getParent().getConfig().bullets().getBullet(definition.bulletId());
        if (bulletDefinition == null)
            return;

        float baseAngle = -definition.arcGap() * (definition.bulletCount() - 1) / 2f;

        Vector3 origin = manager.getMain().getInputHandler().getCenterPos();
        Vector3 mousePos = manager.getMain().getInputHandler().getMousePos();

        OffsetModifiers offsetModifiers = definition.offsetModifiers();
        Vector2 offset = definition.offset();

        Vector2 effectiveOffset = new Vector2(offset);

        if (offsetModifiers.rotate()) {
            Vector2 mouseDirection = new Vector2(mousePos.x - origin.x, mousePos.y - origin.y).nor();
            float angle = MathUtils.atan2(mouseDirection.y, mouseDirection.x);
            effectiveOffset.rotateRad(angle);
        }

        Vector3 spawnPos = new Vector3(origin).add(effectiveOffset.x, effectiveOffset.y, 0f);

        if (offsetModifiers.affectMouse()) {
            mousePos.add(effectiveOffset.x, effectiveOffset.y, 0f);
        }

        Vector3 mouseDir = manager.shouldTargetMouse() ? new Vector3(mousePos).sub(spawnPos).nor() : new Vector3(Vector3.X);

        for (int i = 0; i < definition.bulletCount(); ++i) {
            float angle = (baseAngle + i * definition.arcGap()) + definition.angleOffset();

            float radians = angle * MathUtils.degreesToRadians;
            float cos = MathUtils.cos(radians);
            float sin = MathUtils.sin(radians);

            float rotatedX = mouseDir.x * cos - mouseDir.y * sin;
            float rotatedY = mouseDir.y * cos + mouseDir.x * sin;

            BulletNode bulletNode = bulletDefinition.type() == BulletNodeDefinition.Type.SINGLE ? manager.getParent().getBulletManager().getBulletPool().obtain() : manager.getParent().getBulletManager().getBulletGroupPool().obtain();
            bulletNode.set(bulletDefinition);
            bulletNode.spawn(spawnPos.x, spawnPos.y, rotatedX, rotatedY);
            manager.getParent().getBulletManager().addBullet(bulletNode);
        }
    }
}
