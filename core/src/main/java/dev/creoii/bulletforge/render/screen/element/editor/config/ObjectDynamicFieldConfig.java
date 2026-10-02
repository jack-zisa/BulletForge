package dev.creoii.bulletforge.render.screen.element.editor.config;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.reflect.Field;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.render.screen.element.CollapsiblePane;
import dev.creoii.bulletforge.render.screen.element.editor.DynamicUIFieldRegistry;
import dev.creoii.providerlib.api.value.datatype.DataType;
import dev.creoii.providerlib.api.value.datatype.DataTypes;

import java.util.Map;

public record ObjectDynamicFieldConfig<T>(T owner, Field field, boolean collapsible) implements DynamicFieldConfig<T> {
    public ObjectDynamicFieldConfig(T owner, Field field) {
        this(owner, field, true);
    }

    @Override
    public DataType<T> dataType() {
        return DataTypes.object();
    }

    @Override
    public Actor create(T value) {
        Container<Table> container = new Container<>();
        CollapsiblePane pane = new CollapsiblePane(container);

        Map<Field, DynamicFieldConfig<?>> fields = DynamicUIFieldRegistry.get(value);
        if (fields == null)
            return container;

        Table table = new Table(GlobalAssets.SKIN);

        for (Map.Entry<Field, DynamicFieldConfig<?>> entry : fields.entrySet()) {
            Field elementField = entry.getKey();
            DynamicFieldConfig<?> config = entry.getValue();

            table.add(new Label(elementField.getName(), GlobalAssets.SKIN)).left();
            table.add(config.create()).growX().row();
        }

        container.setActor(table);
        return collapsible ? pane : container;
    }
}
