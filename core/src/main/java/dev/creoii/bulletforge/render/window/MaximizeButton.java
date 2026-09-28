package dev.creoii.bulletforge.render.window;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.GlobalAssets;

public class MaximizeButton extends ImageButton {
    private boolean maximized;

    public MaximizeButton(BulletForge main) {
        super(new TextureRegionDrawable(GlobalAssets.MAXIMIZE_ICON));

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Gdx.graphics instanceof Lwjgl3Graphics graphics) {
                    Lwjgl3Window window = graphics.getWindow();
                    if (maximized)
                        window.restoreWindow();
                    else window.maximizeWindow();

                    maximized = !maximized;
                }
            }
        });
    }
}
