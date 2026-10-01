package dev.creoii.bulletforge.render.screen.element;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.GlobalAssets;

public class ResetPositionButton extends Table {
    public ResetPositionButton(BulletForge main) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(GlobalAssets.TARGET);
        style.over = new TextureRegionDrawable(GlobalAssets.TARGET).tint(Color.LIGHT_GRAY);
        style.checked = new TextureRegionDrawable(GlobalAssets.TARGET);
        style.checkedOver = new TextureRegionDrawable(GlobalAssets.TARGET).tint(Color.LIGHT_GRAY);
        style.imageChecked = new TextureRegionDrawable(GlobalAssets.TARGET);
        style.imageCheckedOver = new TextureRegionDrawable(GlobalAssets.TARGET).tint(Color.LIGHT_GRAY);
        style.down = new TextureRegionDrawable(GlobalAssets.TARGET_OFF).tint(Color.LIGHT_GRAY);
        style.checkedDown = new TextureRegionDrawable(GlobalAssets.TARGET_OFF).tint(Color.LIGHT_GRAY);
        style.imageCheckedDown = new TextureRegionDrawable(GlobalAssets.TARGET_OFF).tint(Color.LIGHT_GRAY);

        ImageButton imageButton = new ImageButton(style);
        add(imageButton);
        add(new Label(main.getI18n().get("tool.reset_position"), GlobalAssets.SKIN));

        imageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.getCamera().position.setZero();
                main.getCamera().update();
            }
        });

        setVisible(false);
    }
}
