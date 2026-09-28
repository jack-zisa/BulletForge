package dev.creoii.bulletforge;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.creoii.bulletforge.render.DebugRenderer;
import dev.creoii.bulletforge.util.localization.I18n;

import java.util.Locale;

public class BulletForge extends Game {
    private final String windowTitle;
    private OrthographicCamera camera;
    private Stage globalStage;
    private final InputMultiplexer input;
    private final InputHandler inputHandler;
    private final DebugRenderer debugRenderer;
    private final I18n i18n;
    private final UserInterface userInterface;
    private boolean debug;

    public BulletForge(String windowTitle) {
        this.windowTitle = windowTitle;
        input = new InputMultiplexer();
        input.addProcessor(inputHandler = new InputHandler(this));
        debugRenderer = new DebugRenderer(this);
        i18n = new I18n(Locale.ENGLISH);
        userInterface = new UserInterface(this);
    }

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        globalStage = new Stage(new ScreenViewport());
        userInterface.createGlobalActors();

        input.addProcessor(globalStage);

        Gdx.input.setInputProcessor(input);

        debugRenderer.create();
    }

    @Override
    public void resize(int width, int height) {
        camera.update();
        globalStage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK);

        super.render();

        float dt = Gdx.graphics.getDeltaTime();

        if (debug) debugRenderer.render(dt);

        globalStage.act(dt);
        globalStage.draw();
    }

    @Override
    public void dispose() {
        debugRenderer.dispose();
        GlobalAssets.dispose();
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Stage getGlobalStage() {
        return globalStage;
    }

    public InputMultiplexer getInput() {
        return input;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public I18n getI18n() {
        return i18n;
    }

    public UserInterface getUserInterface() {
        return userInterface;
    }

    public void toggleDebug() {
        debug = !debug;
    }
}
