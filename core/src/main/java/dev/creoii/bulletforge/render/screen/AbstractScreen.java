package dev.creoii.bulletforge.render.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.creoii.bulletforge.BulletForge;

public class AbstractScreen implements Screen {
    private final BulletForge main;
    private final Stage stage;
    private final Table root;

    public AbstractScreen(BulletForge main) {
        this.main = main;
        stage = new Stage(new ScreenViewport());
        stage.addActor(root = new Table());
    }

    @Override
    public void show() {
        main.getInput().addProcessor(getStage());
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        main.getInput().removeProcessor(getStage());
    }

    @Override
    public void resume() {
        main.getInput().addProcessor(getStage());
    }

    @Override
    public void hide() {
        main.getInput().removeProcessor(getStage());
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public Table getRoot() {
        return root;
    }
}
