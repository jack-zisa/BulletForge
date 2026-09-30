package dev.creoii.bulletforge.util.manager;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.object.definition.AttackDefinition;
import dev.creoii.bulletforge.object.instance.AttackInstance;
import dev.creoii.bulletforge.render.screen.EditorScreen;
import dev.creoii.bulletforge.util.Tickable;

import java.util.ArrayList;
import java.util.List;

public class AttackManager implements InputProcessor, Tickable {
    private final BulletForge main;
    private final EditorScreen parent;
    private boolean autofire;
    private boolean targetMouse;
    private boolean attacking;
    private final List<AttackInstance> attacks;

    public AttackManager(BulletForge main, EditorScreen parent) {
        this.main = main;
        this.parent = parent;
        autofire = true;
        targetMouse = false;
        attacks = new ArrayList<>();

        parent.getConfig().attacks().forEach(attackDefinition -> {
            attacks.add(new AttackInstance(this, attackDefinition));
        });
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
        if (!autofire && !attacking) {
            return;
        }

        attacks.forEach(attackDefinition -> attackDefinition.tick(dt));
    }

    public void addAttack(AttackDefinition attack) {
        attacks.add(new AttackInstance(this, attack));
    }

    public void refresh() {
        attacks.clear();
        parent.getConfig().attacks().forEach(attackDefinition -> {
            attacks.add(new AttackInstance(this, attackDefinition));
        });
    }

    public BulletForge getMain() {
        return main;
    }

    public EditorScreen getParent() {
        return parent;
    }

    public boolean isAutofire() {
        return autofire;
    }

    public void setAutofire(boolean autofire) {
        this.autofire = autofire;
        if (autofire) attacking = false;
    }

    public boolean shouldTargetMouse() {
        return targetMouse;
    }

    public void setTargetMouse(boolean targetMouse) {
        this.targetMouse = targetMouse;
    }
}
