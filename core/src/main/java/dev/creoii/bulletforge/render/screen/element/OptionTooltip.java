package dev.creoii.bulletforge.render.screen.element;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import dev.creoii.bulletforge.GlobalAssets;

public class OptionTooltip extends Table {
    private final Actor owner;

    public OptionTooltip(Actor owner) {
        this.owner = owner;

        setTouchable(Touchable.disabled);
        setVisible(false);

        add(new Label("New", GlobalAssets.SKIN)).row();
        add(new Label("Open", GlobalAssets.SKIN));
    }

    public void show() {
        pack();

        setPosition(owner.getX(), owner.getY() - getHeight());

        toFront();
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }
}
