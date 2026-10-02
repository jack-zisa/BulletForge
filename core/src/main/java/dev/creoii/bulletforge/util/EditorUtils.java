package dev.creoii.bulletforge.util;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.reflect.Field;
import dev.creoii.bulletforge.render.screen.element.editor.DynamicUIFieldRegistry;
import dev.creoii.bulletforge.render.screen.element.editor.config.DynamicFieldConfig;

import java.util.Collection;
import java.util.Map;

public final class EditorUtils {
    public static Table createObjectEditor(Object object, Skin skin) {
        Table table = new Table(skin);
        table.top().left();
        table.defaults().growX().top();

        Map<Field, DynamicFieldConfig<?>> fields = DynamicUIFieldRegistry.get(object);
        if (fields == null)
            return table;

        for (Map.Entry<Field, DynamicFieldConfig<?>> entry : fields.entrySet()) {
            Field field = entry.getKey();
            DynamicFieldConfig<?> config = entry.getValue();

            table.add(new Label(field.getName(), skin)).left();
            table.add(config.create()).growX().row();
        }

        return table;
    }

    public static Table createObjectListEditor(Collection<?> objects, Skin skin) {
        Table table = new Table(skin);
        table.top().left();
        table.defaults().growX().top();

        for (Object object : objects) {
            Container<Table> container = new Container<>();

            Table objectTable = createObjectEditor(object, skin);
            container.setActor(objectTable);

            table.add(container).growX().row();
        }

        return table;
    }

    static public class NumberFilter implements TextField.TextFieldFilter {
        public static final NumberFilter INSTANCE = new NumberFilter();

        public boolean acceptChar(TextField textField, char c) {
            return Character.isDigit(c) || ((textField.getCursorPosition() == 0 || textField.getText().isBlank()) && c == '-') || (textField.getCursorPosition() > 0 && !textField.getText().contains(".") && c == '.');
        }
    }

    static public class IntegerFilter implements TextField.TextFieldFilter {
        public static final IntegerFilter INSTANCE = new IntegerFilter();

        public boolean acceptChar(TextField textField, char c) {
            return Character.isDigit(c) || (textField.getCursorPosition() == 0 && c == '-');
        }
    }
}
