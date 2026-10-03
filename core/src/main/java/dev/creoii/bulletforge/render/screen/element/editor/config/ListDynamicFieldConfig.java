package dev.creoii.bulletforge.render.screen.element.editor.config;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.reflect.Field;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.render.screen.element.CollapsiblePane;
import dev.creoii.bulletforge.util.EditorUtils;
import dev.creoii.providerlib.api.value.datatype.DataType;
import dev.creoii.providerlib.api.value.datatype.DataTypes;

import java.util.Collection;

public record ListDynamicFieldConfig<T>(Object owner, Field field) implements DynamicFieldConfig<Collection<T>> {
    @Override
    public DataType<Collection<T>> dataType() {
        return DataTypes.collection();
    }

    public boolean hasCustomHeader() {
        return true;
    }

    @Override
    public boolean isNested() {
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
            table.add(new CollapsiblePane("[" + index++ + "]", EditorUtils.createObjectEditor(element, GlobalAssets.SKIN), GlobalAssets.SKIN)).growX().top().row();
        }
        return table;
    }
}
