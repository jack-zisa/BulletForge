package dev.creoii.bulletforge.render.screen.element.editor.config;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.reflect.Field;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.render.screen.EditorScreen;
import dev.creoii.bulletforge.render.screen.element.CollapsiblePane;
import dev.creoii.bulletforge.render.screen.element.editor.DynamicUIFieldRegistry;
import dev.creoii.providerlib.api.value.datatype.DataType;
import dev.creoii.providerlib.api.value.datatype.DataTypes;

import java.util.Collection;
import java.util.Map;

public record ListDynamicFieldConfig<T>(Object owner, Field field) implements DynamicFieldConfig<Collection<T>> {
    @Override
    public DataType<Collection<T>> dataType() {
        return DataTypes.collection();
    }

    public boolean hasCustomHeader() {
        return true;
    }

    @Override
    public Actor create(Collection<T> value) {
        Table table = new Table(GlobalAssets.SKIN);
        table.top().left();
        table.defaults().growX().top();

        TextButton addButton = new TextButton("+", GlobalAssets.SKIN);
        table.add(addButton).right().row();

        int index = 0;
        for (T element : value) {
            Table elementTable = new Table(GlobalAssets.SKIN);
            elementTable.top().left();
            elementTable.defaults().growX().top();

            Map<Field, DynamicFieldConfig<?>> fields = DynamicUIFieldRegistry.get(element);
            if (fields != null) {
                for (Map.Entry<Field, DynamicFieldConfig<?>> entry : fields.entrySet()) {
                    Field field = entry.getKey();
                    DynamicFieldConfig<?> config = entry.getValue();

                    if (!config.hasCustomHeader()) elementTable.add(new Label(field.getName(), GlobalAssets.SKIN)).left();
                    elementTable.add(config.create()).growX().maxWidth(EditorScreen.EDITOR_PANE_WIDTH).row();
                }
            }

            table.add(new CollapsiblePane("[" + index++ + "]", elementTable, GlobalAssets.SKIN)).growX().top().row();
        }
        return table;
    }
}
