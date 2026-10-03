package dev.creoii.bulletforge.render.screen.element.editor.config;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.reflect.Field;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.render.screen.element.CollapsiblePane;
import dev.creoii.bulletforge.util.EditorUtils;
import dev.creoii.providerlib.api.value.datatype.DataType;
import dev.creoii.providerlib.api.value.datatype.DataTypes;

public record ObjectDynamicFieldConfig<T>(T owner, Field field, boolean collapsible) implements DynamicFieldConfig<T> {
    public ObjectDynamicFieldConfig(T owner, Field field) {
        this(owner, field, true);
    }

    @Override
    public DataType<T> dataType() {
        return DataTypes.object();
    }

    public boolean hasCustomHeader() {
        return true;
    }

    @Override
    public boolean isNested() {
        return true;
    }

    @Override
    public Actor create(T value) {
        Container<Table> container = new Container<>(EditorUtils.createObjectEditor(value, GlobalAssets.SKIN));
        return collapsible ? new CollapsiblePane(field.getName(), container, GlobalAssets.SKIN) : container;
    }
}
