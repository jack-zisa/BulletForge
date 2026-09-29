package dev.creoii.bulletforge.render.screen.element;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.render.screen.EditorScreen;

public class AutofireButton extends Table {
    public AutofireButton(BulletForge main) {
        CheckBox checkBox = new CheckBox("Autofire", GlobalAssets.SKIN);
        checkBox.setChecked(true);
        checkBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (main.getUserInterface().getActiveScreen() instanceof EditorScreen editorScreen) {
                    editorScreen.getAttackManager().setAutofire(!editorScreen.getAttackManager().isAutofire());
                }
            }
        });

        add(checkBox);

        setVisible(false);
    }
}
