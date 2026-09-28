package dev.creoii.bulletforge.render.window;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.render.screen.element.OptionTooltip;
import dev.creoii.bulletforge.render.screen.element.option.OptionProvider;
import dev.creoii.bulletforge.render.screen.element.tooltip.TooltipProvider;

import java.util.List;

public class HelpButton extends TextButton implements TooltipProvider, OptionProvider {
    private final OptionTooltip tooltip;

    public HelpButton(BulletForge main) {
        super(main.getI18n().get("window.controlBar.help"), GlobalAssets.SKIN);
        tooltip = new OptionTooltip(this);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isChecked()) tooltip.show();
                else tooltip.hide();
            }
        });
    }

    @Override
    public OptionTooltip getTooltip() {
        return tooltip;
    }

    @Override
    public List<Actor> getOptions() {
        return List.of(
            new TextButton("Settings", GlobalAssets.SKIN),
            new TextButton("About", GlobalAssets.SKIN)
        );
    }
}
