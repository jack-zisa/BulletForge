package dev.creoii.bulletforge.render.screen.element;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.GlobalAssets;

public class FileButton extends TextButton {
    private final OptionTooltip tooltip;

    public FileButton(BulletForge main) {
        super(main.getI18n().get("window.controlBar.file"), GlobalAssets.SKIN);
        tooltip = new OptionTooltip(this);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isChecked()) tooltip.show();
                else tooltip.hide();
            }
        });
    }

    public OptionTooltip getTooltip() {
        return tooltip;
    }
}
