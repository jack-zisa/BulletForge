package dev.creoii.bulletforge.attack;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.bullet.Bullet;
import dev.creoii.bulletforge.definition.BulletDefinition;
import dev.creoii.bulletforge.render.screen.EditorScreen;
import dev.creoii.bulletforge.util.Tickable;

public class AttackManager implements InputProcessor, Tickable {
    private final BulletForge main;
    private final EditorScreen parent;
    private boolean attacking = false;

    public AttackManager(BulletForge main, EditorScreen parent) {
        this.main = main;
        this.parent = parent;
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
        if (button == Input.Buttons.LEFT) {
            attacking = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
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
        if (attacking) {
            Bullet bullet = new Bullet(BulletDefinition.DEFAULT);
            Vector3 center = main.getInputHandler().getCenterPos();
            Vector3 mouseDir = main.getInputHandler().getDirectionToMouse(center);
            bullet.spawn(new Vector2(center.x, center.y), new Vector2(mouseDir.x, mouseDir.y));
            parent.getBulletManager().addBullet(bullet);
        }
    }

    public boolean isAttacking() {
        return attacking;
    }
}
