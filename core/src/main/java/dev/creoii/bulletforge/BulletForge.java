package dev.creoii.bulletforge;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import dev.creoii.bulletforge.render.DebugRenderer;
import dev.creoii.bulletforge.render.SpriteRenderer;
import dev.creoii.bulletforge.render.UIRenderer;

public class BulletForge extends ApplicationAdapter {
    private OrthographicCamera camera;
    private final InputHandler inputHandler;
    private final SpriteRenderer renderer;
    private final DebugRenderer debugRenderer;
    private final UIRenderer uiRenderer;
    private boolean debug;

    public BulletForge() {
        inputHandler = new InputHandler(this);
        renderer = new SpriteRenderer(this);
        debugRenderer = new DebugRenderer(this);
        uiRenderer = new UIRenderer(this);
    }

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        Gdx.input.setInputProcessor(inputHandler);

        renderer.create();
        debugRenderer.create();
        uiRenderer.create();
    }

    @Override
    public void resize(int width, int height) {
        camera.update();
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK);

        renderer.render();
        if (debug) debugRenderer.render();
        uiRenderer.render();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        debugRenderer.dispose();
        uiRenderer.dispose();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public void toggleDebug() {
        debug = !debug;
    }
}
