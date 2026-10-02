package dev.creoii.bulletforge.render.screen.element.editor.config;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.reflect.Field;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.util.EditorUtils;
import dev.creoii.providerlib.api.value.datatype.DataType;
import dev.creoii.providerlib.api.value.datatype.DataTypes;

public record NumberFieldDynamicFieldConfig(Object owner, Field field) implements DynamicFieldConfig<Float> {
    @Override
    public DataType<Float> dataType() {
        return DataTypes.FLOAT;
    }

    @Override
    public Actor create(Float value) {
        TextField textField = new TextField("", GlobalAssets.SKIN);
        textField.setTextFieldFilter(EditorUtils.NumberFilter.INSTANCE);
        textField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                set(DataTypes.FLOAT.convert(textField.getText()));
            }
        });
        return textField;
    }
}
