package dev.creoii.bulletforge.render.window;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.GlobalAssets;

public class WindowControlBar extends Table {
    public WindowControlBar(BulletForge main) {
        FileButton fileButton = new FileButton(main);
        add(fileButton).left();

        HelpButton helpButton = new HelpButton(main);
        add(helpButton).left();

        Label title = new Label(main.getWindowTitle(), GlobalAssets.SKIN);
        title.setAlignment(Align.center);
        add(title).growX();

        add(new MinimizeButton(main)).right();
        add(new MaximizeButton(main)).right();
        add(new ExitButton(main)).right();
    }
}
