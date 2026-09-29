package dev.creoii.bulletforge;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.creoii.bulletforge.render.DebugRenderer;
import dev.creoii.bulletforge.util.localization.I18n;
import games.spooky.gdx.nativefilechooser.NativeFileChooser;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;

import java.nio.file.Paths;
import java.util.Locale;

public class BulletForge extends Game {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final String windowTitle;
    private OrthographicCamera camera;
    private Stage globalStage;
    private final InputMultiplexer input;
    private final InputHandler inputHandler;
    private final DebugRenderer debugRenderer;
    private final I18n i18n;
    private final UserInterface userInterface;
    private final NativeFileChooser fileChooser;
    private final NativeFileChooserConfiguration fileChooserConfiguration;
    private long time;
    private boolean debug;

    public BulletForge(String windowTitle, NativeFileChooser fileChooser) {
        this.windowTitle = windowTitle;
        this.fileChooser = fileChooser;
        input = new InputMultiplexer();
        input.addProcessor(inputHandler = new InputHandler(this));
        debugRenderer = new DebugRenderer(this);
        i18n = new I18n(Locale.ENGLISH);
        userInterface = new UserInterface(this);
        fileChooserConfiguration = new NativeFileChooserConfiguration();
    }

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        globalStage = new Stage(new ScreenViewport());
        userInterface.createGlobalActors();

        fileChooserConfiguration.directory = new FileHandle(Paths.get(System.getProperty("user.home"), "Documents").toFile());
        fileChooserConfiguration.nameFilter = (_, name) -> name.endsWith(".json");

        input.addProcessor(globalStage);

        Gdx.input.setInputProcessor(input);

        debugRenderer.create();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        globalStage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK);

        float dt = Gdx.graphics.getDeltaTime();

        userInterface.render(dt);

        if (debug) debugRenderer.render(dt);

        globalStage.act(dt);
        globalStage.draw();

        ++time;
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

    public NativeFileChooser getFileChooser() {
        return fileChooser;
    }

    public NativeFileChooserConfiguration getFileChooserConfiguration() {
        return fileChooserConfiguration;
    }

    public long getTime() {
        return time;
    }

    public void toggleDebug() {
        debug = !debug;
    }
}
