package dev.creoii.bulletforge.render.screen.element;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.GlobalAssets;

public class WindowControlBar extends Table {
    public WindowControlBar(BulletForge main) {
        add(new TextButton(main.getI18n().get("window.controlBar.file"), GlobalAssets.SKIN));
    }
}
