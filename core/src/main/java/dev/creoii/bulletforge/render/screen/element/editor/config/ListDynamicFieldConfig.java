package dev.creoii.bulletforge.render.screen.element.editor.config;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.reflect.Field;
import dev.creoii.bulletforge.GlobalAssets;
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

    @Override
    public Actor create(Collection<T> value) {
        CollapsiblePane pane = new CollapsiblePane(new List<>(GlobalAssets.SKIN));

        Table header = new Table();
        TextButton fieldHeader = new TextButton(field.getName(), GlobalAssets.SKIN);
        TextButton addButton = new TextButton("+", GlobalAssets.SKIN);

        header.add(fieldHeader).growX().right();
        header.add(addButton).right();

        fieldHeader.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pane.setExpanded(!pane.isExpanded());
            }
        });

        if (pane.getContent() instanceof Table table) {
            for (T element : value) {
                Map<Field, DynamicFieldConfig<?>> fields = DynamicUIFieldRegistry.get(element);
                if (fields == null)
                    continue;

                for (Map.Entry<Field, DynamicFieldConfig<?>> entry : fields.entrySet()) {
                    Field elementField = entry.getKey();
                    DynamicFieldConfig<?> config = entry.getValue();

                    table.add(new Label(elementField.getName(), GlobalAssets.SKIN)).left();
                    table.add(config.create()).growX().row();
                }
            }
        }

        return pane;
    }
}
