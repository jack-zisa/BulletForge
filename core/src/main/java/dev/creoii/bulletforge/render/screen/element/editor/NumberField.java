package dev.creoii.bulletforge.render.screen.element.editor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.util.EditorUtils;

public class NumberField extends Table {
    private final TextField textField;
    private final float min, max;

    public NumberField(float min, float max, float stepSize, float defaultValue, boolean showButtons, Skin skin) {
        super(skin);
        textField = new TextField(String.valueOf(defaultValue), skin);
        this.min = min;
        this.max = max;

        textField.setTextFieldFilter(EditorUtils.NumberFilter.INSTANCE);

        add(textField).growX();

        if (showButtons) {
            ImageButton up = new ImageButton(new TextureRegionDrawable(GlobalAssets.DROPARROW));

            TextureRegion downRegion = new TextureRegion(GlobalAssets.DROPARROW);
            downRegion.flip(false, true);

            ImageButton down = new ImageButton(new TextureRegionDrawable(downRegion));

            up.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                    incrementValue(stepSize);
                }
            });

            down.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    incrementValue(-stepSize);
                }
            });

            Table buttons = new Table();
            buttons.add(up).row();
            buttons.add(down);

            add(buttons);
        }

        textField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                validate();
            }
        });
    }

    private void incrementValue(float amount) {
        setValue(getValue() + amount);
    }

    public float getValue() {
        try {
            return MathUtils.clamp(Float.parseFloat(textField.getText()), min, max);
        } catch (NumberFormatException e) {
            return min;
        }
    }

    public void setValue(float value) {
        value = MathUtils.clamp(value, min, max);
        textField.setText(String.valueOf(value));
    }
}
