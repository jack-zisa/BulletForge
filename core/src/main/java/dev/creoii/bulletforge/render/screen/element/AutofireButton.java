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

public class AutofireButton extends Table {
    public AutofireButton(BulletForge main) {
        CheckBox.CheckBoxStyle style = new CheckBox.CheckBoxStyle(GlobalAssets.SKIN.get(CheckBox.CheckBoxStyle.class));
        style.checkboxOn = new TextureRegionDrawable(GlobalAssets.AUTOFIRE);
        style.checkboxOnOver = new TextureRegionDrawable(GlobalAssets.AUTOFIRE).tint(Color.LIGHT_GRAY);
        style.checkboxOff = new TextureRegionDrawable(GlobalAssets.AUTOFIRE_OFF);
        style.checkboxOver = new TextureRegionDrawable(GlobalAssets.AUTOFIRE_OFF).tint(Color.LIGHT_GRAY);

        CheckBox checkBox = new CheckBox(main.getI18n().get("tool.autofire"), style);
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
