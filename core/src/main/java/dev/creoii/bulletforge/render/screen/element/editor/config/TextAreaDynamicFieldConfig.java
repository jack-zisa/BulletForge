package dev.creoii.bulletforge.render.screen.element.editor.config;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.reflect.Field;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.providerlib.api.value.datatype.DataType;
import dev.creoii.providerlib.api.value.datatype.DataTypes;

public record TextAreaDynamicFieldConfig(Object owner, Field field, String defaultText) implements DynamicFieldConfig<String> {
    @Override
    public DataType<String> dataType() {
        return DataTypes.STRING;
    }

    @Override
    public Actor create(String value) {
        TextArea textArea = new TextArea(defaultText, GlobalAssets.SKIN);
        textArea.setText(value);
        textArea.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                set(textArea.getText());
            }
        });
        return textArea;
    }
}
