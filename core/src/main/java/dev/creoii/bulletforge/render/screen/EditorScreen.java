package dev.creoii.bulletforge.render.screen;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.GlobalAssets;

public class EditorScreen extends AbstractScreen {
    public EditorScreen(BulletForge main) {
        super(main);

        getRoot().add(new Label("Editor", GlobalAssets.SKIN)).center();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
