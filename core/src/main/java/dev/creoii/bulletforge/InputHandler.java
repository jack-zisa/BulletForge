package dev.creoii.bulletforge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;

public class InputHandler extends InputAdapter {
    private final BulletForge main;
    private final Vector3 centerPos;
    private final Vector3 mousePos;

    public InputHandler(BulletForge main) {
        this.main = main;
        centerPos = new Vector3();
        mousePos = new Vector3();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.F3) {
            main.toggleDebug();
            return true;
        }
        return false;
    }

    public Vector3 getMousePos() {
        mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0f);
        main.getCamera().unproject(mousePos);
        return mousePos;
    }

    public Vector3 getCenterPos() {
        centerPos.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0f);
        main.getCamera().unproject(centerPos);
        return centerPos;
    }
}
