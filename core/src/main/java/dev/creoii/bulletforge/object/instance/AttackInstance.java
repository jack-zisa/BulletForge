package dev.creoii.bulletforge.object.instance;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import dev.creoii.bulletforge.attack.AttackManager;
import dev.creoii.bulletforge.object.definition.AttackDefinition;
import dev.creoii.bulletforge.object.definition.BulletDefinition;

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
        BulletDefinition bulletDefinition = manager.getParent().getConfig().bullets().getBullet(definition.bulletId());
        if (bulletDefinition == null)
            return;

        float baseAngle = -definition.arcGap() * (definition.bulletCount() - 1) / 2f;

        Vector3 center = manager.getMain().getInputHandler().getCenterPos();
        Vector3 mouseDir = manager.shouldTargetMouse() ? manager.getMain().getInputHandler().getDirectionToMouse(center) : Vector3.X;

        Vector2 up = new Vector2(-mouseDir.y, mouseDir.x);
        float x = center.x + mouseDir.x * up.x;
        float y = center.y + mouseDir.y * up.y;

        for (int i = 0; i < definition.bulletCount(); ++i) {
            float angle = (baseAngle + i * definition.arcGap()) + definition.angleOffset();

            float radians = angle * MathUtils.degreesToRadians;
            float cos = MathUtils.cos(radians);
            float sin = MathUtils.sin(radians);

            float rotatedX = mouseDir.x * cos - mouseDir.y * sin;
            float rotatedY = mouseDir.y * cos + mouseDir.x * sin;

            BulletInstance bullet = manager.getParent().getBulletManager().getBulletPool().obtain();
            bullet.set(bulletDefinition);
            bullet.spawn(x, y, rotatedX, rotatedY);
            manager.getParent().getBulletManager().addBullet(bullet);
        }
    }
}
