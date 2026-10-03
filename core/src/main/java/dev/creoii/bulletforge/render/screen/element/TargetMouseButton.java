package dev.creoii.bulletforge.render.screen.element;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.render.screen.EditorScreen;

public class TargetMouseButton extends Table {
    public TargetMouseButton(BulletForge main) {
        CheckBox.CheckBoxStyle style = new CheckBox.CheckBoxStyle(GlobalAssets.SKIN.get(CheckBox.CheckBoxStyle.class));
        style.checkboxOn = new TextureRegionDrawable(GlobalAssets.TARGET);
        style.checkboxOnOver = new TextureRegionDrawable(GlobalAssets.TARGET).tint(Color.LIGHT_GRAY);
        style.checkboxOff = new TextureRegionDrawable(GlobalAssets.TARGET_OFF);
        style.checkboxOver = new TextureRegionDrawable(GlobalAssets.TARGET_OFF).tint(Color.LIGHT_GRAY);

        CheckBox checkBox = new CheckBox(main.getI18n().get("tool.target_mouse"), style);
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
    }
}
