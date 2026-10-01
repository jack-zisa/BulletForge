package dev.creoii.bulletforge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import dev.creoii.bulletforge.render.screen.EditorScreen;

import java.util.Arrays;

public class InputHandler extends InputAdapter {
    private final BulletForge main;
    private final Vector3 mousePos;

    private int prevWindowWidth, prevWindowHeight;

    private boolean dragging;

    private static final float[] ZOOM_LEVELS = {.25f, .5f, 1f, 1.5f, 2f, 2.5f, 3.5f, 4.5f};
    private float zoom = 1f;

    public InputHandler(BulletForge main) {
        this.main = main;
        mousePos = new Vector3();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.F3) {
            main.toggleDebug();
            return true;
        }

        if (keycode == Input.Keys.F11) {
            if (Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setWindowedMode(prevWindowWidth, prevWindowHeight);
            } else {
                prevWindowWidth = Gdx.graphics.getWidth();
                prevWindowHeight = Gdx.graphics.getHeight();
                Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode(Gdx.graphics.getMonitor());
                if (!Gdx.graphics.setFullscreenMode(displayMode)) {
                    throw new IllegalStateException("Failed to enter fullscreen mode.");
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.RIGHT && main.getUserInterface().getActiveScreen() instanceof EditorScreen) {
            dragging = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (dragging) {
            float deltaX = Gdx.input.getDeltaX() * zoom;
            float deltaY = Gdx.input.getDeltaY() * zoom;

            main.getCamera().position.add(-deltaX, deltaY, 0);
            main.getCamera().update();
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.RIGHT) {
            dragging = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.RIGHT) dragging = false;
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        updateZoom(amountY);
        main.getCamera().zoom = zoom;
        main.getCamera().update();
        return true;
    }

    public Vector3 getMousePos() {
        return getMousePos(true);
    }

    public Vector3 getMousePos(boolean unproject) {
        mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0f);
        if (unproject) main.getCamera().unproject(mousePos);
        return mousePos;
    }

    public Vector3 getDirectionToMouse() {
        return getDirectionToMouse(true);
    }

    public Vector3 getDirectionToMouse(boolean unproject) {
        return getMousePos(unproject).cpy().nor();
    }

    public Vector3 getDirectionToMouse(Vector3 pos) {
        return getDirectionToMouse(pos, true);
    }

    public Vector3 getDirectionToMouse(Vector3 pos, boolean unproject) {
        return getMousePos(unproject).cpy().sub(pos).nor();
    }

    public void updateZoom(float amountY) {
        int index = Arrays.binarySearch(ZOOM_LEVELS, zoom);

        if (amountY > 0f && index < ZOOM_LEVELS.length - 1) zoom = ZOOM_LEVELS[index + 1];
        else if (amountY < 0f && index > 0) zoom = ZOOM_LEVELS[index - 1];
    }
}
