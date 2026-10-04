package dev.creoii.bulletforge.render.screen.element.editor.config;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.reflect.Field;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.providerlib.api.value.datatype.DataType;
import dev.creoii.providerlib.api.value.datatype.DataTypes;

public record CheckBoxDynamicFieldConfig(Object owner, Field field) implements DynamicFieldConfig<Boolean> {
    @Override
    public DataType<Boolean> dataType() {
        return DataTypes.BOOLEAN;
    }

    public boolean hasCustomHeader() {
        return true;
    }

    @Override
    public Actor create(Boolean value) {
        CheckBox checkBox = new CheckBox(field.getName(), GlobalAssets.SKIN);
        checkBox.setChecked(value);

        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                set(checkBox.isChecked());
            }
        });

        checkBox.left();

        return checkBox;
    }
}
