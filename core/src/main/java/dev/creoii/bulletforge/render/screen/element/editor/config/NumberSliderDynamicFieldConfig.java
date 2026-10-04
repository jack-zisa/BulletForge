package dev.creoii.bulletforge.render.screen.element.editor.config;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.reflect.Field;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.render.screen.EditorScreen;
import dev.creoii.bulletforge.util.EditorUtils;
import dev.creoii.providerlib.api.value.datatype.DataType;
import dev.creoii.providerlib.api.value.datatype.DataTypes;

public record NumberSliderDynamicFieldConfig(Object owner, Field field, float min, float max, float stepSize, boolean showField) implements DynamicFieldConfig<Float> {
    public NumberSliderDynamicFieldConfig(Object owner, Field field, float min, float max, float stepSize) {
        this(owner, field, min, max, stepSize, true);
    }

    public NumberSliderDynamicFieldConfig(Object owner, Field field, float min, float max) {
        this(owner, field, min, max, 1, true);
    }

    @Override
    public DataType<Float> dataType() {
        return DataTypes.FLOAT;
    }

    @Override
    public Actor create(Float value) {
        Table table = new Table();

        Slider slider = new Slider(min, max, stepSize, false, GlobalAssets.SKIN);
        slider.setValue(value);

        TextField textField = new TextField(String.valueOf(value), GlobalAssets.SKIN);
        textField.setTextFieldFilter(EditorUtils.NumberFilter.INSTANCE);

        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float f = Math.clamp(slider.getValue(), min, max);
                set(f);
                textField.setText(String.valueOf(f));
            }
        });

        slider.addListener(new InputListener() {
            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
                if (amountY != 0f) {
                    float f = Math.clamp(slider.getValue(), min, max);
                    slider.setValue(amountY > 0f ? f + 1 : f - 1);
                    textField.setText(String.valueOf(f));
                    return true;
                }
                return false;
            }
        });

        textField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float f = Math.clamp(DataTypes.FLOAT.convert(textField.getText()), min, max);
                set(f);
                slider.setValue(f);
            }
        });

        table.add(slider).width(EditorScreen.EDITOR_PANE_WIDTH / 3f).fillX();
        if (showField) table.add(textField).expandX();

        return table;
    }
}
