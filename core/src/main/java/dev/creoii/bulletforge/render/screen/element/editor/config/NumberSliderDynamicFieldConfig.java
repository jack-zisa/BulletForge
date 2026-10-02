package dev.creoii.bulletforge.render.screen.element.editor.config;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.reflect.Field;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.util.EditorUtils;
import dev.creoii.providerlib.api.value.datatype.DataType;
import dev.creoii.providerlib.api.value.datatype.DataTypes;

public record NumberSliderDynamicFieldConfig(Object owner, Field field, float min, float max, float stepSize) implements DynamicFieldConfig<Float> {
    @Override
    public DataType<Float> dataType() {
        return DataTypes.FLOAT;
    }

    @Override
    public Actor create(Float value) {
        Table table = new Table();
        Slider slider = new Slider(min, max, stepSize, false, GlobalAssets.SKIN);
        TextField textField = new TextField("", GlobalAssets.SKIN);

        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                set(slider.getValue());
                textField.setText(String.valueOf(slider.getValue()));
            }
        });

        textField.setTextFieldFilter(EditorUtils.NumberFilter.INSTANCE);
        textField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float f = DataTypes.FLOAT.convert(textField.getText());
                set(f);
                slider.setValue(f);
            }
        });

        table.add(slider);
        table.add(textField);

        return table;
    }
}
