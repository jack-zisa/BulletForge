package dev.creoii.bulletforge.render.screen.element.editor.config;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.reflect.Field;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.render.screen.element.CyclingButton;
import dev.creoii.providerlib.api.value.datatype.DataType;
import dev.creoii.providerlib.api.value.datatype.DataTypes;

public record CycleButtonDynamicFieldConfig<T>(Object owner, Field field, T[] values) implements DynamicFieldConfig<Integer> {
    @Override
    public DataType<Integer> dataType() {
        return DataTypes.INTEGER;
    }

    @Override
    public Actor create(Integer value) {
        CyclingButton<T> button = new CyclingButton<>("", values, GlobalAssets.SKIN);

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                set(button.getCurrentIndex());
            }
        });

        return button;
    }
}
