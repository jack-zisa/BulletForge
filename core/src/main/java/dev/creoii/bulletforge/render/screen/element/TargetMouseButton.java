package dev.creoii.bulletforge.render.screen.element;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.render.screen.EditorScreen;

public class TargetMouseButton extends Table {
    public TargetMouseButton(BulletForge main) {
        CheckBox checkBox = new CheckBox("Target Mouse", GlobalAssets.SKIN);
        checkBox.setChecked(false);
        checkBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (main.getUserInterface().getActiveScreen() instanceof EditorScreen editorScreen) {
                    editorScreen.getAttackManager().setTargetMouse(!editorScreen.getAttackManager().shouldTargetMouse());
                }
            }
        });

        add(checkBox);

        setVisible(false);
    }
}
