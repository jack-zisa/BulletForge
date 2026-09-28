package dev.creoii.bulletforge.render.screen.element;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import dev.creoii.bulletforge.render.screen.element.option.OptionProvider;

public class OptionTooltip extends Table {
    private final Actor owner;

    public OptionTooltip(Actor owner) {
        this.owner = owner;

        setVisible(false);

        if (owner instanceof OptionProvider optionProvider) {
            optionProvider.getOptions().forEach(actor -> {
                add(actor).row();
            });
        } else throw new IllegalArgumentException("Cannot create an OptionTooltip without an OptionProvider.");
    }

    public void show() {
        pack();

        Vector2 position = owner.localToStageCoordinates(new Vector2());
        setPosition(position.x, position.y - getHeight());

        toFront();
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }
}
