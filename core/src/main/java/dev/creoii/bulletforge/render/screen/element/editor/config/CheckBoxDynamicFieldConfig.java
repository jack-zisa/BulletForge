package dev.creoii.bulletforge.render.screen.element.editor.config;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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

    @Override
    public Actor create(Boolean value) {
        Button button = new Button(GlobalAssets.SKIN);

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                set(button.isChecked());
            }
        });

        return button;
    }
}
