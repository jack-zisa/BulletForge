package dev.creoii.bulletforge.render.screen.element.editor.config;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.reflect.Field;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.providerlib.api.value.datatype.DataType;
import dev.creoii.providerlib.api.value.datatype.DataTypes;

public record SelectBoxDynamicFieldConfig<T>(Object owner, Field field, T[] values) implements DynamicFieldConfig<Integer> {
    @Override
    public DataType<Integer> dataType() {
        return DataTypes.INTEGER;
    }

    @Override
    public Actor create(Integer value) {
        SelectBox<T> selectBox = new SelectBox<>(GlobalAssets.SKIN);
        selectBox.setItems(values);

        if (value != null && value >= 0 && value < values.length) {
            selectBox.setSelectedIndex(value);
        }

        selectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                set(selectBox.getSelectedIndex());
            }
        });

        return selectBox;
    }
}
