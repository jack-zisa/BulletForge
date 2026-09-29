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
    private final Vector3 centerPos;
    private final Vector3 mousePos;

    private int prevWindowWidth, prevWindowHeight;

    private static final float[] ZOOM_LEVELS = {.25f, .5f, 1f, 1.5f, 2f, 2.5f};
    private float zoom = 1f;

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

    public Vector3 getCenterPos() {
        int width = Gdx.graphics.getWidth() - (int) EditorScreen.EDITOR_PANE_WIDTH;
        int height = Gdx.graphics.getHeight() + ((int) UserInterface.CONTROL_BAR_HEIGHT + (int) UserInterface.TAB_BAR_HEIGHT);
        centerPos.set(width / 2f, height / 2f, 0f);
        main.getCamera().unproject(centerPos);
        return centerPos;
    }

    public Vector3 getDirectionToMouse(Vector3 pos) {
        return getDirectionToMouse(pos, true);
    }

    public Vector3 getDirectionToMouse(Vector3 pos, boolean unproject) {
        return getMousePos(unproject).cpy().sub(pos).nor();
    }

    public void updateZoom(float amountY) {
        int index = Arrays.binarySearch(ZOOM_LEVELS, zoom);
        if (index < 0)
            index = -index - 1;

        if (amountY > .25f && index < ZOOM_LEVELS.length - 1) {
            zoom = ZOOM_LEVELS[index + 1];
        } else if (amountY < -.25f && index > 0) {
            zoom = ZOOM_LEVELS[index - 1];
        }
    }
}
