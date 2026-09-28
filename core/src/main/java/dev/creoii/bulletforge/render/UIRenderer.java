package dev.creoii.bulletforge.render;

import com.badlogic.gdx.scenes.scene2d.Stage;
import dev.creoii.bulletforge.BulletForge;

public class UIRenderer implements Renderer {
    private final BulletForge main;
    private Stage stage;

    public UIRenderer(BulletForge main) {
        this.main = main;
    }

    @Override
    public void create() {
        stage = new Stage();
    }

    @Override
    public void render() {
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
