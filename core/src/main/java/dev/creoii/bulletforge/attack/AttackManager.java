package dev.creoii.bulletforge.attack;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.bullet.Bullet;
import dev.creoii.bulletforge.definition.AttackDefinition;
import dev.creoii.bulletforge.definition.BulletDefinition;
import dev.creoii.bulletforge.render.screen.EditorScreen;
import dev.creoii.bulletforge.util.Tickable;

public class AttackManager implements InputProcessor, Tickable {
    private final BulletForge main;
    private final EditorScreen parent;
    private boolean autofire;
    private boolean attacking;

    public AttackManager(BulletForge main, EditorScreen parent) {
        this.main = main;
        this.parent = parent;
        autofire = true;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (!autofire && button == Input.Buttons.LEFT) {
            attacking = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!autofire && button == Input.Buttons.LEFT) {
            attacking = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        attacking = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return attacking;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public void tick(float dt) {
        if (autofire || attacking) {
            AttackDefinition attack = AttackDefinition.DEFAULT;

            float baseAngle = -attack.arcGap() * (attack.bulletCount() - 1) / 2f;

            Vector3 center = main.getInputHandler().getCenterPos();
            Vector3 mouseDir = main.getInputHandler().getDirectionToMouse(center);

            Vector2 up = new Vector2(-mouseDir.y, mouseDir.x);
            float x = center.x + mouseDir.x * up.x;
            float y = center.y + mouseDir.y * up.y;

            for (int i = 0; i < attack.bulletCount(); ++i) {
                float angle = (baseAngle + i * attack.arcGap()) + attack.angleOffset();

                float radians = angle * MathUtils.degreesToRadians;
                float cos = MathUtils.cos(radians);
                float sin = MathUtils.sin(radians);

                float rotatedX = mouseDir.x * cos - mouseDir.y * sin;
                float rotatedY = mouseDir.y * cos + mouseDir.x * sin;

                Bullet bullet = new Bullet(BulletDefinition.DEFAULT);
                bullet.spawn(new Vector2(x, y), new Vector2(rotatedX, rotatedY));
                parent.getBulletManager().addBullet(bullet);
            }
        }
    }

    public boolean isAutofire() {
        return autofire;
    }

    public void setAutofire(boolean autofire) {
        this.autofire = autofire;
        if (autofire) attacking = false;
    }
}
