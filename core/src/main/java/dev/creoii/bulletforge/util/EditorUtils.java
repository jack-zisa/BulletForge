package dev.creoii.bulletforge.util;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.Method;
import dev.creoii.bulletforge.render.screen.element.CollapsiblePane;
import dev.creoii.bulletforge.render.screen.element.editor.DynamicUIConfig;
import dev.creoii.bulletforge.render.screen.element.editor.DynamicUIRegistry;
import dev.creoii.bulletforge.render.screen.element.editor.config.DynamicFieldConfig;

import java.util.Collection;
import java.util.Map;

public final class EditorUtils {
    public static Table createObjectListEditor(Collection<?> objects, Skin skin) {
        Table table = new Table(skin);
        table.top().left();
        table.defaults().growX().top();

        int index = 0;
        for (Object object : objects) {
            Table objectTable = createObjectEditor(object, skin);
            CollapsiblePane pane = new CollapsiblePane("[" + index++ + "]", objectTable, skin);
            table.add(pane).growX().top().left().row();
        }
        return table;
    }

    public static Table createObjectEditor(Object object, Skin skin) {
        Table table = new Table(skin);
        table.top().left();
        table.defaults().growX().top().left();

        DynamicUIConfig config = DynamicUIRegistry.get(object);
        if (config == null)
            return table;

        for (Map.Entry<Field, DynamicFieldConfig<?>> entry : config.fields().entrySet()) {
            Field field = entry.getKey();
            DynamicFieldConfig<?> fieldConfig = entry.getValue();

            if (!fieldConfig.hasCustomHeader()) table.add(new Label(field.getName(), skin)).growX().left().row();
            table.add(fieldConfig.create()).growX().top().left().row();
        }
        return table;
    }

    static public class NumberFilter implements TextField.TextFieldFilter {
        public static final NumberFilter INSTANCE = new NumberFilter();

        @Override
        public boolean acceptChar(TextField textField, char c) {
            if (Character.isDigit(c)) return true;
            if (c == '-') return textField.getCursorPosition() == 0 && !textField.getText().contains("-");
            if (c == '.') return textField.getCursorPosition() > 0 && !textField.getText().contains(".");
            return false;
        }
    }
}
